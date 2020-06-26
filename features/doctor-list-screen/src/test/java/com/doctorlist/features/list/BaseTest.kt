package com.doctorlist.features.list

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class BaseTest {
    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }
}

const val once = 1
const val never = 0