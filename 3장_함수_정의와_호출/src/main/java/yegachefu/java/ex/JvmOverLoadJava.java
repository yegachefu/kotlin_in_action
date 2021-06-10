package yegachefu.java.ex;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;
import static yegachefu.kotlin.inner.JvmOverLoadKt.joinToStringKt;

public class JvmOverLoadJava {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("하", "쿠", "나", "마", "타", "타");
        String separator = "-";
        String prefix = "#";
        String postfix = "~";

        out.println(joinToStringKt(list));
        out.println(joinToStringKt(list, separator));
        out.println(joinToStringKt(list, separator, prefix));
        out.println(joinToStringKt(list, separator, prefix, postfix));
    }
}
