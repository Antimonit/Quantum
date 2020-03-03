package me.khol.quantum.math

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

private typealias C = Complex

internal class MatrixTest {

    private val a = Matrix(
        listOf(
            listOf(C(2), C(3)),
            listOf(C(4), C(5))
        )
    )

    private val b = Matrix(
        listOf(
            listOf(C(3), C(2)),
            listOf(C(1), C(0))
        )
    )

    @DisplayName("Multiply matrices")
    @Test
    fun matrixTimesMatrix() {
        val c = Matrix(
            listOf(
                listOf(C(9), C(4)),
                listOf(C(17), C(8))
            )
        )
        assertThat(a * b, equalTo(c))
    }

    @DisplayName("Add matrices")
    @Test
    fun matrixPlusMatrix() {
        val c = Matrix(
            listOf(
                listOf(C(5), C(5)),
                listOf(C(5), C(5))
            )
        )
        assertThat(a + b, equalTo(c))
    }

    @DisplayName("Subtract matrices")
    @Test
    fun matrixMinusMatrix() {
        val c = Matrix(
            listOf(
                listOf(C(-1), C(1)),
                listOf(C(3), C(5))
            )
        )
        assertThat(a - b, equalTo(c))
    }

    @DisplayName("Multiply matrix by number")
    @Test
    fun matrixTimesNumber() {
        val c = Matrix(
            listOf(
                listOf(C(6), C(9)),
                listOf(C(12), C(15))
            )
        )
        assertThat(a * 3, equalTo(c))
    }

    @DisplayName("Multiply matrix by complex number")
    @Test
    fun matrixTimesComplex() {
        val c = Matrix(
            listOf(
                listOf(C(2, 4), C(3, 6)),
                listOf(C(4, 8), C(5, 10))
            )
        )
        assertThat(a * C(1, 2), equalTo(c))
    }

    @DisplayName("Transpose square matrix")
    @Test
    fun transposeSquareMatrix() {
        val c = Matrix(
            listOf(
                listOf(C(2), C(4)),
                listOf(C(3), C(5))
            )
        )
        assertThat(a.transpose(), equalTo(c))
    }

    @DisplayName("Transpose rectangular matrix")
    @Test
    fun transposeRectangularMatrix() {
        val c = Matrix(
            listOf(
                listOf(C(1), C(4))
            )
        )
        val d = Matrix(
            listOf(
                listOf(C(1)),
                listOf(C(4))
            )
        )
        assertThat(c.transpose(), equalTo(d))
    }

    @DisplayName("Get returns correct values")
    @Test
    fun get() {
        assertThat(a[0, 0], equalTo(C(2)))
        assertThat(a[0, 1], equalTo(C(3)))
        assertThat(a[1, 0], equalTo(C(4)))
        assertThat(a[1, 1], equalTo(C(5)))
    }
}
