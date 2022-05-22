package com.nourish1709;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class DemoUsingProxy {
    public static void main(String[] args) {
        GreetingService helloService = createMethodLoggingProxy(GreetingService.class);
        helloService.hello(); // logs nothing to the console
        helloService.gloryToUkraine(); // logs method invocation to the console
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setInterfaces(targetClass.getInterfaces());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            if (method.isAnnotationPresent(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n",
                        method.getName(), o.getClass().getSimpleName());
            }
            return methodProxy.invokeSuper(o, objects);
        });
        return targetClass.cast(enhancer.create());
    }
}