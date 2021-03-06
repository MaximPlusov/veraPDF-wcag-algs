package org.verapdf.wcag.algorithms.entities;

import org.verapdf.wcag.algorithms.entities.enums.SemanticType;
import org.verapdf.wcag.algorithms.entities.geometry.BoundingBox;

/**
 * Allowed semantic types: PART, SECT, DIV, ASIDE, NON_STRUCT (TABLE ???)
 */
public class SemanticGroupingNode extends SemanticNode {

    public SemanticGroupingNode() {
        setSemanticType(SemanticType.DIV);
    }

    public SemanticGroupingNode(SemanticType initialSemanticType) {
        super(initialSemanticType);
        setSemanticType(SemanticType.DIV);
    }

    public SemanticGroupingNode(BoundingBox bbox, SemanticType initialSemanticType, SemanticType semanticType) {
        super(bbox, initialSemanticType, semanticType);
    }
}
