package com.altran.shoppingcart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.altran.shoppingcart.exceptions.NoContentException;
import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.model.vo.ItemVO;
import com.altran.shoppingcart.repository.ItemRepository;
import com.altran.shoppingcart.service.interfaces.IItemService;

@Service
public class ItemService implements IItemService{

	private ItemRepository repository;
	private SequenceGeneratorService sequence;

	@Autowired
	private ItemService(ItemRepository repository, SequenceGeneratorService sequence) {
		this.repository = repository;
		this.sequence = sequence;
	}

	@Override
	public List<Item> getAll() {
		List<Item> itemList = repository.findAll();
		if(CollectionUtils.isEmpty(itemList)) {
			throw new NoContentException("");
		}
		return itemList;
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
	public Item createItem(ItemVO itemVO) {
		Item item = new Item();
		item.setId(sequence.generateSequence(Item.SEQUENCE_NAME));
		item.setNome(itemVO.getNome());
		item.setValor(itemVO.getValor());
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