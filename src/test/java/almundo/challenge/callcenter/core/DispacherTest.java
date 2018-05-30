package almundo.challenge.callcenter.core;

import almundo.challenge.callcenter.config.AsyncConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes= {AsyncConfig.class},loader = AnnotationConfigContextLoader.class)
public class DispacherTest {

    @Autowired
    Dispacher dispacher;

    @Test
    public void dispatchtenCallsTest() throws ExecutionException, InterruptedException {
        List<Integer> llamadas = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10);

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

    @Test
    public void dispatchMoreTenCallsTest() throws ExecutionException, InterruptedException {
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

    @Test
    public void dispatchCallOperatorTest() throws InterruptedException {
        dispacher.dispatchCall(1);
    }

    @Test
    public void dispatchCallSupervisorTest() throws InterruptedException {
        dispacher.getOperators().forEach(employee -> employee.setFree(false));

        dispacher.dispatchCall(1);
    }

    @Test
    public void dispatchCallDirectorTest() throws InterruptedException {
        dispacher.getOperators().forEach(employee -> employee.setFree(false));
        dispacher.getSupervisors().forEach(employee -> employee.setFree(false));

        dispacher.dispatchCall(1);
    }
}
