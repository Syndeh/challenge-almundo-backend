package almundo.challenge.callcenter.config;

import almundo.challenge.callcenter.core.Dispacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ComponentScan("almundo.challenge.callcenter.core")
@EnableAsync
public class AsyncConfig {

    @Bean
    public Dispacher dispacher () {
        return new Dispacher();
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("RunningTask-");
        executor.initialize();
        return executor;
    }
}
