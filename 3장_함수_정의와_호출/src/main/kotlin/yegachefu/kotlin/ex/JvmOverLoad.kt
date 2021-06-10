package yegachefu.kotlin.inner

import java.lang.StringBuilder

@JvmOverloads
fun <T> joinToStringKt(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "[",
    postfix: String = "]"
): String {
    val sb = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) sb.append(separator)
        sb.append(element)
    }

    return sb.append(postfix).toString()
}

fun main() {
    val s = listOf("하","쿠","나","마","타","타")
    print(joinToStringKt(s))
}
