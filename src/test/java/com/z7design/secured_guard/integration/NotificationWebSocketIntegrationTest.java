package com.z7design.secured_guard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.User;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.model.enums.NotificationType;
import com.z7design.secured_guard.repository.NotificationRepository;
import com.z7design.secured_guard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NotificationWebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private WebSocketStompClient stompClient;
    private StompSession stompSession;
    private User testUser;

    @BeforeEach
    void setUp() throws Exception {
        // Configurar cliente WebSocket
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // Criar usuário de teste
        testUser = User.builder()
                .username("testuser_" + UUID.randomUUID())
                .email("test@example.com")
                .password("password123")
                .build();
        testUser = userRepository.save(testUser);

        // Conectar ao WebSocket
        String url = "ws://localhost:" + port + "/ws";
        stompSession = stompClient.connect(url, new StompSessionHandlerAdapter() {}).get(5, TimeUnit.SECONDS);
    }

    @Test
    void testNotificationWebSocketDelivery() throws Exception {
        // Configurar fila para receber mensagens
        BlockingQueue<Notification> receivedNotifications = new LinkedBlockingQueue<>();

        // Subscrever ao tópico de notificações do usuário
        stompSession.subscribe("/user/queue/notifications", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Notification.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                receivedNotifications.offer((Notification) payload);
            }
        });

        // Criar e salvar notificação
        Notification notification = Notification.builder()
                .user(testUser)
                .title("Teste WebSocket")
                .message("Mensagem de teste para WebSocket")
                .type(NotificationType.INFO)
                .status(NotificationStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        // Verificar se a notificação foi recebida via WebSocket
        Notification receivedNotification = receivedNotifications.poll(10, TimeUnit.SECONDS);
        assertNotNull(receivedNotification);
        assertEquals("Teste WebSocket", receivedNotification.getTitle());
        assertEquals("Mensagem de teste para WebSocket", receivedNotification.getMessage());
    }

    @Test
    void testGlobalNotificationBroadcast() throws Exception {
        BlockingQueue<Notification> receivedNotifications = new LinkedBlockingQueue<>();

        // Subscrever ao tópico global
        stompSession.subscribe("/topic/notifications", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Notification.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                receivedNotifications.offer((Notification) payload);
            }
        });

        // Enviar notificação global via endpoint
        stompSession.send("/app/broadcast", "Notificação Global de Teste");

        // Verificar recebimento
        Notification receivedNotification = receivedNotifications.poll(10, TimeUnit.SECONDS);
        assertNotNull(receivedNotification);
    }
}