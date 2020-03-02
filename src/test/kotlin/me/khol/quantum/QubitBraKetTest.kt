package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class QubitBraKetTest {

    @DisplayName("Multiplication of Ket and Bra vectors")
    @Nested
    inner class QubitMultiplicationTest {

        @DisplayName("|0><0| + |1><1| = I")
        @Test
        fun ketsAndBras() = assertEquals(
            Matrix.identity(2),
            ZERO.ket * ZERO.bra + ONE.ket * ONE.bra
        )

        @DisplayName("(|0><1|)*|1> = |0>")
        @Test
        fun mapsToZeroQubit() = assertEquals(
            ZERO.ket,
            (ZERO.ket * ONE.bra) * ONE.ket
        )

        @DisplayName("(|0><1|)*|0> = 0")
        @Test
        fun mapsToZero() = assertEquals(
            Matrix(2, 1, Complex.ZERO, Complex.ZERO),
            (ZERO.ket * ONE.bra) * ZERO.ket
        )

        @DisplayName("(|x><x|)*|x> = |x>*(<x|x>)")
        @Test
        fun associativeQubits() {
            val qubit = Qubit(Complex(0.8, 0.0), Complex(0.0, 0.6))
            assertEquals(
                (qubit.ket * qubit.bra) * qubit.ket,
                qubit.ket * (qubit.bra * qubit.ket)
            )
        }
    }

    @DisplayName("Multiplication of Ket and Bra vectors with mathematical operators")
    @Nested
    inner class QubitMultiplicationWithOperatorsTest {

        @DisplayName("|0><0| + |1><1| = I")
        @Test
        fun ketsAndBras() = assertEquals(
            Matrix.identity(2),
            (ZERO cross ZERO) + (ONE cross ONE)
        )

        @DisplayName("(|0><1|)*|1> = |0>")
        @Test
        fun mapsToZeroQubit() = assertEquals(
            ZERO.ket,
            (ZERO cross ONE) * ONE.ket
        )

        @DisplayName("(|0><1|)*|0> = 0")
        @Test
        fun mapsToZero() = assertEquals(
            Matrix(2, 1, Complex.ZERO, Complex.ZERO),
            (ZERO cross ONE) * ZERO.ket
        )

        @DisplayName("(|x><x|)*|x> = |x>*(<x|x>)")
        @Test
        fun associativeQubits() {
            val qubit = Qubit(Complex(0.8, 0.0), Complex(0.0, 0.6))
            assertEquals(
                (qubit cross qubit) * qubit.ket,
                qubit.ket * (qubit dot qubit)
            )
        }
    }

    @DisplayName("Tensor product of two qubits")
    @Nested
    inner class TensorProductTest {

        @DisplayName("Tensor |00>")
        @Test
        fun tensorZeroZero() = assertEquals(
            Matrix(4, 1, Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO),
            ZERO x ZERO
        )

        @DisplayName("Tensor |01>")
        @Test
        fun tensorZeroOne() = assertEquals(
            Matrix(4, 1, Complex.ZERO, Complex.ONE, Complex.ZERO, Complex.ZERO),
            ZERO x ONE
        )

        @DisplayName("Tensor |10>")
        @Test
        fun tensorOneZero() = assertEquals(
            Matrix(4, 1, Complex.ZERO, Complex.ZERO, Complex.ONE, Complex.ZERO),
            ONE x ZERO
        )

        @DisplayName("Tensor |11>")
        @Test
        fun tensorOneOne() = assertEquals(
            Matrix(4, 1, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE),
            ONE x ONE
        )
    }
}
