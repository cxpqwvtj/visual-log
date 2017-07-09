package app.visual.log.extentions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class StringKtTest {
    @Test
    fun dateConvert() {
        val expect = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN).parse("2017/07/08 16:31:59")
        val actual = "08/Jul/2017:16:31:59 +0900".dateConvert()
        assertThat(actual).hasSameTimeAs(expect)
    }
}