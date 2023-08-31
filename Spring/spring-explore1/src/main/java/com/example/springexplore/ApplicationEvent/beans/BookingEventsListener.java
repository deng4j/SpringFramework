package com.example.springexplore.ApplicationEvent.beans;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventsListener implements ApplicationListener<BookingCreatedEvent> {

    /**
     * 实现ApplicationListener方式
     */
    @Override
    public void onApplicationEvent(BookingCreatedEvent event) {
        System.out.println("\033[32m" + "ApplicationListener：" +event.getBooking()+ "\033[0m");
    }

    /**
     * 基于注解方式
     */
    @EventListener()
    public void processBlackListEvent(BookingCreatedEvent event) {
        System.out.println("\033[32m" + "@EventListener：" +event.getBooking()+ "\033[0m");
    }

    @EventListener(classes = {BookingCreatedEvent.class})
    public void processBlackListEventStart() {
        System.out.println("\033[32m" + "@EventListener(classes = {BookingCreatedEvent.class})：" + "\033[0m");
    }

    @EventListener(condition = "#event.booking == 'bookingggg'")
    public void processBlackListEventCondition(BookingCreatedEvent event) {
        System.out.println("\033[32m" + "@EventListener(condition = \"#event.booking == 'bookingggg'\")：" + "\033[0m");
    }
}
