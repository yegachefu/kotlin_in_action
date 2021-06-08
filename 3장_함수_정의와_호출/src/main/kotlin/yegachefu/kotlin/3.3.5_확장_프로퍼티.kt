/**
 * 확장 프로퍼티: 클래스 외부에서 프로퍼티를 정의 하는 법
 *
 * 확장 프로퍼티 만드는 법:
 *   val 클래스.확장프로퍼티명: 반환 타입(getter 구현)
 *     - 값(Immutable)이기 때문에 get 만 가능
 *
 *   var 클래스.확장프로퍼티명: 반환 타입(geeter, setter 구현)
 *     - 변수이기 때문에 get, set 가능
 */

package yegachefu.kotlin

import java.lang.StringBuilder

fun main() {
    var sb = StringBuilder("코틀린 인 액션")
    println("stringBuilder.lastChar = ${sb.lastChar}")

    sb.replaceLastChar = '숑'
    println("sb = $sb")

}

var StringBuilder.replaceLastChar: Char
    get() {
        return this[0]
    }
    set(value: Char) {
        this.setCharAt(this.length - 1, value)
    }

val StringBuilder.lastChar: Char
    get() {
        return this.toString()[this.length - 1]
    }


