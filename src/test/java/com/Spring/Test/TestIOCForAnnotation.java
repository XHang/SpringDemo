package com.Spring.Test;

import com.Spring.Example.CarService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIOCForAnnotation {
    public static void main (String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("AnnotatedBeans.xml");
        CarService carService = context.getBean("carService",CarService.class);
        carService.showCar();
    }
}
