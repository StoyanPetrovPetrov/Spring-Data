package com.softuni.modelmapper;

import com.softuni.modelmapper.entities.Address;
import com.softuni.modelmapper.entities.Employee;
import com.softuni.modelmapper.entities.dtos.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ModelMapperMain implements CommandLineRunner {
ModelMapper mapper = new ModelMapper();
    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();
  //       PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity());
//            }
//        };
//        mapper.addMappings(propertyMap);
//        mapper.validate();

        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.addMappings(mapping -> mapping.map(
        source -> source.getAddress().getCity(),
        EmployeeDTO::setAddressCity)
                );
//        typeMap.validate();

        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("First", BigDecimal.TEN, address);

        EmployeeDTO employeeDTO = typeMap.map(employee);

        System.out.println(employeeDTO);
    }
}
