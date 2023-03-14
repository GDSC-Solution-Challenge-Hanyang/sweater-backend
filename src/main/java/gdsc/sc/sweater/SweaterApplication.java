package gdsc.sc.sweater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SweaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SweaterApplication.class, args);
    }

}
