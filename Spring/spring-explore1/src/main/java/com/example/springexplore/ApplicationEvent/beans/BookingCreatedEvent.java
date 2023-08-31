package com.example.springexplore.ApplicationEvent.beans;

import org.springframework.context.ApplicationEvent;



public class BookingCreatedEvent extends ApplicationEvent {

    private String booking;

    public BookingCreatedEvent(Object source) {
        super(source);
    }

    public BookingCreatedEvent(Object source, String booking) {
        super(source);
        this.booking = booking;
    }

    public String getBooking() {
        return booking;
    }
}
