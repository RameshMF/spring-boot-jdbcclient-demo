import net.javaguides.springboot.dto.EmployeeDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestClientTest {
    private final RestClient restClient;

    public RestClientTest() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Order(1)
    @Test
    public void createEmployee() {
        EmployeeDto newEmployee = new EmployeeDto(null, "admin", "admin", "admin123@gmail.com");

        EmployeeDto savedEmployee = restClient.post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newEmployee)
                .retrieve()
                .body(EmployeeDto.class);

        System.out.println(savedEmployee.toString());
    }

    @Order(2)
    @Test
    public void getEmployeeById() {

        Long employeeId = 4L;

        EmployeeDto employeeDto = restClient.get()
                .uri("/api/employees/{id}", employeeId)
                .retrieve()
                .body(EmployeeDto.class);

        System.out.println(employeeDto);
    }

    @Order(3)
    @Test
    public void updateEmployee() {

        Long employeeId = 4L;

        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Ramesh");
        updatedEmployee.setLastName("Fadatare");
        updatedEmployee.setEmail("ramesh@gmail.com");

        EmployeeDto result = restClient.put()
                .uri("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedEmployee)
                .retrieve()
                .body(EmployeeDto.class);

        System.out.println(result.toString());
    }

    @Order(4)
    @Test
    public void findAll() {
        List<EmployeeDto> listOfEmployees = restClient.get()
                .uri("/api/employees")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EmployeeDto>>() {});

        listOfEmployees.forEach(employeeDto -> {
            System.out.println(employeeDto.toString());
        });
    }

    @Order(5)
    @Test
    public void deleteEmployee() {
        Long employeeId = 4L;

        String response = restClient.delete()
                .uri("/api/employees/{id}", employeeId)
                .retrieve()
                .body(String.class);

        System.out.println(response);
    }

    @Test
    public void exceptionHandlingClientErrorDemo(){
        HttpClientErrorException thrown = Assertions.assertThrows(HttpClientErrorException.class,
                () -> {

                    EmployeeDto employee = restClient.get()
                            .uri("/employees/404")
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(EmployeeDto.class);
                });

        Assertions.assertEquals(404, thrown.getStatusCode().value());
    }

    @Test
    public void exceptionHandlingServerErrorDemo(){
        HttpServerErrorException thrown = Assertions.assertThrows(HttpServerErrorException.class,
                () -> {

                    EmployeeDto employee = restClient.get()
                            .uri("/api/employees/500")
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(EmployeeDto.class);
                });

        Assertions.assertEquals(500, thrown.getStatusCode().value());
    }
}
