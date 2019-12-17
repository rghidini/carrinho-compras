package com.altran.shoppingcart.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.altran.shoppingcart.model.Item;
import com.altran.shoppingcart.service.SequenceGeneratorService;

@Component
public class ItemModelListener extends AbstractMongoEventListener<Item> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ItemModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Item> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Item.SEQUENCE_NAME));
        }
    }

}
