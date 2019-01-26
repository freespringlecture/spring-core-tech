package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    MessageSource messageSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        System.out.println(messageSource.getClass());

        while (true) {
            System.out.println(messageSource.getMessage("greeting", new String[]{"freelife"}, Locale.KOREA));
            System.out.println(messageSource.getMessage("greeting", new String[]{"freelife"}, Locale.US));
            Thread.sleep(1000l);
        }
    }
}
