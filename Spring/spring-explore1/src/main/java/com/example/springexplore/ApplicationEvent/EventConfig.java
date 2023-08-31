package com.example.springexplore.ApplicationEvent;

import com.example.springexplore.ApplicationEvent.beans.BookingEventsListener;
import com.example.springexplore.ApplicationEvent.beans.EmailPublisher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {BookingEventsListener.class, EmailPublisher.class})
public class EventConfig {

}
