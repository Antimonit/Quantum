package me.khol.quantum.math

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
import kotlin.math.PI

internal class ComplexTest {

    @DisplayName("Constructing complex numbers from polar angles is correct")
    @Test
    fun complexFromAngle() {
        assertThat(Complex.fromPolar(0 * PI / 2), equalTo(Complex.ONE))
        assertThat(Complex.fromPolar(1 * PI / 2), equalTo(Complex.I))
        assertThat(Complex.fromPolar(2 * PI / 2), equalTo(-Complex.ONE))
        assertThat(Complex.fromPolar(3 * PI / 2), equalTo(-Complex.I))
    }

    @DisplayName("Angle of a complex number in polar coordinates is correct")
    @Test
    fun complexTheta() {
        val angle = 0.12345
        val c = Complex.fromPolar(angle)
        assertThat(c.theta, closeTo(angle, 1e-10))
    }

    @DisplayName("Radius of a complex number in polar coordinates is correct")
    @Test
    fun complexRadius() {
        val c = Complex(3.0, 4.0)
        assertThat(c.r, equalTo(5.0))
    }
}
