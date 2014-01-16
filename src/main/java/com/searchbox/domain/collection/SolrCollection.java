package com.searchbox.domain.collection;
import com.searchbox.domain.Collection;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SolrCollection extends Collection {

    /**
     */
    private String solrHost;
}
