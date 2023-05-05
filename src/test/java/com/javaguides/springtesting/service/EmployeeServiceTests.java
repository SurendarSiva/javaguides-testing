package com.javaguides.springtesting.service;

import com.javaguides.springtesting.exception.ResourceNotFoundException;
import com.javaguides.springtesting.model.Employee;
import com.javaguides.springtesting.repository.EmployeeRepository;
import com.javaguides.springtesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        /*employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);*/
        employee = Employee.builder()
                .id(1L)
                .firstName("Suren")
                .lastName("Siva")
                .email("suren123@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Save Employee operation in Service class using Mockito")
    public void givenEmployeeObject_whenSaved_thenReturnEmployeeObject(){

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee))
                .willReturn(employee);

        System.out.println(employeeService);
        System.out.println(employeeRepository);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);

        assertThat(savedEmployee).isNotNull();

    }

    @Test
    @DisplayName("Save Employee operation negative scenario in Service class using Mockito")
    public void givenEmployeeObject_whenSaved_thenThrowsException(){

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeService);
        System.out.println(employeeRepository);

        assertThrows(ResourceNotFoundException.class,() ->{
            employeeService.saveEmployee(employee);
        });

        verify(employeeRepository, never()).save(any(Employee.class));

    }

    @Test
    @DisplayName("Get all employees positive scenario")
    public void givenEmployees_whenGetAll_thenReturnAllEmployees(){

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Sangee")
                .lastName("Dharshik")
                .email("dharshik@gmail.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(List.of(employee, employee1));

        List<Employee> employeeList = employeeService.getAllEmployees();
        System.out.println(employeeList);

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).hasSize(2);

    }

    @Test
    @DisplayName("Get all employees negative scenario")
    public void givenEmptyEmployeesList_whenGetAll_thenReturnNoEmployees(){

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Sangee")
                .lastName("Dharshik")
                .email("dharshik@gmail.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        List<Employee> employeeList = employeeService.getAllEmployees();
        System.out.println(employeeList);

        assertThat(employeeList).isEmpty();
        assertThat(employeeList).hasSize(0);

    }

    @Test
    @DisplayName("Get Employee by Id")
    public void givenEmployeeId_whenFindById_thenReturnEmployeeObject(){

        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.of(employee));

        //Optional<Employee> employeeById = employeeService.getEmployeeById(employee.getId());
        Employee employeeById = employeeService.getEmployeeById(employee.getId()).get();
        System.out.println(employeeById);

        assertThat(employeeById).isNotNull();


    }

    @Test
    @DisplayName("Update Employee")
    public void givenEmployeeObject_whenUpdated_thenReturnUpdatedEmployeeObject(){

        given(employeeRepository.save(employee))
                .willReturn(employee);
        employee.setEmail("suren456");
        employee.setLastName("Dharshik");

        //Optional<Employee> employeeById = employeeService.getEmployeeById(employee.getId());
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        System.out.println(updatedEmployee);

        assertThat(updatedEmployee.getLastName()).isEqualTo("Dharshik");
        assertThat(updatedEmployee.getEmail()).isEqualTo("suren456");
    }

    @Test
    @DisplayName("Delete Employee")
    public void givenEmployeeId_whenDeleted_thenRemoveEmployeeObject(){

        willDoNothing().given(employeeRepository)
                .deleteById(employee.getId());

        employeeService.deleteById(employee.getId());

        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

}
