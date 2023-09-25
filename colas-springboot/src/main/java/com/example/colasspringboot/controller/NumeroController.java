package com.example.colasspringboot.controller;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emergencias-insertar")
public class NumeroController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange exchange;
    @Autowired
    private FanoutExchange fanoutExchange;
    @Autowired
    private TopicExchange topicExchange;
    @PostMapping("/colas")
    public String numeroColas(@RequestBody EmergenciasModeloJZ numeroModeloJZ) {
        String tipoEmergencia = numeroModeloJZ.getTipo_emergencia();

        if ("Incendio".equalsIgnoreCase(tipoEmergencia)) {
            //rabbitTemplate.convertAndSend(exchange.getName(), "bomberosJZ", numeroModeloJZ);
            //rabbitTemplate.convertAndSend(exchange.getName(), "ambulanciasJZ", numeroModeloJZ);
            rabbitTemplate.convertAndSend(topicExchange.getName(), "bomberos.incendio", numeroModeloJZ);
            rabbitTemplate.convertAndSend(topicExchange.getName(), "ambulancias.incendio", numeroModeloJZ);
            return "Mensaje enviado a las colas de Bomberos y Ambulancias para Incendio";
        } else if ("Robo".equalsIgnoreCase(tipoEmergencia)) {
            rabbitTemplate.convertAndSend(exchange.getName(), "policiaJZ", numeroModeloJZ);
            return "Mensaje enviado a la cola de Policias";
        } else if ("Accidente".equalsIgnoreCase(tipoEmergencia)) {
            rabbitTemplate.convertAndSend(exchange.getName(), "ambulanciasJZ", numeroModeloJZ);
            return "Mensaje enviado a la cola de Ambulancias";
        } else if ("Todos".equalsIgnoreCase(tipoEmergencia)) {
            rabbitTemplate.convertAndSend(fanoutExchange.getName(),"", numeroModeloJZ);

            return "Mensaje enviado a todas las colas";
        }

        return "Tipo de emergencia no reconocido";
    }
}
