package me.khol.quantum.math

import me.khol.quantum.math.Complex.Companion.ONE
import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

internal class MatrixTensorTest {

    @Test
    fun oneTensorOne() = assertThat(
        Matrix(1, 1, ONE) tensor Matrix(1, 1, ONE),
        equalTo(Matrix(1, 1, ONE))
    )

    @Test
    fun twoTensorOne() = assertThat(
        Matrix(2, 1, ONE, ONE) tensor Matrix(1, 1, ONE),
        equalTo(Matrix(2, 1, ONE, ONE))
    )

    @Test
    fun oneTensorTwo() = assertThat(
        Matrix(1, 2, ONE, ONE) tensor Matrix(1, 1, ONE),
        equalTo(Matrix(1, 2, ONE, ONE))
    )

    @Test
    fun twoTensorTwo() = assertThat(
        Matrix(1, 2, ONE, ONE) tensor Matrix(2, 1, ONE, ONE),
        equalTo(Matrix(2, 2, ONE, ONE, ONE, ONE))
    )

    @Test
    fun twoTensorTwoKet() = assertThat(
        Matrix(2, 1, ONE, ONE) tensor Matrix(2, 1, ONE, ONE),
        equalTo(Matrix(4, 1, ONE, ONE, ONE, ONE))
    )

    @Test
    fun twoTensorTwoBra() = assertThat(
        Matrix(1, 2, ONE, ONE) tensor Matrix(1, 2, ONE, ONE),
        equalTo(Matrix(1, 4, ONE, ONE, ONE, ONE))
    )
}
