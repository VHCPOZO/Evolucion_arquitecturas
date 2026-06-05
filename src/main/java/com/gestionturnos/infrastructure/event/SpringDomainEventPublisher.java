package com.gestionturnos.infrastructure.event;

import com.gestionturnos.application.port.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringDomainEventPublisher implements EventPublisher {

    @Override
    public void publish(Object domainEvent) {
        log.info("Domain event: {}", domainEvent);
    }
}
