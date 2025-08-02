package com.bookstore;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    @Before("execution(* com.bookstore.controller.BookController.addBook(..))")
    public void logger() {
        System.out.println("Before method");
    }

    @After("execution(* *.*.*.addBook(..))")
    public void afterLogger() {
        System.out.println("After method");
    }

}
