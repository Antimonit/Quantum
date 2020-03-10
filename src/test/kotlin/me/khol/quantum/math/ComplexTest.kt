package me.khol.quantum.math

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import kotlin.math.PI

internal class ComplexTest {

    /**
     * Java double equality checks behaves in a weird way.
     *
     * When negative zero and positive zero are compared as raw types, they are equal. But when
     * they are compared as boxed types, they are NOT equal and positive zero is considered larger
     * that negative zero.
     */
    @DisplayName("Negative zero equals positive zero")
    @Suppress("SimplifyBooleanWithConstants")
    @Test
    fun negativeZero() {
        // Compares as raw types. Assertion succeeds.
        assertThat(-0.0 == 0.0, equalTo(true))
        // Compares as boxed types. Assertion fails.
        // assertThat(-0.0, equalTo(0.0))
    }

    /**
     * Make sure that Complex numbers don't misbehave when defined by negative zero as described
     * in the [previous test][negativeZero].
     */
    @DisplayName("Negative complex zero equals positive complex zero")
    @Test
    fun negativeComplexZero() {
        assertThat(
            listOf(Complex(-0.0, 0.0), Complex(-0.0, -0.0), Complex(0.0, -0.0)),
            everyItem(equalTo(Complex(0.0, 0.0)))
        )
    }

    @DisplayName("Complex zero's theta should be undefined")
    @Test
    fun complexNumbersDoesNotDefineTheta() {
        assertThat(Complex.ZERO.theta, notANumber())
    }

    @DisplayName("Angle of complex number is correct")
    @Test
    fun theta() {
        assertThat(Complex(1, 1).theta, equalTo(PI / 4))
    }

    @DisplayName("Radius of a complex number is correct")
    @Test
    fun radius() {
        assertThat(Complex(-4, 3).radius, equalTo(5.0))
    }

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
        val radius = 42.0
        val c = Complex.fromPolar(1.0, radius)
        assertThat(c.radius, equalTo(radius))
    }

    @DisplayName("Complex number is valid even if angle is NaN")
    @Test
    fun complexThetaNaN() {
        val c = Complex.fromPolar(Double.NaN, radius = 0)
        assertThat(c.re, equalTo(0.0))
        assertThat(c.im, equalTo(0.0))
    }
}
