package yegachefu.kotlin

fun main(array: Array<String>) {

    // 여러가지 컬렉션 생성 방법
    val set = setOf(1, 7, 53)
    println("set.javaClass = ${set.javaClass}")
    val hashSet = hashSetOf(1, 7, 53)
    println("hashSet.javaClass = ${hashSet.javaClass}")
    val list = listOf(1, 7, 53)
    println("list.javaClass = ${list.javaClass}")
    val arrayList = arrayListOf(1, 7, 53)
    println("arrayList.javaClass = ${arrayList.javaClass}")

    // 3중 따옴표 문자열
    // 1. 이스케이프가 필요없다.
    // 2. \n 을 사용하여 게행할 수 없음
    // 3. 문자열 템플릿 사용 가능
    println("""
        
       \n
    -------\n-----\\n-------
        
        ${list[0]}
        $
        
    """.trimIndent())

    /// 정규식과 3중따옴표 문자열
    val regex = """(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    val (var1, var2, var3, var4) = regex.matchEntire("1.2.3.4")!!.destructured
    println("var1 = ${var1}, var2 = ${var2}, var3 = ${var3}, var4 = ${var4}")

    val regex2 = """(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    val (var21, var22, var23, var24, var25, var26, var27, var28, var29, var30) = regex2.matchEntire("1.2.3.4.5.6.7.8.9.10")!!.destructured
    println("var21 = ${var21}, var22 = ${var22}, var23 = ${var23}, var24 = ${var24}, var25 = ${var25}, var26 = ${var26}, var27 = ${var27}, var28 = ${var28}, var29 = ${var29}, var30 = ${var30}")

    // MatchResult 의 확장 프로퍼티 descturcted 는 10개까지만 결과를 받는다
    //    val regex2 = """(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)\.(.+)""".toRegex()
    //    val (var21, var22, var23, var24, var25, var26, var27, var28, var29, var30, var31) = regex2.matchEntire("1.2.3.4.5.6.7.8.9.10.11")!!.destructured
    //    println("var1 = ${var1}, var2 = ${var2}, var3 = ${var3}, var4 = ${var4}")
}
