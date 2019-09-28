package me.khol.quantum.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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
        assertEquals(c, a * b)
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
        assertEquals(c, a + b)
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
        assertEquals(c, a - b)
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
        assertEquals(c, a * 3)
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
        assertEquals(c, a * C(1, 2))
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
        assertEquals(c, a.transpose())
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
        assertEquals(d, c.transpose())
    }

    @DisplayName("Get returns correct values")
    @Test
    fun get() {
        assertEquals(C(2), a[0, 0])
        assertEquals(C(3), a[0, 1])
        assertEquals(C(4), a[1, 0])
        assertEquals(C(5), a[1, 1])
    }
}
