package com.example.colasspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigColas {
    private static final String colas_bomberosJZ ="cola-bomberosJZ";
    private static final String colas_ambulanciasJZ ="cola-ambulanciasJZ";
    private static final String colas_policiaJZ ="cola-policiaJZ";
    public static final String ROUTING_A = "bomberosJZ";
    public static final String ROUTING_B = "ambulanciasJZ";
    public static final String ROUTING_C = "policiaJZ";

    private static final String EXCHANGE_NAME = "exchange-topic";

    /*Creacion de las colas*/
    @Bean
    public Queue colaBomberosJZ() {
        return new Queue(colas_bomberosJZ, false);
    }
    @Bean
    public Queue colaAmbulanciaJZ() {
        return new Queue(colas_ambulanciasJZ, false);
    }
    @Bean
    public Queue colaPoliciaJZ() {
        return new Queue(colas_policiaJZ, false);
    }

    /*CRQACION TOPIX*/
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange-fanout");
    }


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("exchange-numeros");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    //Bindign
    @Bean
    public Binding bindingBomnberos(Queue colaBomberosJZ, DirectExchange exchange) {
        return BindingBuilder.bind(colaBomberosJZ).to(exchange).with(ROUTING_A);
    }
    @Bean
    public Binding bindingAmbulancia(Queue colaAmbulanciaJZ, DirectExchange exchange) {
        return BindingBuilder.bind(colaAmbulanciaJZ).to(exchange).with(ROUTING_B);
    }
    @Bean
    public Binding bindingPolicia(Queue colaPoliciaJZ, DirectExchange exchange) {
        return BindingBuilder.bind(colaPoliciaJZ).to(exchange).with(ROUTING_C);
    }

    @Bean
    public Binding bindingBomberosTodos(Queue colaBomberosJZ, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(colaBomberosJZ).to(fanoutExchange);
    }
    @Bean
    public Binding bindingAmbulanciaTodos(Queue colaAmbulanciaJZ, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(colaAmbulanciaJZ).to(fanoutExchange);
    }
    @Bean
    public Binding bindingPoliciaTodos(Queue colaPoliciaJZ, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(colaPoliciaJZ).to(fanoutExchange);
    }


    @Bean
    public Binding bindingBomberosIncendio(Queue colaBomberosJZ, TopicExchange topicExchange) {
        return BindingBuilder.bind(colaBomberosJZ).to(topicExchange).with("bomberos.*");
    }

    @Bean
    public Binding bindingAmbulanciasIncendio(Queue colaAmbulanciaJZ, TopicExchange topicExchange) {
        return BindingBuilder.bind(colaAmbulanciaJZ).to(topicExchange).with("ambulancias.*");
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
