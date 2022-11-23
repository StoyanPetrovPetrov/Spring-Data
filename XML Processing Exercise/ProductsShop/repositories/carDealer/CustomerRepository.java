package com.softuni.productsshop.repositories.carDealer;

import com.softuni.productsshop.models.carDealer.dtos.CustomerSalesDto;
import com.softuni.productsshop.models.carDealer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByOrderByBirthDateAscYoungerDriverAsc();

    @Query("SELECT NEW com.softuni.productsshop.models.carDealer.dtos.CustomerSalesDto(c.name, COUNT(s), " +
            "SUM(p.price * (1.0 - (s.discountPercentage/100.0)))) " +
            "FROM Customer c JOIN c.sales s " +
            "JOIN s.car car " +
            "JOIN car.parts p " +
            "GROUP BY c ")
    List<CustomerSalesDto> findAllCustomersWithThereSales();
}
