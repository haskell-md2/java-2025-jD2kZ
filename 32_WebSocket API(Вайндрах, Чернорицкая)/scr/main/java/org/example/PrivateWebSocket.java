package org.example;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/private")
public class PrivateWebSocket {
    private static final ConcurrentMap<String, Session> userSessions = new ConcurrentHashMap<>();
    private static final AtomicInteger userCounter = new AtomicInteger(0);
    private static final ConcurrentMap<String, Integer> sessionToUserId = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        int userId = userCounter.incrementAndGet();
        sessionToUserId.put(session.getId(), userId);
        userSessions.put(session.getId(), session);

        System.out.println("Private session opened for User " + userId + ": " + session.getId());
        sendToSession(session, "Your ID: " + userId + ". Use format: user123:message");
    }

    @OnMessage
    public void onMessage(String message, Session sender) {
        Integer userId = sessionToUserId.get(sender.getId());
        if (userId == null) return;

        System.out.println("Private message from User " + userId + ": " + message);

        if (message.contains(":")) {
            String[] parts = message.split(":", 2);
            String targetUserId = parts[0].trim();
            String privateMessage = parts[1].trim();

            try {
                int targetId = Integer.parseInt(targetUserId);
                sendPrivateMessage(userId, targetId, privateMessage);
            } catch (NumberFormatException e) {
                sendToSession(sender, "Invalid user ID format");
            }
        } else {
            sendToSession(sender, "Use format: user123:your_message");
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        Integer userId = sessionToUserId.get(session.getId());
        if (userId != null) {
            userSessions.remove(session.getId());
            sessionToUserId.remove(session.getId());
            System.out.println("Private session closed for User " + userId + ": " + session.getId());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("Private chat error for " + session.getId() + ": " + error.getMessage());
    }

    private void sendPrivateMessage(int fromUserId, int toUserId, String message) {
        Session targetSession = findSessionByUserId(toUserId);
        Session fromSession = findSessionByUserId(fromUserId);

        if (targetSession != null && targetSession.isOpen() && fromSession != null) {

            String receivedMsg = String.format("[User %s -> You] : %s", fromUserId, message);
            sendToSession(targetSession, receivedMsg);

            String sentMsg = String.format("[You -> User %s] : %s", toUserId, message);
            sendToSession(fromSession, sentMsg);

            System.out.println("Private message from User " + fromUserId + " to User " + toUserId);
        } else {
            String errorMsg = String.format("User %s not found or offline", toUserId);
            if (fromSession != null) {
                sendToSession(fromSession, errorMsg);
            }
        }
    }

    private Session findSessionByUserId(int userId) {
        for (var entry : sessionToUserId.entrySet()) {
            if (entry.getValue() == userId) {
                return userSessions.get(entry.getKey());
            }
        }
        return null;
    }

    private void sendToSession(Session session, String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                System.err.println("Failed to send private message to " + session.getId());
            }
        }
    }
}