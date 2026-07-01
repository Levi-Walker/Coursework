# Networking

This folder contains two Python projects: a terminal chat program built on UDP and a distance-vector routing simulation.

## Projects

- [UDP Chat Application](./udp-chat-application/) uses a central server and a small JSON message format for registration, chat messages, acknowledgments, and unregistration.
- [Distance-Vector Routing](./distance-vector-routing/) models routers sharing tables and finding lower-cost paths over several update rounds.

## What I learned

- Sending datagrams with Python sockets
- Defining an application-level message format
- Receiving messages on a background thread
- Tracking clients and acknowledgments
- Representing routing tables, link costs, and next hops
- Applying the Bellman-Ford update rule
