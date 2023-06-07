package com.orderService.orderService.repositories;

import com.orderService.orderService.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
//this interface will actually write the data from model class to database
public interface OrderRepository extends JpaRepository<Orders,Long> {
}
