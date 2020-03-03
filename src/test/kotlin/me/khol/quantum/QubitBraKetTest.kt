package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class QubitBraKetTest {

    @DisplayName("Multiplication of Ket and Bra vectors")
    @Nested
    inner class QubitMultiplicationTest {

        @DisplayName("|0><0| + |1><1| = I")
        @Test
        fun ketsAndBras() = assertThat(
            ZERO.ket * ZERO.bra + ONE.ket * ONE.bra,
            equalTo(Matrix.identity(2))
        )

        @DisplayName("(|0><1|)*|1> = |0>")
        @Test
        fun mapsToZeroQubit() = assertThat(
            (ZERO.ket * ONE.bra) * ONE.ket,
            equalTo(ZERO.ket)
        )

        @DisplayName("(|0><1|)*|0> = 0")
        @Test
        fun mapsToZero() = assertThat(
            (ZERO.ket * ONE.bra) * ZERO.ket,
            equalTo(Matrix(2, 1, Complex.ZERO, Complex.ZERO))
        )

        @DisplayName("(|x><x|)*|x> = |x>*(<x|x>)")
        @Test
        fun associativeQubits() {
            val qubit = Qubit(Complex(0.8, 0.0), Complex(0.0, 0.6))
            assertThat(
                (qubit.ket * qubit.bra) * qubit.ket,
                equalTo(qubit.ket * (qubit.bra * qubit.ket))
            )
        }
    }

    @DisplayName("Multiplication of Ket and Bra vectors with mathematical operators")
    @Nested
    inner class QubitMultiplicationWithOperatorsTest {

        @DisplayName("|0><0| + |1><1| = I")
        @Test
        fun ketsAndBras() = assertThat(
            (ZERO cross ZERO) + (ONE cross ONE),
            equalTo(Matrix.identity(2))
        )

        @DisplayName("(|0><1|)*|1> = |0>")
        @Test
        fun mapsToZeroQubit() = assertThat(
            (ZERO cross ONE) * ONE.ket,
            equalTo(ZERO.ket)
        )

        @DisplayName("(|0><1|)*|0> = 0")
        @Test
        fun mapsToZero() = assertThat(
            (ZERO cross ONE) * ZERO.ket,
            equalTo(Matrix(2, 1, Complex.ZERO, Complex.ZERO))
        )

        @DisplayName("(|x><x|)*|x> = |x>*(<x|x>)")
        @Test
        fun associativeQubits() {
            val qubit = Qubit(Complex(0.8, 0.0), Complex(0.0, 0.6))
            assertThat(
                (qubit cross qubit) * qubit.ket,
                equalTo(qubit.ket * (qubit dot qubit))
            )
        }
    }

    @DisplayName("Tensor product of two qubits")
    @Nested
    inner class TensorProductTest {

        @DisplayName("Tensor |00>")
        @Test
        fun tensorZeroZero() = assertThat(
            ZERO x ZERO,
            equalTo(Matrix(4, 1, Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO))
        )

        @DisplayName("Tensor |01>")
        @Test
        fun tensorZeroOne() = assertThat(
            ZERO x ONE,
            equalTo(Matrix(4, 1, Complex.ZERO, Complex.ONE, Complex.ZERO, Complex.ZERO))
        )

        @DisplayName("Tensor |10>")
        @Test
        fun tensorOneZero() = assertThat(
            ONE x ZERO,
            equalTo(Matrix(4, 1, Complex.ZERO, Complex.ZERO, Complex.ONE, Complex.ZERO))
        )

        @DisplayName("Tensor |11>")
        @Test
        fun tensorOneOne() = assertThat(
            ONE x ONE,
            equalTo(Matrix(4, 1, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE))
        )
    }
}
