package com.example.colasspringboot.config;

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
}
