package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ApplicationContext resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(resourceLoader.getClass());

        // 접두어로 classpath를 사용하였으므로 자동으로 ClassPathResource 타입으로 적용됨
        // Resource resource = resourceLoader.getResource("classpath:test.txt");
        // 접두어를 빼면 ServletContextResurce 타입으로 적용됨
        // 내장 톰캣은 context path가 지정되어 있지 않으므로 리소스를 찾을 수 없음
        Resource resource = resourceLoader.getResource("test.txt");
        System.out.println(resource.getClass());

        System.out.println(resource.exists());
        System.out.println(resource.getDescription());
        System.out.println(Files.readString(Path.of(resource.getURI())));
    }
}
