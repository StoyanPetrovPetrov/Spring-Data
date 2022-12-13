package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Town;
import exam.model.dtos.CustomerImportDto;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static exam.util.Paths.CUSTOMERS_JSON_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final TownRepository townRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final Validator validator;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository,
                               ModelMapper modelMapper, Gson gson, Validator validator) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMERS_JSON_PATH);
    }

    @Override
    public String importCustomers() throws IOException {
        String jsonCustomers = readCustomersFileContent();
        CustomerImportDto[] customerImportDtos = this.gson.fromJson(jsonCustomers, CustomerImportDto[].class);
        List<String> results = new ArrayList<>();

        for (CustomerImportDto customerImportDto : customerImportDtos) {
            Set<ConstraintViolation<CustomerImportDto>> errors =
                    this.validator.validate(customerImportDto);
            if (errors.isEmpty()) {
                Optional<Customer> optionalCustomer =
                        this.customerRepository.findByEmail(customerImportDto.getEmail());
                if (optionalCustomer.isEmpty()) {
                    Customer customer = this.modelMapper.map(customerImportDto, Customer.class);

                    Optional<Town> town = this.townRepository.findByName(customerImportDto.getTown().getName());

                    customer.setTown(town.get());

                    this.customerRepository.save(customer);

                    results.add(String.format("Successfully imported Customer %s %s - %s",
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail()));

                } else {
                    results.add("Invalid Customer");
                }
            } else {
                results.add("Invalid Customer");
            }
        }
        return String.join(System.lineSeparator(), results);
    }
}
