package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.converter.EmployeeConverter;
import net.javaguides.springboot.dto.EmployeeDto;
import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.EmployeeJdbcRepository;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeJdbcRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeConverter.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeConverter.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        // we need to check whether employee with given id is exist in DB or not
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not exists with a given id : " + employeeId)
                );

        return EmployeeConverter.mapToEmployeeDto(existingEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> EmployeeConverter.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        // we need to check whether employee with given id is exist in DB or not
        Employee existingEmployee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not exists with a given id : " + employeeDto.getId())
                );

        // convert EmployeeDto to Employee JPA entity
        Employee employee = EmployeeConverter.mapToEmployee(employeeDto);
        return EmployeeConverter.mapToEmployeeDto(employeeRepository.update(employee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        // we need to check whether employee with given id is exist in DB or not
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Employee not exists with a given id : " + employeeId)
                );

        employeeRepository.deleteById(employeeId);
    }
}
