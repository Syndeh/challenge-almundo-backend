package almundo.challenge.callcenter;

import almundo.challenge.callcenter.config.AsyncConfig;
import almundo.challenge.callcenter.core.Dispacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@SpringBootApplication
public class CallCenterApplication {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AsyncConfig.class);


		Dispacher dispacher =  context.getBean(Dispacher.class);


		List<Integer> llamadas = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,13,14,15);


		ForkJoinPool forkJoinPool = new ForkJoinPool(15);
		forkJoinPool.submit(() ->

				//parallel stream invoked here
				llamadas.parallelStream().forEach(llamada -> {
							try {
								dispacher.dispatchCall(llamada);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
				)
		).get();

	}

}
