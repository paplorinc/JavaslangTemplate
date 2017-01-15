package pap.lorinc;

import javaslang.Function2;

@FunctionalInterface
interface Replacer<Text> extends Function2<Text, Integer, Text> {}
