package com.searchbox.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.domain.PresetDefinition;
import com.searchbox.domain.SearchElementDefinition;
import com.searchbox.service.ApplicationConversionService;
import com.searchbox.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {

	private static Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	ConversionService conversionService;
	
	@Autowired
	ApplicationConversionService searchComponentService;
	
	@Autowired
	SearchService searchService;
	
	public SearchController() {
	}
	
	@RequestMapping
//	public ModelAndView search(@RequestParam("ff") FieldFacet.ValueCondition condition) {
	public ModelAndView search(HttpServletRequest request) {
		
		PresetDefinition pDef =  PresetDefinition.findAllPresetDefinitions().get(0);
		
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
		for(String param:searchComponentService.getSearchConditionParams()){
			if(request.getParameterValues(param) != null){
				for(String value:request.getParameterValues(param)){
					if(value != null && !value.isEmpty()){
						SearchCondition cond = (SearchCondition) conversionService.convert(value, searchComponentService.getSearchConditionClass(param));
						conditions.add(cond);
					}
				}
			}
		}
		
		SearchResult result = new SearchResult();
		
		if(pDef != null){
			Preset preset = pDef.getElement();
			if(preset != null){	
				for(SearchElement element:searchService.execute(preset, conditions)){
					logger.info("Adding to result view element: " + element);
					result.addElement(element);
				}
			}
		}
		
		ModelAndView model = new ModelAndView("search/index");
		model.addObject("result", result);
		
		return model;
	}
}
