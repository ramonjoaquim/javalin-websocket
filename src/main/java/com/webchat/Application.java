package com.webchat;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;

public class Application {

    private static final Map<WsContext, String> userUsernameMap = new ConcurrentHashMap<>();
    private static int nextUserNumber = 1; // Assign to username for next connecting user

    public static void main(String[] args) {
        Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
            config.router.mount(router -> {
                router.ws("/chat", ws -> {
                    ws.onConnect(ctx -> {
                        String username = MessageFormat.format("User {0}", nextUserNumber++);
                        userUsernameMap.put(ctx, username);
                        broadcastMessage("Server", (MessageFormat.format("{0} joined the chat", username)));
                    });
                    ws.onClose(ctx -> {
                        String username = userUsernameMap.get(ctx);
                        userUsernameMap.remove(ctx);
                        broadcastMessage("Server", (MessageFormat.format("{0} left the chat", username)));
                    });
                    ws.onMessage(ctx -> {
                        broadcastMessage(userUsernameMap.get(ctx), ctx.message());
                    });
                });
            });
        }).start(7070);
    }

    // Sends a message from one user to all users, along with a list of current usernames
    private static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(
                    Map.of(
                            "userMessage", createHtmlMessageFromSender(sender, message),
                            "userlist", userUsernameMap.values()
                    )
            );
        });
    }

    // Builds a HTML element with a sender-name, a message, and a timestamp
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article(
                b(MessageFormat.format("{0} says:", sender)),
                span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
                p(message)
        ).render();
    }
}