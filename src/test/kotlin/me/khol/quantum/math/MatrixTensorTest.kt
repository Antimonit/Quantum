package me.khol.quantum.math

import me.khol.quantum.math.Complex.Companion.ONE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MatrixTensorTest {

    @Test
    fun oneTensorOne() = assertEquals(
        Matrix(1, 1, ONE),
        Matrix(1, 1, ONE) tensor Matrix(1, 1, ONE)
    )

    @Test
    fun twoTensorOne() = assertEquals(
        Matrix(2, 1, ONE, ONE),
        Matrix(2, 1, ONE, ONE) tensor Matrix(1, 1, ONE)
    )

    @Test
    fun oneTensorTwo() = assertEquals(
        Matrix(1, 2, ONE, ONE),
        Matrix(1, 2, ONE, ONE) tensor Matrix(1, 1, ONE)
    )

    @Test
    fun twoTensorTwo() = assertEquals(
        Matrix(2, 2, ONE, ONE, ONE, ONE),
        Matrix(1, 2, ONE, ONE) tensor Matrix(2, 1, ONE, ONE)
    )

    @Test
    fun twoTensorTwoKet() = assertEquals(
        Matrix(4, 1, ONE, ONE, ONE, ONE),
        Matrix(2, 1, ONE, ONE) tensor Matrix(2, 1, ONE, ONE)
    )

    @Test
    fun twoTensorTwoBra() = assertEquals(
        Matrix(1, 4, ONE, ONE, ONE, ONE),
        Matrix(1, 2, ONE, ONE) tensor Matrix(1, 2, ONE, ONE)
    )
}
