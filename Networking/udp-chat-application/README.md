# UDP Chat Application

This course project is a terminal chat room built with Python UDP sockets. A server registers usernames and broadcasts chat messages to the other clients. The protocol also tracks acknowledgments to explore some of the delivery problems that come with UDP.

## What I learned

- Sending and receiving UDP datagrams
- Serializing message objects as JSON
- Receiving messages on a separate thread
- Tracking registered users and pending acknowledgments
- Handling registration and unregistration messages

The protocol defines `REGISTER`, `REGISTER_RESPONSE`, `CHAT`, `ACK`, and `UNREGISTER` message types.