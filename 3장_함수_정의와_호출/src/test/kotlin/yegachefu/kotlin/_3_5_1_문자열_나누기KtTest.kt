package yegachefu.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class _3_5_1_문자열_나누기KtTest {

    @DisplayName("구분자를 여러개 입력하여 문자열 나누기")
    @ParameterizedTest
    @ValueSource(strings = ["1:2:3:4:5", "1.2.3.4.5"])
    fun splitTest(token: String) {
        val result = token.split(".", ":")
        val expected = listOf("1", "2", "3", "4", "5")
        assertThat(result).containsAll(expected)
    }


}
