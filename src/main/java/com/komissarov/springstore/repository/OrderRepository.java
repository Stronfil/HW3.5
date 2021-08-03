package com.komissarov.springstore.repository;

import com.komissarov.springstore.entity.ShopOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<ShopOrder, Long> {

    List<ShopOrder> findAllByUserId(long userId);
}
