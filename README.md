# Web-Chat Application with Javalin 6
This is a simple chat application built using Javalin version 6, showcasing websocket communication.

## Started
To run this application locally, follow these steps:

###  Prerequisites
Java Development Kit (JDK) version 17 or higher
Apache Maven

### Installation
1. Clone this repository to your local machine:

```shell
git clone https://github.com/your-username/web-chat-javalin.git
```
2. Navigate to the project directory:

```shell
cd web-chat-javalin
```

3. Build and package the application using Maven:

```shell
mvn package
```

## Running the Application
After building the application, you can run it using the following command:

```shell
java -cp target/web-chat-javalin-1.0.0-SNAPSHOT.jar com.webchat.Application
```

This command starts the Javalin server and launches the web-chat application. You can access the chat interface by opening a web browser and navigating to http://localhost:7070.

### Usage
- Open the chat application in multiple browser tabs or windows to simulate multiple users.
- Start chatting with other users in real-time using websockets.
- Enjoy the interactive chat experience!