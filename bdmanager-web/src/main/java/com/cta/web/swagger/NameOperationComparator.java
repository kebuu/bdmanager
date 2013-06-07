package com.cta.web.swagger;

import java.util.Comparator;

import com.wordnik.swagger.core.DocumentationOperation;


public class NameOperationComparator implements Comparator<DocumentationOperation> {
    
	@Override
    public int compare(DocumentationOperation first, DocumentationOperation second) {
        return first.getNickname().compareTo(second.getNickname());
    }
}