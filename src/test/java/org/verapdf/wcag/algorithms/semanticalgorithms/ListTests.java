package org.verapdf.wcag.algorithms.semanticalgorithms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.verapdf.wcag.algorithms.entities.INode;
import org.verapdf.wcag.algorithms.entities.ITree;
import org.verapdf.wcag.algorithms.entities.JsonToPdfTree;
import org.verapdf.wcag.algorithms.entities.SemanticTree;
import org.verapdf.wcag.algorithms.entities.lists.PDFList;
import org.verapdf.wcag.algorithms.semanticalgorithms.consumers.AccumulatedNodeConsumer;
import org.verapdf.wcag.algorithms.semanticalgorithms.consumers.ClusterTableConsumer;
import org.verapdf.wcag.algorithms.semanticalgorithms.consumers.SemanticTreePreprocessingConsumer;
import org.verapdf.wcag.algorithms.semanticalgorithms.utils.ListUtils;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ListTests {

    static Stream<Arguments> listDetectionTestParams() {
        return Stream.of(
                Arguments.of("list-with-image-label.json", new int[] {3}, false),
                Arguments.of("NEG-fake-list.json", new int[] {4}, false),
                Arguments.of("ordered-list1.json", new int[] {5, 5}, true)
                );
    }

    @ParameterizedTest(name = "{index}: ({0}, {1}, {2} ) => {0}")
    @MethodSource("listDetectionTestParams")
    void testListDetection(String filename, int[] checkSizes, boolean initialSemanticIsValid) throws IOException {
        INode root = JsonToPdfTree.getPdfTreeRoot("/files/lists/" + filename);
        ITree tree = new SemanticTree(root);

        Consumer<INode> semanticTreeValidator = new SemanticTreePreprocessingConsumer();
        tree.forEach(semanticTreeValidator);

        Consumer<INode> paragraphValidator = new AccumulatedNodeConsumer();
        tree.forEach(paragraphValidator);

        ClusterTableConsumer tableFinder = new ClusterTableConsumer();
        tree.forEach(tableFinder);

        List<PDFList> resultLists = tableFinder.getLists();

        Assertions.assertEquals(checkSizes.length, resultLists.size());

        for (int i = 0; i < checkSizes.length; ++i) {
            Assertions.assertEquals(checkSizes[i], resultLists.get(i).getNumberOfListItems());
        }

        if (initialSemanticIsValid) {
            testListTreeStructure(tree);
        }
    }

    private void testListTreeStructure(ITree tree) {
        for (INode node : tree) {
            if (ListUtils.isListNode(node)) {
                Assertions.assertEquals(node.getInitialSemanticType(), node.getSemanticType());
            }
        }
    }
}
