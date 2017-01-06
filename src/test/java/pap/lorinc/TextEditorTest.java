package pap.lorinc;

import javaslang.Tuple2;
import javaslang.collection.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pap.lorinc.TextEditor.*;

class TextEditorTest {
    @Test
    void histories_should_be_equal() {
        final String stringHistory = editString(STRING_TEXT).mkString();
        final String vectorHistory = editVector(VECTOR_TEXT).map(Vector::mkString).mkString();
        assertEquals(stringHistory, vectorHistory);
    }

    @Test
    void history_entries_should_differs_by_a_single_character() {
        editVector(VECTOR_TEXT).sliding(2).forEach(window -> {
            final Vector<Tuple2<Character, Character>> zippedChars = window.get(0).zip(window.get(1));
            assertEquals(1, zippedChars.count(p -> p._1 != p._2));
        });
    }
}