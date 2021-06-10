package yegachefu.kotlin.ex

fun spreadOperator(args: Array<String>) {
    val spreadList = listOf("spread: ", *args)
    println(spreadList)

    val unSpreadList = listOf("unspread: ", args)
    println(unSpreadList)
}

fun main() {
    val arg = arrayOf("하", "쿠", "나", "마", "타", "타")
    spreadOperator(arg)
}
