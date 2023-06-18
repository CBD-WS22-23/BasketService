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

    @Value("product_added_to_basket_queue")
    private String productAddedName;

    @Value("product_added_to_basket_routing_key")
    private String productAddedRoutingKey;

    @Value("product_removed_from_basket_queue")
    private String productRemovedName;

    @Value("product_removed_from_basket_routing_key")
    private String productRemovedRoutingKey;

    @Value("basket_exchange")
    private String basketExchange;

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

    public Queue productAddedToBasketQueue() {
        return new Queue(productAddedName);
    }

    public Queue productRemovedFromBasketQueue() {
        return new Queue(productRemovedName);
    }

    public DirectExchange basketExchange() {
        return new DirectExchange(basketExchange);
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
    public Binding productAddedToBasketBinding() {
        return BindingBuilder
                .bind(productAddedToBasketQueue())
                .to(basketExchange())
                .with(productAddedRoutingKey);
    }

    @Bean
    public Binding productRemovedFromBasketBinding() {
        return BindingBuilder
                .bind(productRemovedFromBasketQueue())
                .to(basketExchange())
                .with(productRemovedRoutingKey);
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
