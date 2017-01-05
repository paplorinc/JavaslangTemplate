package pap.lorinc;

import javaslang.collection.Vector;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;

import static java.lang.String.format;
import static javaslang.collection.Iterator.continually;

public class TextEditor {
    private static final int SIZE = 1_000;
    static final String STRING_TEXT = continually(TextEditor::randomChar).take(SIZE).mkString();
    static final Vector<Character> VECTOR_TEXT = Vector.ofAll(STRING_TEXT.toCharArray());

    public static void main(String... args) {
        final List<String> string = editString(STRING_TEXT);
        final List<Vector<Character>> vector = editVector(VECTOR_TEXT);

        if (SIZE < 100) { System.out.println(string); }
        System.out.println(format("for %d elements String is %.1f× larger than Vector!",
                SIZE,
                (float) byteSize(string) / byteSize(vector)));
    }

    static List<String> editString(String t) {
        return editAll(t, t.length(),
                i -> t.substring(0, i) + " " + t.substring(i + 1));
    }
    static List<Vector<Character>> editVector(Vector<Character> t) {
        return editAll(t, t.length(),
                i -> t.update(i, ' '));
    }

    private static <T> List<T> editAll(T text, int length, IntFunction<T> replacer) {
        final List<T> history = new ArrayList<>(length);

        history.add(text);
        for (int i = 0; i < length; i++) {
            history.add(replacer.apply(i));
        }

        return history;
    }

    private static long byteSize(Object target) { return GraphLayout.parseInstance(target).totalSize(); }
    private static char randomChar() { return (char) ThreadLocalRandom.current().nextInt('A', 'Z'); }
}
