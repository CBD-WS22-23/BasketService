package edu.timebandit.BasketService.port.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("create_product_queue")
    private String createQueueName;

    @Value("create_product_routing_key")
    private String createRoutingKey;

    @Value("update_product_queue")
    private String updateQueueName;

    @Value("update_product_routing_key")
    private String updateRoutingKey;

    @Value("delete_product_queue")
    private String deleteQueueName;

    @Value("delete_product_routing_key")
    private String deleteRoutingKey;

    @Value("product_exchange")
    private String exchange;

    @Bean
    public Queue createQueue() {
        return new Queue(createQueueName);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueueName);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteQueueName);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding createBinding(){
        return BindingBuilder
                .bind(createQueue())
                .to(exchange())
                .with(createRoutingKey);
    }

    @Bean
    public Binding updateBinding(){
        return BindingBuilder
                .bind(updateQueue())
                .to(exchange())
                .with(updateRoutingKey);
    }

    @Bean
    public Binding deleteBinding(){
        return BindingBuilder
                .bind(deleteQueue())
                .to(exchange())
                .with(deleteRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
