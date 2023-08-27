package com.example.demo;


import com.example.demo.spring.AnnotationRegisterListener;
import com.example.demo.spring.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(value = {"com.example.demo"})

public class DemoApplication {

	public static void main(String[] args) {


		SpringApplication.run(DemoApplication.class, args);

		AnnotationRegisterListener a = SpringUtils.getBean("annotationRegisterListener");


		a.setCount(123123);
		System.out.println("-----------------");
		a = SpringUtils.getBean("annotationRegisterListener");

		System.out.println(a.getCount());

	}

}
