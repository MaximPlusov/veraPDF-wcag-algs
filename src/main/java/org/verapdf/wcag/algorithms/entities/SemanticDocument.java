package org.verapdf.wcag.algorithms.entities;

import org.verapdf.wcag.algorithms.entities.enums.SemanticType;

public class SemanticDocument extends SemanticNode {

    public SemanticDocument() {
        setSemanticType(SemanticType.DOCUMENT);
    }

    public SemanticDocument(SemanticType initialSemanticType) {
        super(initialSemanticType);
        setSemanticType(SemanticType.DOCUMENT);
    }
}
