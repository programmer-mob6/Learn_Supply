package com.example.learnsupply

import com.google.common.truth.Truth
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
        val tambah = 2 + 2
        Truth.assertThat(tambah).isEqualTo(4)
    }
}