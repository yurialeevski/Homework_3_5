
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;
public class Application {
    public static void main(String[] args) {
        /*  1.Добавьте зависимость hibernate-core версии 6.2.1.Final в файл pom.xml.
            2.Замените в классе Employee поле city с типа City на тип int.
            3.Приведите класс Employee ко всем критериям Entity. Используйте все необходимые аннотации.
            4.Создайте конфигурационный файл persistence.xml в каталоге src/main/resources/META-INF.
            5.Реализуйте класс со статическим методом чтения конфигурационного файла.
            6.Скорректируйте в интерфейсе EmployeeDAO методы удаления и изменения, они должны принимать объект типа
              Employee.
            7.Измените методы класса EmployeeDAOImpl так, чтобы они реализовывались через Hibernate.
            8.Измените класс Application так, чтобы он реализовывался через Hibernate.
        */

        //Тестирование методов, реализованных в EmployeeDAOIml
        task_1();

        //Настройка Hibernate
        //task_3();
    }
    public static void task_1() {
        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

        EntityManager entityManager = JPAUtility.getEntityManager();

        System.out.println("persist() Создание (добавление) сущности Employee в таблицу");
        employeeDAO.createEmployee(entityManager, "Семен", "Семенов", "муж", 55, 3);

        System.out.println("\n find() Получение конкретного объекта Employee по id");
        Employee employee = employeeDAO.getEmployeeById(entityManager, 20);
        printEmployee(employee);


        System.out.println("\njpql- запрос Получение списка всех объектов Employee из базы");
        List<Employee> employeeList = employeeDAO.getAllEmployees(entityManager);
        for (Employee employee_1 : employeeList) {
            printEmployee(employee_1);
        }

        System.out.println("\nmerge() Изменение конкретного объекта Employee в базе по id.");
        Employee employeeToUpdate = new Employee(20, "Вера", "Козлова", "жен", 42, 3);
        employeeDAO.updateEmployeeById(entityManager, employeeToUpdate.getId(), employeeToUpdate);
        Employee employeeUpdated = employeeDAO.getEmployeeById(entityManager, 17);
        printEmployee(employeeUpdated);

        System.out.println("\nremove() Удаление конкретного объекта Employee из базы по id.");
        Employee employeeToDelete = employeeList.get(6);
        employeeDAO.deleteEmployeeById(entityManager, employeeToDelete.getId(), employeeToDelete);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        employeeList = employeeDAO.getAllEmployees(entityManager);
        for (Employee employee_2 : employeeList) {
            printEmployee(employee_2);
        }
        entityManager.close();
        JPAUtility.close();
    }
    public static void task_3() {
        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
        EntityManager entityManager = JPAUtility.getEntityManager();

        // Начинаем транзакцию
        entityManager.getTransaction().begin();

        // Создаем JPQL-запрос для выборки всех служащих
        String jpqlQuery = "SELECT e FROM Employee e WHERE e.age > 50";

        // Создаем объект запроса с указанием типа возвращаемого результата
        // (Employee.class)
        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);

        // Выполняем запрос и получаем результат в виде списка сотрудников
        List<Employee> employees = query.getResultList();

        // Завершаем транзакцию
        entityManager.getTransaction().commit();

        // Выводим информацию о сотрудниках в консоль
        for (Employee employee : employees) {
            printEmployee(employee);
        }

        entityManager.close();
        //entityManagerFactory.close();
        JPAUtility.close();
    }

    public static void printEmployee(Employee employee) {
        if(employee == null) {
            System.out.println("Сотрудника с таким ID нет в базе данных");
        } else {
        System.out.println("ID сотрудника : " + employee.getId() +
                " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                " ID города: " + employee.getCityId());
        }
    }
}
