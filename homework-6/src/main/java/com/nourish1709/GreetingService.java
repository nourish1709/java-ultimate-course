package com.nourish1709;

public class GreetingService {

    public void hello() {
        System.out.println("hello");
    }

    @LogInvocation
    public void gloryToUkraine() {
        System.out.println("Slava Ukraini");
    }
}
