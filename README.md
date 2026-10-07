# Java MultiThreaded Server

This project demonstrates socket-based client-server communication in Java using different threading models:
- Single-threaded server
- Multi-threaded server
- Thread-pool based server using ExecutorService

It is a beginner-friendly project focused on understanding:
- ServerSocket and Socket
- client/server communication
- input/output streams
- multi-threading in network applications
- performance comparison between raw threads and thread pools

## Project Structure

- SingleThreaded/  
  Basic server that handles one client at a time.

- MultiThreaded/  
  Server creates a new thread per client connection.

- ThreadPool/  
  Server uses ExecutorService with a fixed thread pool to manage concurrent client tasks efficiently.

## Technologies Used

- Java
- Sockets
- ServerSocket
- BufferedReader
- PrintWriter
- Threads
- ExecutorService

## How it works

The server binds to a port using ServerSocket and listens for incoming client connections.  
When a client connects, the server accepts the connection and creates a communication channel using Socket.  
The client sends messages using PrintWriter and the server reads them using BufferedReader.  
The server then sends a response back using PrintWriter.

This simple architecture helps in understanding:
- how network communication works
- how concurrency is handled in Java
- why thread pools are preferred over creating one thread per request

## SingleThreaded

This is the simplest implementation. It processes one client at a time.

Use case:
- educational
- basic socket learning
- low concurrency

## MultiThreaded

This version creates a separate thread for each client connection.

Use case:
- basic concurrency
- learning thread lifecycle and client isolation

## ThreadPool

This version uses ExecutorService and a fixed worker pool.

Use case:
- better scalability
- lower overhead than creating a new thread for every request
- more production-like server design

## Run Instructions

### 1. Compile

```bash
javac SingleThreaded/*.java
javac MultiThreaded/*.java
javac ThreadPool/*.java

2. Run the server
SingleThreaded
java -cp SingleThreaded Server

MultiThreaded
java -cp MultiThreaded Server

ThreadPool
java -cp ThreadPool Server

3. Run the client
SingleThreaded
java -cp SingleThreaded Client

MultiThreaded
java -cp MultiThreaded Client


ThreadPool

java -cp ThreadPool Client

Key Learning Outcomes

Understanding of socket programming in Java
ServerSocket and Socket responsibilities
Role of accept() and connect()
Thread creation and concurrency basics
ExecutorService and thread-pool concepts
Resource management with sockets and streams

Notes
This project is intended for learning and demonstration.
It is not a production-grade HTTP server, but it provides a strong foundation for understanding server-side Java networking and concurrency.