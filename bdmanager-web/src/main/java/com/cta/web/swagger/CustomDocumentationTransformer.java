package com.cta.web.swagger;

import java.util.Comparator;

import com.mangofactory.swagger.DocumentationTransformer;
import com.wordnik.swagger.core.Documentation;
import com.wordnik.swagger.core.DocumentationEndPoint;
import com.wordnik.swagger.core.DocumentationOperation;

public class CustomDocumentationTransformer extends DocumentationTransformer {

	public CustomDocumentationTransformer(Comparator<DocumentationEndPoint> endPointComparator, Comparator<DocumentationOperation> operationComparator) {
		super(endPointComparator, operationComparator);
	}

	@Override
	public Documentation applyTransformation(Documentation documentation) {
		return applySorting(documentation);
	}
}
