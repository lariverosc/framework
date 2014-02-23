package com.searchbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.searchbox.core.engine.SearchEngine;
import com.searchbox.data.PubmedCollection;
import com.searchbox.event.EngineReadyEvent;

@Service
public class CollectionService implements ApplicationListener<EngineReadyEvent> {
	
	private static Logger logger = LoggerFactory.getLogger(CollectionService.class);

	@Autowired
	AutowireCapableBeanFactory factory;
	
	@Override
	public void onApplicationEvent(EngineReadyEvent event) {
		// TODO here we have to get the collection of the engine
		// and update their fields :)
		
		SearchEngine<?, ?> engine = (SearchEngine<?, ?>)event.getSource();
		logger.info("SearchEngine " + engine.getName() + " is ready for some action!!!");
		
		
		PubmedCollection pubmecCollection = factory.createBean(PubmedCollection.class);
		pubmecCollection.importCollection();
		
	}
}
