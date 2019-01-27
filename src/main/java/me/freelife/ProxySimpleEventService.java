package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Proxy 클래스
 */
@Primary
@Service
public class ProxySimpleEventService implements EventService {

    /**
     * Real Subject
     */
    @Autowired
    SimpleEventService simpleEventService;

    /**
     * Real Subject를 위임에서 일을 처리
     */
    @Override
    public void createEvent() {
        /**
         * 부가적인 기능을 추가
         */
        long begin = System.currentTimeMillis();
        simpleEventService.createEvent();
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Override
    public void publishEvent() {
        long begin = System.currentTimeMillis();
        simpleEventService.publishEvent();
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Override
    public void deleteEvent() {
        simpleEventService.deleteEvent();
    }
}
