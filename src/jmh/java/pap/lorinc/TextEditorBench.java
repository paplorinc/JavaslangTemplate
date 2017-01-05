package pap.lorinc;

import org.openjdk.jmh.annotations.*;

import static pap.lorinc.TextEditor.*;

@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class TextEditorBench {
    @Benchmark
    public Object string() { return editString(STRING_TEXT); }

    @Benchmark
    public Object vector() { return editVector(VECTOR_TEXT); }
}
