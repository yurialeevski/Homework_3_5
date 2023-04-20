import java.sql.*;
import java.util.List;
public class Application {
    final static String user = "postgres";
    final static String password = "ya030423";
    final static String url = "jdbc:postgresql://localhost:5432/skypro";
    public static void main(String[] args) throws SQLException {
        try (final Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Соединение установлено!");

            task_1(connection);

            //task_2(connection);

        }  catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
            // Исключение для обработки возможных ошибок при подключении
        }
    }
    public static void task_1(Connection connection) throws SQLException {
//4. Получить и вывести в консоль полные данные об одном из работников(имя, фамилия, пол, город) по id.
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id = (?)")) {
            statement.setInt(1, 2);
            System.out.println("Соединение установлено!");

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int idOfEmployee = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String gender = resultSet.getString("gender");
            int age = resultSet.getInt("age");
            int cityId = resultSet.getInt("city_id");

            System.out.println("ID сотрудника: " + idOfEmployee + " \tимя: " + firstName + " \tфамилия: " + lastName +
                    " \tпол: " + gender + " \tвозраст: " + age + " \tID города: " + cityId);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
            // Исключение для обработки возможных ошибок при подключении
        }
    }
    public static void task_2(Connection connection) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

        System.out.println("Создание (добавление) сущности Employee в таблицу");
        employeeDAO.createEmployee("Тимофей", "Тимофеев", "муж", 54, 1);

        System.out.println("\nПолучение конкретного объекта Employee по id");
        Employee employee = employeeDAO.getEmployeeById(13);
        printEmployee(employee);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        List<Employee> employeeList = employeeDAO.getAllEmployees();
        for (Employee employee_1 : employeeList) {
            printEmployee(employee_1);
        }

        System.out.println("\nИзменение конкретного объекта Employee в базе по id.");
        employeeDAO.updateEmployeeById(13, "Петр", "Петров","муж",37, 1);
        Employee employeeUpdated = employeeDAO.getEmployeeById(13);
        printEmployee(employeeUpdated);

        System.out.println("\nУдаление конкретного объекта Employee из базы по id.");
        employeeDAO.deleteEmployeeById(8);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        employeeList = employeeDAO.getAllEmployees();
        for (Employee employee_2 : employeeList) {
            printEmployee(employee_2);
        }
    }

    public static void printEmployee(Employee employee) {
        System.out.println("ID сотрудника : " + employee.getId() +
                " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                " ID города: " + employee.getCityId());
    }
}
