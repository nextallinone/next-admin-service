package studio.clouthin.next.miniadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import studio.clouthin.next.shared.SharedConfiguration;
import studio.clouthin.next.shared.utils.SpringContextHolder;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class MiniAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniAdminApplication.class, args);
    }


    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
