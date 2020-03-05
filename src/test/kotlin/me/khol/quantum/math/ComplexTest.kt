package me.khol.quantum.math

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
import kotlin.math.cos
import kotlin.math.sin

internal class ComplexTest {

    @DisplayName("Angle of a complex number in polar coordinates is correct")
    @Test
    fun complexTheta() {
        val angle = 0.12345
        val c = Complex(cos(angle), sin(angle))
        assertThat(c.theta, closeTo(angle, 1e-10))
    }

    @DisplayName("Radius of a complex number in polar coordinates is correct")
    @Test
    fun complexRadius() {
        val c = Complex(3.0, 4.0)
        assertThat(c.r, equalTo(5.0))
    }
}
