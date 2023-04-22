import javax.persistence.EntityManager;
import java.util.List;

public interface EmployeeDAO {
    void createEmployee(EntityManager entityManager, String firstName, String lastName, String gender, int age, int cityId);
    List<Employee> getAllEmployees(EntityManager entityManager);
    Employee getEmployeeById(EntityManager entityManager, int id);
    void deleteEmployeeById(EntityManager entityManager, int id, Employee employee);
    void updateEmployeeById(EntityManager entityManager, int id, Employee employee);
}
