package yegachefu.kotlin

import org.junit.jupiter.api.Test
import yegachefu.java.ex.AsIs
import yegachefu.kotlin.ex.Tobe
import yegachefu.kotlin.ex.save

internal class _3_6_로컬함수_확장KtTest {

    @Test
    fun test() {
        val asIs = AsIs(1, "까든")
        val tobe = Tobe(1, "가든")

        asIs.save()
        tobe.save()
    }
}


