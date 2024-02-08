package pro.sky.homeworkemployeebook.service;

import org.springframework.stereotype.Service;
import pro.sky.homeworkemployeebook.exception.EmployeeAlreadyAddedException;
import pro.sky.homeworkemployeebook.exception.EmployeeNotFoundException;
import pro.sky.homeworkemployeebook.exception.EmployeeStorageIsFullException;
import pro.sky.homeworkemployeebook.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private static final int EMPLOYEE_COUNT = 10;
    private final List<Employee> employees = new ArrayList<>();

    public Employee createEmployee(String firstName, String lastName) {
        if (employees.size() >= EMPLOYEE_COUNT) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName);

        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.add(employee);
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee target = new Employee(firstName, lastName);
        if (employees.remove(target)) {
            throw new EmployeeNotFoundException();
        }
        return target;
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee target = new Employee(firstName, lastName);
        int targetIndex = employees.indexOf(target);
        if (targetIndex < 0) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(targetIndex);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }
}
