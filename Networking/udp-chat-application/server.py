import socket
import argparse
from typing import Dict, Tuple
from protocol import Message, MessageType

class ChatServer:
    def __init__(self, host: str = 'localhost', port: int = 12345):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.users: Dict[str, Tuple[str, int]] = {}  # Username -> (address, port)
        self.missed_acks: Dict[str, int] = {}  # Username -> pending acknowledgment count
        # Generate message IDs for acknowledgment tracking
        self.next_message_id = 1
        
    def start(self):
        self.sock.bind((self.host, self.port))
        print(f"Server started on {self.host}:{self.port}")
        while True:
            try:
                pass
                data, addr = self.sock.recvfrom(1024)
                received_msg = Message.from_json(data.decode())
                self.handle_message(received_msg, addr)
            except Exception as e:
                print(f"Error handling message: {e}")

    
    def handle_message(self, message: Message, addr: Tuple[str, int]):
        pass
        # Handle CHAT messages
        if message.type == MessageType.CHAT:
            if message.sender_name not in self.users:
                print(f"User {message.sender_name} not registered")
            else:
                message.message_id = self.next_message_id
                self.next_message_id += 1

                # Broadcast the message to all other users
                for username in list(self.users.keys()):
                    user_addr = self.users[username]
                    if username != message.sender_name:
                        self.sock.sendto(message.to_json().encode(), user_addr)
                        # Increment the recipient's pending acknowledgment count
                        if self.missed_acks.get(username):
                            self.missed_acks[username] += 1
                        else:
                            self.missed_acks[username] = 1
                        # Remove users after more than five missed acknowledgments
                        if self.missed_acks[username] > 5:
                            print(f"User {username} unregistered, missed acks")
                            kick_msg = Message(type=MessageType.CHAT,
                                               sender_name='Server',
                                               content=f'You have been kicked from chat! Please reconnect.'
                                               )
                            # Notify the user before removal
                            self.sock.sendto(kick_msg.to_json().encode(), self.users[username])
                            self.users.pop(username, None)
                            self.missed_acks.pop(username, None)

        # Handle ACK messages
        elif message.type == MessageType.ACK:
            if message.sender_name in self.users:

                self.missed_acks[message.sender_name] -= 1  # Reduce the pending acknowledgment count
                print(f'Acknowledged message from {message.sender_name}')

                if self.missed_acks[message.sender_name] < 0:
                    print(f'ERROR: User {message.sender_name} acks reached invalid state')
                    self.missed_acks[message.sender_name] = 0
            else:
                print(f'ERROR: received from non-registered user {message.sender_name}')

        # Handle REGISTER messages
        elif message.type == MessageType.REGISTER:
            username = message.sender_name
            if username in self.users:
                response = Message(type=MessageType.REGISTER_RESPONSE,
                    sender_name='Server',
                    content='ERROR: Username already taken'
                )
                self.sock.sendto(response.to_json().encode(), addr)
                print(f"Registration failed for {username}: already taken")
            else:
                # Include current participants in the welcome message
                if self.users:
                    userlist = ", ".join(self.users.keys())
                    welcome_msg = f'Welcome {username}! \nChatting With: {userlist}'
                # Welcome the first participant
                else:
                    welcome_msg = f'Welcome {username}!'

                self.users[username] = addr
                self.missed_acks[username] = 0

                response = Message(
                    type=MessageType.REGISTER_RESPONSE,
                    sender_name='Server',
                    content= welcome_msg
                )
                self.sock.sendto(response.to_json().encode(), addr)
                print(f"User {username} registered from {addr}")

                # Notify other participants that the user joined
                reg_msg = Message(type=MessageType.CHAT,
                                  sender_name='Server',
                                  content=f'{username} has joined the chat!'
                )

                for user in list(self.users.keys()):
                    user_addr = self.users[user]
                    if user != message.sender_name:
                        self.missed_acks[user] += 1
                        self.sock.sendto(reg_msg.to_json().encode(), user_addr)

        # Handle UNREGISTER messages
        elif message.type == MessageType.UNREGISTER:
            username = message.sender_name

            if username in self.users:
                print(f"User {username} unregistered")
                self.users.pop(username, None)
                self.missed_acks.pop(username, None)

                # Notify other participants that the user left
                unreg_msg = Message(type=MessageType.CHAT,
                                  sender_name='Server',
                                  content=f'{username} has left the chat!'
                                  )

                for user in list(self.users.keys()):
                    user_addr = self.users[user]
                    if user != message.sender_name:
                        self.sock.sendto(unreg_msg.to_json().encode(), user_addr)
                        self.missed_acks[user] +=1
            # Handle requests from users who are not registered
            else:
                print(f'ERROR: Cannot unregister non-registered user {message.sender_name}')


        # Handle unknown message types
        else:
            print(f"Unknown message type: {message.type}")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='UDP Chat Server')
    parser.add_argument('--host', type=str, default='localhost',
                      help='The host address to bind to (default: localhost)')
    parser.add_argument('--port', type=int, default=12345,
                      help='The port to bind to (default: 12345)')
    
    args = parser.parse_args()
    server = ChatServer(host=args.host, port=args.port)
    server.start()
