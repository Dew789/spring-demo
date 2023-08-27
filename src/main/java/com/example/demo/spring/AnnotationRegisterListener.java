package com.example.demo.spring;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class AnnotationRegisterListener {

    private int count  = 0;

}
