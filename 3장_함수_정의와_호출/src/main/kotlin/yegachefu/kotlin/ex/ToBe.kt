package yegachefu.kotlin.ex

class Tobe(
    val id: Int,
    val name: String
)

fun Tobe.save() {
    fun validate(value: Object, fieldName: String) {
        if (value == null) {
            throw IllegalArgumentException("$fieldName 잘못됨!")
        }
    }

    validate(id as Object, "id")
    validate(name as Object, "name")
    println("saved!")
}


