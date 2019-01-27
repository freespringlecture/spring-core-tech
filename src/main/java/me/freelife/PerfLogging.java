package me.freelife;

import java.lang.annotation.*;

/**
 * 이 애노테이션을 사용하면 성능을 로깅해 줍니다
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface PerfLogging {
}
