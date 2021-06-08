/***
 * 코틀린에서 문자열 나누는 방법
 *
 * String, Char 그대로 split 메소드의 파라미터로 전달
 *
 * 코틀린에서 3중 따옴표로 묶으면 이프케이프 문자가 필요없어지기 때문에 정규식 표현하기 쉽다.
 *
 */
package yegachefu.kotlin

fun main() {
    // 3중 따옴표 문자열
    // 1. 이스케이프가 필요없다.
    // 2. \n 을 사용하여 게행할 수 없음
    // 3. 문자열 템플릿 사용 가능
    val list = listOf("코틀린", "인", "액션")

    println("""
    .        
    .       \n
    .    -------\n-----\\n-------
    .        
    .        ${list[0]}
    .               ${list[1]}
    .${list[2]}
    .        $
    .        
    """.trimMargin("."))

    println("""
            
           \n
        -------\n-----\\n-------
            
            ${list[0]}
                   ${list[1]}
    ${list[2]}
            $
            
    """.trimIndent())

    /// 정규식과 3중따옴표 문자열
    val regex = """(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    val (var1, var2, var3, var4) = regex.matchEntire("1.2.3.4")!!.destructured
    println("var1 = ${var1}, var2 = ${var2}, var3 = ${var3}, var4 = ${var4}")

    val regex2 = """(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    val (var21, var22, var23, var24, var25, var26, var27, var28, var29, var30) = regex2.matchEntire("1.2.3.4.5.6.7.8.9.10")!!.destructured
    println("var21 = ${var21}, var22 = ${var22}, var23 = ${var23}, var24 = ${var24}, var25 = ${var25}, var26 = ${var26}, var27 = ${var27}, var28 = ${var28}, var29 = ${var29}, var30 = ${var30}")

    //    val regex2 = """(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    //    val (var21, var22, var23, var24, var25, var26, var27, var28, var29, var30, var31) = regex2.matchEntire("1.2.3.4.5.6.7.8.9.10.11")!!.destructured
    //    println("var1 = ${var1}, var2 = ${var2}, var3 = ${var3}, var4 = ${var4}")
}

