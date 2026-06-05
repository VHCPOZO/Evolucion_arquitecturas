package com.gestionturnos.application.port;

public interface EventPublisher {

    void publish(Object domainEvent);
}
