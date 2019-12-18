package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.model.vo.ItemVO;

public interface IItemService {
	
	List<Item> getAll();
	Item getItemById(Long id);
	Item createItem(ItemVO item);
	void deleteItem(Long id);
	Item updateItem(Item item);
}
