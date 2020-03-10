package seniorsathome.seniorsathomespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class SeniorsathomespringApplication {

    private static final Logger log = Logger.getLogger(SeniorsathomespringApplication .class.getName());
    public static void main(String[] args) {
        new SpringApplicationBuilder(SeniorsathomespringApplication .class).run(args);
    }

    /*public static void main(String[] args) {
        SpringApplication.run(SeniorsathomespringApplication.class, args);
    }*/

}
