package com.altran.shoppingcart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.altran.shoppingcart.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, Long>{
}
