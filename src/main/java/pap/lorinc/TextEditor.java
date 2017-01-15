package pap.lorinc;

import javaslang.collection.Seq;
import javaslang.collection.Vector;

import static java.lang.Character.toLowerCase;
import static java.lang.String.format;
import static javaslang.collection.Iterator.continually;
import static pap.lorinc.Edits.editAllCharsAndReturnHistory;
import static pap.lorinc.Memory.byteSize;

@SuppressWarnings("ConstantConditions")
public class TextEditor {
    private static final int SIZE = 1_000;
    static final String STRING_TEXT = continually(Generator::randomUppercaseChar).take(SIZE).mkString();
    static final Vector<Character> VECTOR_TEXT = Vector.ofAll(STRING_TEXT.toCharArray());

    public static void main(String... args) {
        final Seq<String> string = editString(STRING_TEXT);
        final Seq<Vector<Character>> vector = editVector(VECTOR_TEXT);

        if (SIZE < 100) { System.out.println(string); }
        System.out.println(format("for %d elements String is %.1f× larger than Vector!",
                SIZE,
                (float) byteSize(string) / byteSize(vector)));
    }

    static Seq<String> editString(String text) {
        final Replacer<String> stringReplacer = (t, i) -> t.substring(0, i) + toLowerCase(t.charAt(i)) + t.substring(i + 1);
        return editAllCharsAndReturnHistory(text, text.length(), stringReplacer);
    }

    static Seq<Vector<Character>> editVector(Vector<Character> text) {
        final Replacer<Vector<Character>> vectorReplacer = (t, i) -> t.update(i, toLowerCase(t.get(i)));
        return editAllCharsAndReturnHistory(text, text.length(), vectorReplacer);
    }
}
