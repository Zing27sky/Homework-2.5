package pro.sky.homeworkemployeebook.service;

import org.springframework.stereotype.Service;
import pro.sky.homeworkemployeebook.exception.EmployeeAlreadyAddedException;
import pro.sky.homeworkemployeebook.exception.EmployeeNotFoundException;
import pro.sky.homeworkemployeebook.exception.EmployeeStorageIsFullException;
import pro.sky.homeworkemployeebook.model.Employee;

@Service
public class EmployeeService {
    private static final int EMPLOYEE_COUNT = 10;
    private final Employee[] employees = new Employee[EMPLOYEE_COUNT];
    private int addEmployees = 0;

    public Employee createEmployee(String firstName, String lastName) {
        if (addEmployees >= EMPLOYEE_COUNT) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        if (findEmployee(employee) >= 0) {
            throw new EmployeeAlreadyAddedException();
        }
        employees[addEmployees] = employee;
        addEmployees++;
        return employee;
    }

    public Employee removeEmployee(String firstName, String lastName) {
        Employee target = new Employee(firstName, lastName);
        int targetIndex = findEmployee(target);
        if (targetIndex < 0) {
            throw new EmployeeNotFoundException();
        }

        employees[targetIndex] = null;

        if (targetIndex != employees.length - 1) {
            System.arraycopy(employees, targetIndex + 1,
                    employees, targetIndex, employees.length - targetIndex - 1);
        }
        addEmployees--;
        return target;
    }

    public Employee findEmployee(String firstName, String lastName) {
        Employee target = new Employee(firstName, lastName);
        int targetIndex = findEmployee(target);
        if (targetIndex < 0) {
            throw new EmployeeNotFoundException();
        }
        return target;
    }

    private int findEmployee(Employee target) {
        int targetIndex = -1;
        int lastElementIndex = Math.min(addEmployees, employees.length - 1);
        for (int i = 0; i <= lastElementIndex; i++) {
            if (employees[i] != null && employees[i].equals(target)) {
                targetIndex = i;
            }
        }
        return targetIndex;
    }
}
