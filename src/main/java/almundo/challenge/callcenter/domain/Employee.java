package almundo.challenge.callcenter.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Employee {

    private Boolean free;

    private String name;

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    public Employee(String name) {
        this.name = name;
        this.free = true;
    }

    /**
     * Se obtiene un numero entre 5 y 10 para esperar esa cantidad de segundos
     * y luego se setea el empleado liberado
     * @throws InterruptedException
     */
    public void handleCall() throws InterruptedException {
        Integer randomNum = ThreadLocalRandom.current().nextInt(5, 11);
        logger.info("Handle call ...");
        Thread.sleep((1000 * randomNum));
        this.setFree(true);
        logger.info("Finish call.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
