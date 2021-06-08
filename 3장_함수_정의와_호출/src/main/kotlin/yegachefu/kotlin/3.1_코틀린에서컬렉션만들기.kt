/***
 * 3.1 여러가지 컬렉션 생성 방법
 * 3.4의 컬렉션 처리도 함께 다룸
 *
 * 가변 길이 인자는 자바의 ... 와 같다 코틀린에서는 vararg 게 쓴다.
 * 컬렉션 생성 정적 함수인 {컬렉션명}Of()의 생성자를 살펴보기!
 */

package yegachefu.kotlin

fun main() {

    val set = setOf(1, 7, 53)
    println("set.javaClass = ${set.javaClass}")

    val hashSet = hashSetOf(1, 7, 53)
    println("hashSet.javaClass = ${hashSet.javaClass}")

    val list = listOf(1, 7, 53)
    println("list.javaClass = ${list.javaClass}")

    val arrayList = arrayListOf(1, 7, 53)
    println("arrayList.javaClass = ${arrayList.javaClass}")
}
