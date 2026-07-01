import socket
import threading
import sys
import argparse
from protocol import Message, MessageType

class ChatClient:
    def __init__(self, name: str, server_host: str = 'localhost', server_port: int = 12345):
        self.name = name
        self.server_addr = (server_host, server_port)
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.running = False

    def start(self):
        self.running = True
        # Register with the server
        register_msg = Message(
            type=MessageType.REGISTER,
            sender_name=self.name
        )
        self.sock.sendto(register_msg.to_json().encode(), self.server_addr)

        # Wait for the registration response
        try:
            data, _ = self.sock.recvfrom(1024)
            response = Message.from_json(data.decode())

            if response.type == MessageType.REGISTER_RESPONSE:
                if response.content.startswith("ERROR"):
                    print(f"Registration failed: {response.content}")
                    self.stop(send_unreg=False)
                    return
                print(response.content)
            else:
                print("Unexpected response from server")
                self.stop()
                return
        except Exception as e:
            print(f"Error during registration: {e}")
            self.stop()
            return

        # Start the receiver thread
        receiver_thread = threading.Thread(target=self.receive_messages)
        receiver_thread.daemon = True
        receiver_thread.start()

        # Send messages until the client stops
        try:
            while self.running:
                usr_input = input('\033[94mSend message or type quit to exit: \033[37m \n')
                if usr_input == 'quit':
                    self.stop()
                else:
                    chat = Message(type=MessageType.CHAT,
                                   sender_name=self.name,
                                   content=usr_input
                    )
                    self.sock.sendto(chat.to_json().encode(), self.server_addr)

        # Treat a keyboard interrupt as a clean shutdown
        except KeyboardInterrupt:
            self.stop()

    def receive_messages(self):
        while self.running:
            try:
                data, addr = self.sock.recvfrom(1024)
                recieved_message = Message.from_json(data.decode())
                if recieved_message.type == MessageType.CHAT:
                    print(f'\033[92m[{recieved_message.sender_name}] {recieved_message.content}')
                    print('\033[94mSend message or type quit to exit: \033[37m')
                # Acknowledge receipt to the server
                ack = Message(type=MessageType.ACK, sender_name=self.name,
                              message_id=recieved_message.message_id
                )
                self.sock.sendto(ack.to_json().encode(), self.server_addr)

            except Exception as e:
                if self.running:
                    print(f"Error receiving message: {e}")

    def stop(self, send_unreg =True):
       if self.running:
            print("Stopping client")

            # Avoid unregistering another client after a duplicate-name rejection
            if send_unreg:
                unregister_msg = Message(type=MessageType.UNREGISTER, sender_name=self.name)
                self.sock.sendto(unregister_msg.to_json().encode(), self.server_addr)

            self.running = False
            self.sock.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='UDP Chat Client')
    parser.add_argument('username', type=str,
                      help='Your username for the chat')
    parser.add_argument('--host', type=str, default='localhost',
                      help='The server host address (default: localhost)')
    parser.add_argument('--port', type=int, default=12345,
                      help='The server port (default: 12345)')

    args = parser.parse_args()

    client = ChatClient(args.username, args.host, args.port)
    client.start()
