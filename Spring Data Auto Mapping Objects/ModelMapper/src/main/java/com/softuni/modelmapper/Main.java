package com.softuni.modelmapper;

import com.softuni.modelmapper.entities.Address;
import com.softuni.modelmapper.entities.Employee;
import com.softuni.modelmapper.entities.dtos.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Main implements CommandLineRunner {
ModelMapper mapper = new ModelMapper();
    @Override
    public void run(String... args) throws Exception {
       Address address = new Address("Bulgaria","Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN,address);

       EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

       // EmployeeDto employeeDto ?
        System.out.println(employeeDTO);
    }
}
