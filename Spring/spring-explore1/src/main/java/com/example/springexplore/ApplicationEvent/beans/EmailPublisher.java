package com.example.springexplore.ApplicationEvent.beans;

import com.example.springexplore.ApplicationEvent.beans.BookingCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EmailPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher=applicationEventPublisher;
    }

    public void sendMessage(String message) {
        publisher.publishEvent(new BookingCreatedEvent(this,message));
    }
}
