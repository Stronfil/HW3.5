package com.komissarov.springstore.service;

import com.komissarov.springstore.util.Cart;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitReceiverService {

    private final String EXCHANGE_NAME = "store-exchanger";
    private Connection connection;
    private Channel channel;
    private OrderService orderService;
    private CartMapperService cartMapperService;

    @Autowired
    public void setCartMapperService(CartMapperService cartMapperService) {
        this.cartMapperService = cartMapperService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public RabbitReceiverService() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        System.out.println("Rabbit connected");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            System.out.println("wait for msg");

            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), "UTF-8");
                Cart cart = cartMapperService.deserialize(msg);
                orderService.saveOrder(cart);
                System.out.println("Receiver" + msg);
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
