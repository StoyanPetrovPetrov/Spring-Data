package com.softuni.modelmapper.repositories;

import com.softuni.modelmapper.entities.Employee;
import com.softuni.modelmapper.entities.dtos.EmployeeNameAndSalaryDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeNamesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT new com.softuni.modelmapper.entities.dtos.EmployeeNamesDTO(e.firstName, e.lastName)" +
            " FROM Employee AS e WHERE e.id = :id")
    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findFirstNameAndSalaryById(long id);

}
