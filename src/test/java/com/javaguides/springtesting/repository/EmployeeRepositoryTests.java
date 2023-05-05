package com.javaguides.springtesting.repository;

import com.javaguides.springtesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setEmployeeObject(){
        employee = Employee.builder()
                .firstName("Suren")
                .lastName("Siva")
                .email("suren123@gmail.com")
                .build();
    }

    //save functionality
    @DisplayName("Save operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition/setup

        //when - action or behaviour
        Employee savedEmployee = employeeRepository.save(employee);

        //then - output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Find All functionality")
    public void givenEmployeesList_whenFindAll_thenReturnListOfEmployess(){
        //given employee objects
        Employee employee1 = Employee.builder()
                .firstName("Sangee")
                .lastName("Dharshik")
                .email("sangee123@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when get all or find all
        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Get employee by Id")
    public void givenEmployeeObject_whenFindById_thenReturnEmployee(){

       employeeRepository.save(employee);

        //WHEN
        Employee findById = employeeRepository.findById(employee.getId()).get();

        //THEN
        assertThat(findById).isNotNull();
    }

    @Test
    @DisplayName("Get employee by email")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        Employee employeeByEmail = employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employeeByEmail).isNotNull();
    }

    @Test
    @DisplayName("Update opertaion")
    public void givenEmployeeObject_whenUpdate_thenReturnUpdatedEmployee(){

        employeeRepository.save(employee);

        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("sangee@abc");
        savedEmployee.setLastName("dharshik");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        assertThat(updatedEmployee.getEmail()).isEqualTo("sangee@abc");
        assertThat(updatedEmployee.getLastName()).isEqualTo("dharshik");

    }

    @Test
    @DisplayName("Delete operation")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployeeObject(){

        employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());

        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();

    }

    @Test
    @DisplayName("Custom query using JPQL index params")
    public void givenFirstNameAndLastName_whenFindByJPQLIndexParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);
        String firstName = "Suren";
        String lastName = "Siva";

        Employee savedEmployee = employeeRepository.findByJPQLIndexParams(firstName, lastName);

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    @DisplayName("Custom query using JPQL named params")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);
        String firstName = "Suren";
        String lastName = "Siva";

        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    @DisplayName("Custom query using Native SQL query index params")
    public void givenFirstNameAndLastName_whenFindByNativeSQLIndexParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);
//        String firstName = "Suren";
//        String lastName = "Siva";

        Employee savedEmployee = employeeRepository.findByNativeSQLIndex(employee.getFirstName(), employee.getLastName());

        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("Custom query using Native SQL query named params")
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);
//        String firstName = "Suren";
//        String lastName = "Siva";

        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        assertThat(savedEmployee).isNotNull();
    }

























}
