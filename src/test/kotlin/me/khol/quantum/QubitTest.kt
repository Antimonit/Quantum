package me.khol.quantum

import me.khol.quantum.math.ejml.Complex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class QubitTest {

    @Test
    fun `ZERO Qubit always measures to 0`() {
        val qubit = Qubit.ZERO
        assertEquals(1.0, qubit.probabilityZero, 1e-10)
        assertEquals(0.0, qubit.probabilityOne, 1e-10)
    }

    @Test
    fun `ONE Qubit always measures to 1`() {
        val qubit = Qubit.ONE
        assertEquals(0.0, qubit.probabilityZero, 1e-10)
        assertEquals(1.0, qubit.probabilityOne, 1e-10)
    }

    @Test
    fun `HALF Qubit measures randomly`() {
        val qubit = Qubit(Complex(re = sqrt(0.5)), Complex(im = sqrt(0.5)))
        assertEquals(0.5, qubit.probabilityZero, 1e-10)
        assertEquals(0.5, qubit.probabilityOne, 1e-10)
    }

    @Test
    fun `Measurement probabilities of qubits with negated alpha probabilities are equal`() {
        val a = Qubit(Complex.ONE, Complex.ZERO)
        val b = Qubit(-Complex.ONE, Complex.ZERO)
        assertEquals(a.probabilityZero, b.probabilityZero, 1e-10)
        assertEquals(a.probabilityOne, b.probabilityOne, 1e-10)
    }

    @Test
    fun `Measurement probabilities of qubits with negated beta probabilities are equal`() {
        val a = Qubit(Complex.ZERO, Complex.ONE)
        val b = Qubit(Complex.ZERO, -Complex.ONE)
        assertEquals(a.probabilityZero, b.probabilityZero, 1e-10)
        assertEquals(a.probabilityOne, b.probabilityOne, 1e-10)
    }

    @Test
    fun `Measurement probabilities of qubits with HALF probabilities are equal`() {
        val root = sqrt(0.5)
        val half = Complex(root, 0)
        val halfI = Complex(0, root)
        val qubits = listOf(
            Qubit(half, half),
            Qubit(-half, half),
            Qubit(half, -half),
            Qubit(-half, -half),
            Qubit(half, halfI),
            Qubit(-half, halfI),
            Qubit(half, -halfI),
            Qubit(-half, -halfI),
            Qubit(halfI, halfI),
            Qubit(-halfI, halfI),
            Qubit(halfI, -halfI),
            Qubit(-halfI, -halfI)
        )
        assertEquals(1, qubits.map(Qubit::probabilityZero).toSet().size)
        assertEquals(1, qubits.map(Qubit::probabilityOne).toSet().size)
    }
}