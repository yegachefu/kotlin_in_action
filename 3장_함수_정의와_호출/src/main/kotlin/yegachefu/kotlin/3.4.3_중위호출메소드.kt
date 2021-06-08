/***
 * 중위 호출(infix call): 수신 객체와 유일한 메소드 인자사이에 메소드 이름을 넣는 방식으로 함수를 호출하는 방식
 * 객체 메소드이름 유일한인자 == 객체.메소드이름(유일한인자)
 * ex) public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
 *
 * 구조 분해란 순서쌍을 푸는 작업을 말한다.
 * Pair(1, "one") 을 구조 분해 선언하여 변수에 할당하고자 할 땐,
 * val (number, value) = Pair(1, "one") 이렇게 선언하면
 * number = 1, value = "one" 이 할당되도록 한다.
 * 이것이 구조 분해이다.
 */
package yegachefu.kotlin

fun main() {
    val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-seven")
    val hashMap = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-seven")
    val hashMap2 = hashMapOf<Int, String>(Pair(1, "one"), 7 to "seven", 53.to("fifty-seven"))

    // (key, value) in hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-seven") 이렇게 루프 안에서 구조분해도 가능하다.
    for((key, value) in hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-seven")) {
        println("key: $key, value: $value")
    }
}
