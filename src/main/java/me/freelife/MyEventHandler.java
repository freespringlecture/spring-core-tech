package me.freelife;

import org.springframework.context.event.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class MyEventHandler {

    @EventListener
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Async
    public void handle(MyEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("이벤트 받았다. 데이터는 " + event.getData());
    }

    /**
     * ContextRefreshedEvent: ApplicationContext를 초기화 했거나 리프래시 했을 때 발생
     * @param event
     */
    @EventListener
    @Async
    public void handle(ContextRefreshedEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("ContextRefreshedEvent");
    }

    /**
     * ContextStartedEvent
     * ApplicationContext를 start()하여 라이프사이클 빈 들이 시작신호를 받은시점에 발생
     * @param event
     */
    @EventListener
    @Async
    public void handle(ContextStartedEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("ContextStartedEvent");
    }

    /**
     * ContextStoppedEvent
     * ApplicationContext를 stop()하여 라이프사이클 빈 들이 정지신호를 받은시점에 발생
     * @param event
     */
    @EventListener
    @Async
    public void handle(ContextStoppedEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("ContextStoppedEvent");
    }

    /**
     * ContextClosedEvent
     * ApplicationContext를 close()하여 싱글톤 빈 소멸 되는시점에 발생
     * @param event
     */
    @EventListener
    @Async
    public void handle(ContextClosedEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("ContextClosedEvent");
    }

    /**
     * RequestHandledEvent
     * HTTP 요청을 처리 했을 때 발생
     * @param event
     */
    @EventListener
    @Async
    public void handle(RequestHandledEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("RequestHandledEvent");
    }
}
