package com.softuni.productsshop.models.carDealer.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderedCustomersRootDto {
    @XmlElement(name="customer")
    private List<OrderedCustomersDto> orders;

    public OrderedCustomersRootDto(List<OrderedCustomersDto> orders) {
        this.orders = orders;
    }

    public OrderedCustomersRootDto() {
    }
}
