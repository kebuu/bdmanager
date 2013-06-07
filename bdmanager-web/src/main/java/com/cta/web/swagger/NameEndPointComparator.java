package com.cta.web.swagger;

import java.util.Comparator;

import com.wordnik.swagger.core.DocumentationEndPoint;


public class NameEndPointComparator implements Comparator<DocumentationEndPoint> {
    
	@Override
    public int compare(DocumentationEndPoint first, DocumentationEndPoint second) {
        return first.getPath().compareTo(second.getPath());
    }
}