package com.altran.shoppingcart.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.service.interfaces.IItemService;

@Service
public class ItemService implements IItemService{

	private ItemRepository repository;

	@Autowired
	private ItemService(ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Item> getAll() {
		return Optional.ofNullable(repository.findAll())
				.orElseThrow(() -> new NoContentException(""))
				.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@Override
	public Item getItemById(Long id) {
		Item item;
		Optional<Item> findItem = repository.findById(id);
		if(!findItem.isPresent()) {
			throw new NoContentException("");
		}
		item = findItem.get();
		return item;
	}

	@Override
	public Item createItem(Item item) {
		return repository.save(item);
	}

	@Override
	public void deleteItem(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Item updateItem(Item item) {
		return repository.save(item);
	}


}