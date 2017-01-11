package pap.lorinc;

import javaslang.Function2;
import javaslang.collection.Stack;
import javaslang.collection.Vector;
import org.openjdk.jol.info.GraphLayout;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Character.*;
import static java.lang.String.format;
import static javaslang.API.List;
import static javaslang.collection.Iterator.continually;

@SuppressWarnings("ConstantConditions")
public class TextEditor {
    private static final int SIZE = 1_000;
    static final String STRING_TEXT = continually(TextEditor::randomChar).take(SIZE).mkString();
    static final Vector<Character> VECTOR_TEXT = Vector.ofAll(STRING_TEXT.toCharArray());

    public static void main(String... args) {
        final Stack<String> string = editString(STRING_TEXT);
        final Stack<Vector<Character>> vector = editVector(VECTOR_TEXT);

        if (SIZE < 100) { System.out.println(string); }
        System.out.println(format("for %d elements String is %.1f× larger than Vector!",
                SIZE,
                (float) byteSize(string) / byteSize(vector)));
    }

    static Stack<String> editString(String text) {
        final Replacer<String> stringReplacer = (t, i) -> t.substring(0, i) + toLowerCase(t.charAt(i)) + t.substring(i + 1);
        return editAllCharsAndReturnHistory(text, text.length(), stringReplacer);
    }

    static Stack<Vector<Character>> editVector(Vector<Character> text) {
        final Replacer<Vector<Character>> vectorReplacer = (t, i) -> t.update(i, toLowerCase(t.get(i)));
        return editAllCharsAndReturnHistory(text, text.length(), vectorReplacer);
    }

    private static <Text> Stack<Text> editAllCharsAndReturnHistory(Text text, int length, Replacer<Text> replacer) {
        Stack<Text> history = List(text);
        for (int i = 0; i < length; i++) {
            text = replacer.apply(text, i);
            history = history.push(text);
        }
        return history;
    }

    @FunctionalInterface
    interface Replacer<Text> extends Function2<Text, Integer, Text> {}
    private static long byteSize(Object target) { return GraphLayout.parseInstance(target).totalSize(); }
    private static char randomChar() { return (char) ThreadLocalRandom.current().nextInt('A', 'Z'); }
}
