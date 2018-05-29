package almundo.challenge.callcenter.core;

import almundo.challenge.callcenter.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Dispacher {


    private static final Logger logger = LoggerFactory.getLogger(Dispacher.class);

    private List<Employee> operators = Arrays.asList(new Employee("David"),new Employee("Dario"),new Employee("Daniela"));


    private List<Employee> supervisors = Arrays.asList(new Employee("Alan"),new Employee("Alicia"));


    private List<Employee> directors = Arrays.asList(new Employee("Roberto"));

    public void dispatchCall(Integer llamada) throws InterruptedException {
        logger.info("designando empleado para la llamada: " + llamada);
        Employee employee = this.getEmployee();
        logger.info(employee.getName() + " esta atendiendo la llamada: " + llamada);
        employee.handleCall();
        logger.info(employee.getName() + " termino la llamada: " + llamada);
    }

    private synchronized Employee getEmployee() throws InterruptedException {
        Employee employeeFree;
        logger.info("buscando operador");
        Optional<Employee> operatorOptional = findFree(operators);

        if(operatorOptional.isPresent()){
            employeeFree = changeStatus(operatorOptional.get());
        } else {
            logger.info("buscando supervisor");
            Optional<Employee> supervisorOptional = findFree(supervisors);
            if (supervisorOptional.isPresent()){
                employeeFree = changeStatus(supervisorOptional.get());
            } else {
                logger.info("buscando director");
                Optional<Employee> directorOptional = findFree(directors);
                if(directorOptional.isPresent()){
                    employeeFree = changeStatus(directorOptional.get());
                } else {
                    Thread.sleep(1000);
                    employeeFree = this.getEmployee();
                }
            }
        }
        return employeeFree;
    }

    private Employee changeStatus(Employee employee) {
        employee.setFree(false);
        return employee;
    }

    private static Optional<Employee> findFree(List<Employee> employees) {
        return employees.stream().filter(Employee::getFree).findAny();
    }

}
