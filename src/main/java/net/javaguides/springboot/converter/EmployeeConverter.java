package net.javaguides.springboot.converter;

import net.javaguides.springboot.dto.EmployeeDto;
import net.javaguides.springboot.entity.Employee;

public class EmployeeConverter {
    // convert Employee JPA entity to EmployeeDto
    // convert EmployeeDto to Employee JPA entity
    public static Employee mapToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
        return employee;
    }
    // convert Employee JPA entity to EmployeeDto
    // convert EmployeeDto to Employee JPA entity
    public static EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
        return employeeDto;
    }
}
