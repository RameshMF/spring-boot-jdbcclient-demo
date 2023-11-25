package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Employee;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class EmployeeJdbcRepository {

    private final JdbcClient jdbcClient;

    public EmployeeJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Employee> findAll() {
        String sql = "select * from employees";
        return jdbcClient.sql(sql).query(Employee.class).list();
    }

    public Optional<Employee> findById(Long id) {
        String sql = "select * from employees where id = :id";
        return jdbcClient.sql(sql).param("id", id).query(Employee.class).optional();
    }

    @Transactional
    public Employee save(Employee employee) {
        String sql = "insert into employees(first_name, last_name, email) values(:first_name,:last_name,:email)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .param("first_name", employee.getFirstName())
                .param("last_name", employee.getLastName())
                .param("email", employee.getEmail())
                .update(keyHolder);
        BigInteger id = keyHolder.getKeyAs(BigInteger.class);
        employee.setId(id.longValue());
        return employee;
    }

    @Transactional
    public Employee update(Employee employee) {
        String sql = "update employees set first_name = ?, last_name = ?, email = ? where id = ?";
        int count = jdbcClient.sql(sql)
                .param(1, employee.getFirstName())
                .param(2, employee.getLastName())
                .param(3, employee.getEmail())
                .param(4, employee.getId())
                .update();
        if (count == 0) {
            throw new RuntimeException("Employee not found");
        }
        return employee;
    }

    @Transactional
    public void deleteById(Long id) {
        String sql = "delete from employees where id = ?";
        int count = jdbcClient.sql(sql).param(1, id).update();
        if (count == 0) {
            throw new RuntimeException("Employee not found");
        }
    }
}
