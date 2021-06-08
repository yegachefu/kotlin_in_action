/**
 * 3.2.1 이름붙인 인자
 * 3.2.2 디폴트파라미터 값
 * 3.2.3 정적인 유틸리티 클래스 없애기: 최상위 함수와 프로퍼티
 */
package yegachefu.kotlin

import java.lang.StringBuilder

fun main() {
    val list = listOf(1, 2, 3)
    println(joinToString(list, ";", "(", ")"))

    // 인자에 이름을 붙이면 순서가 바뀌어도 가능
    println(joinToString(separator = ";", collection = list, postfix = "(", prefix = ")"))

    // 인자에 이름을 붙이는 순간 뒤에 나오는 인자의 이름도 필수로 붙여야 함, 컴파일에러!
//    println(joinToString(list, ";", postfix = "(", ")")

    // 디폴트 파라미터 값 설정하면 위에서 안되던 동작 가능
    println(joinToStringWithDefaultValue(list, postfix = "("))

     /*
        함수의 디폴트 파라미터 값은 함수를 호출하는 쪽이 아니라 함수 선언 쪽에서 지정됨됨
     */
}

fun <T> joinToString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {

    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// 클래스 외부에 최상위 함수로 선언할 수 있다. 자바의 static method 라고 이해
fun <T> joinToStringWithDefaultValue(
    collection: Collection<T>,
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String {

    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

// 최상위 프로퍼티
var opCount = 0         // private static int opCount = 0;
const val OP_COUNT = 0  // private static final OP_COUNT = 0;
