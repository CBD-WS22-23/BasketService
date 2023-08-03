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

    @Value("checkout_queue")
    private String checkoutName;

    @Value("checkout_routing_key")
    private String checkoutRoutingKey;

    @Value("empty_basket_queue")
    private String emptyBasketName;

    @Value("empty_basket_routing_key")
    private String emptyBasketRoutingKey;

    @Value("checkout_exchange")
    private String checkoutExchange;

    @Bean
    public Queue productAddedToBasketQueue() {
        return new Queue(productAddedName);
    }
    @Bean
    public Queue productRemovedFromBasketQueue() {
        return new Queue(productRemovedName);
    }
    @Bean
    public DirectExchange basketExchange() {
        return new DirectExchange(basketExchange);
    }

    @Bean
    public Queue checkoutQueue() {
        return new Queue(checkoutName);
    }

    @Bean
    public Queue emptyBasketQueue() {
        return new Queue(emptyBasketName);
    }

    @Bean
    public DirectExchange checkoutExchange() {
        return new DirectExchange(checkoutExchange);
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
    public Binding checkoutBinding() {
        return BindingBuilder
                .bind(checkoutQueue())
                .to(checkoutExchange())
                .with(checkoutRoutingKey);
    }

    @Bean
    public Binding emptyBasketBinding() {
        return BindingBuilder
                .bind(emptyBasketQueue())
                .to(checkoutExchange())
                .with(emptyBasketRoutingKey);
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
