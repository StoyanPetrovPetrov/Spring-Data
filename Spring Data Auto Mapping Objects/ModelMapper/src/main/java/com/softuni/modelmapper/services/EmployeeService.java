package com.softuni.modelmapper.services;

import com.softuni.modelmapper.entities.Employee;
import com.softuni.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeNameAndSalaryDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeNamesDTO;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();

    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findNameAndSalaryById(long id);
}
