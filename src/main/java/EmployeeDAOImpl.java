import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    public EmployeeDAOImpl() {

    }

    @Override
    public void createEmployee(EntityManager entityManager, String firstName, String lastName, String gender, int age, int cityId) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setAge(age);
        employee.setCityId(cityId);

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Employee> getAllEmployees(EntityManager entityManager) {
        List<Employee> employees = new ArrayList<>();

        //EntityManager entityManager = JPAUtility.getEntityManager();

        // Начинаем транзакцию
        entityManager.getTransaction().begin();

        // Создаем JPQL-запрос для выборки всех служащих
        String jpqlQuery = "SELECT e FROM Employee e";

        // Создаем объект запроса с указанием типа возвращаемого результата
        // (Employee.class)
        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);

        // Выполняем запрос и получаем результат в виде списка сотрудников
        employees = query.getResultList();

        // Завершаем транзакцию
        entityManager.getTransaction().commit();

        //entityManager.close();
        //JPAUtility.close();
        return employees;
    }

    @Override
    public Employee getEmployeeById(EntityManager entityManager, int id) {
        Employee employee = null;
        entityManager.getTransaction().begin();
        employee = entityManager.find(Employee.class, new Integer(id));
        entityManager.getTransaction().commit();
        return employee;
    }

    @Override
    public void updateEmployeeById(EntityManager entityManager, int id, Employee employee) {
        entityManager.getTransaction().begin();
        Employee employeeCheck = entityManager.find(Employee.class, new Integer(id));
        if(entityManager.contains(employeeCheck)) {
            entityManager.merge(employee);
            System.out.println("Сотрудник ID: " + id + " обновлен");
        } else {
            System.out.println("Сотрудник с ID: " + id + " не найден в базе данных");
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteEmployeeById(EntityManager entityManager, int id, Employee employee) {
        entityManager.getTransaction().begin();
        Employee employeeCheck = entityManager.find(Employee.class, new Integer(id));
        if(entityManager.contains(employeeCheck)) {
            entityManager.remove(employee);
            System.out.println("Сотрудник ID: " + id + " удален");
        } else {
            System.out.println("Сотрудник с ID: " + id + " не найден в базе данных");
        }
        entityManager.getTransaction().commit();
    }
}
