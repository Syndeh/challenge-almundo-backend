package almundo.challenge.callcenter.core;

import almundo.challenge.callcenter.domain.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Dispacher {


    private static final Log logger = LogFactory.getLog(Dispacher.class);


    //Lista de empleados, si bien son del mismo tipo, estan ordenado por distinta categoria ya que no hay diferencia en cada uno.
    //no me parecio necesario implementar una herencia por mas simple que sea.
    private List<Employee> operators = Arrays.asList(new Employee("David"),new Employee("Dario"),new Employee("Daniela"));
    private List<Employee> supervisors = Arrays.asList(new Employee("Alan"),new Employee("Alicia"));
    private List<Employee> directors = Arrays.asList(new Employee("Roberto"));

    /**
     * dispatchCall recive una identificador de llamada para ver que llamada se esta atendiendo
     *
     * obtiene un empleado
     * luego hace que conteste la llamada
     * @Asysc para ejecutar el metodo asincronicamente y limitarlo a 10 configurando el bean con spring
     */
    @Async
    public void dispatchCall(Integer llamada) throws InterruptedException {
        logger.info("designando empleado para la llamada: " + llamada);
        Employee employee = this.getEmployee();
        logger.info(employee.getName() + " esta atendiendo la llamada: " + llamada);
        employee.handleCall();
        logger.info(employee.getName() + " termino la llamada: " + llamada);
    }

    /**
     * busca dentro de la lista de empleados si alguno esta libre.
     * quedandose con el primero que encuentra libre, si no hay ninguno libre para a la siguiente lista
     * operators -> supervisors -> directors
     * si no existe ninguno libre se vuelve a llamar recursivamente
     * @return Employee
     * @throws InterruptedException
     */
    private synchronized Employee getEmployee() throws InterruptedException {
        Employee employeeFree;
        logger.info("buscando operador");
        Optional<Employee> operatorOptional = findFree(this.getOperators());

        if(operatorOptional.isPresent()){
            employeeFree = changeStatus(operatorOptional.get());
        } else {
            logger.info("buscando supervisor");
            Optional<Employee> supervisorOptional = findFree(this.getSupervisors());
            if (supervisorOptional.isPresent()){
                employeeFree = changeStatus(supervisorOptional.get());
            } else {
                logger.info("buscando director");
                Optional<Employee> directorOptional = findFree(this.getDirectors());
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

    /**
     * cambia el estado del empleado y lo retorna
     * @param employee
     * @return Employee
     */
    private Employee changeStatus(Employee employee) {
        employee.setFree(false);
        return employee;
    }

    /**
     * recibe una lista de empleados, filtra al que esta libre y devuelve el primero
     * @param employees
     * @return Optional<Employee>
     */
    private static Optional<Employee> findFree(List<Employee> employees) {
        return employees.stream().filter(Employee::getFree).findFirst();
    }

    public List<Employee> getOperators() {
        return operators;
    }

    public List<Employee> getSupervisors() {
        return supervisors;
    }

    public List<Employee> getDirectors() {
        return directors;
    }
}
