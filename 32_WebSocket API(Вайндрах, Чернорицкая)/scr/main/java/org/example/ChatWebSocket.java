package org.example;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/chatroom")
public class ChatWebSocket {
    private static final ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static final AtomicInteger userCounter = new AtomicInteger(0);
    private static final ConcurrentMap<String, Integer> sessionToUserId = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        int userId = userCounter.incrementAndGet();
        sessionToUserId.put(session.getId(), userId);
        sessions.put(session.getId(), session);

        System.out.println("User " + userId + " joined chat: " + session.getId());
        broadcastMessage("System", "User " + userId + " joined the chat");
    }

    @OnMessage
    public void onMessage(String message, Session sender) {
        Integer userId = sessionToUserId.get(sender.getId());
        if (userId != null) {
            System.out.println("Chat message from User " + userId + ": " + message);
            broadcastMessage("User " + userId, message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        Integer userId = sessionToUserId.get(session.getId());
        if (userId != null) {
            sessions.remove(session.getId());
            sessionToUserId.remove(session.getId());
            System.out.println("User " + userId + " left chat: " + session.getId());
            broadcastMessage("System", "User " + userId + " left the chat");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("Chat error for " + session.getId() + ": " + error.getMessage());
    }

    private void broadcastMessage(String sender, String message) {
        String formattedMessage;

        if (sender.startsWith("System")) {
            formattedMessage = String.format("[System] : %s", message);
        } else {
            // Извлекаем ID пользователя из "User X"
            String userId = sender.replace("User ", "");
            formattedMessage = String.format("[User %s] : %s", userId, message);
        }

        sessions.entrySet().parallelStream()
                .filter(entry -> entry.getValue().isOpen())
                .forEach(entry -> {
                    Session session = entry.getValue();
                    try {
                        session.getAsyncRemote().sendText(formattedMessage);
                    } catch (Exception e) {
                        System.err.println("Failed to send message to " + entry.getKey());
                        sessions.remove(entry.getKey());
                    }
                });
    }
}