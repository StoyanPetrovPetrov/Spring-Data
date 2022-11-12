package com.softuni.modelmapper.services;

import com.softuni.modelmapper.entities.Address;
import com.softuni.modelmapper.entities.Employee;
import com.softuni.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeNameAndSalaryDTO;
import com.softuni.modelmapper.entities.dtos.EmployeeNamesDTO;
import com.softuni.modelmapper.repositories.AddressRepository;
import com.softuni.modelmapper.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Employee create(CreateEmployeeDTO employeeDTO) {
        Employee employee = mapper.map(employeeDTO, Employee.class);

        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                employeeDTO.getAddress().getCountry(),
                employeeDTO.getAddress().getCity());

        address.ifPresent(employee::setAddress);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return this.employeeRepository.findAll()
                .stream()
                .map(e -> mapper.map(e, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeNamesDTO findNamesById(long id) {
        return this.employeeRepository.findNamesById(id);
    }

    @Override
    public EmployeeNameAndSalaryDTO findNameAndSalaryById(long id) {
        return this.employeeRepository.findFirstNameAndSalaryById(id);
    }
}
