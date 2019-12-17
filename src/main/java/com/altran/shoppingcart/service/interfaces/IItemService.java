package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Item;

public interface IItemService {
	
	List<Item> getAll();
	Item getItemById(Long id);
	Item createItem(Item item);
	void deleteItem(Long id);
	Item updateItem(Item item);
}
