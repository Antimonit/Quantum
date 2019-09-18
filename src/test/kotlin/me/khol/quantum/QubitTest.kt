package me.khol.quantum

import me.khol.quantum.math.scalar.Complex
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class QubitTest {

    @Test
    fun `ZERO Qubit always measures to 0`() {
        assertEquals(1.0, Qubit.ZERO.probabilityZero)
        assertEquals(0.0, Qubit.ZERO.probabilityOne)
    }

    @Test
    fun `ONE Qubit always measures to 1`() {
        assertEquals(0.0, Qubit.ONE.probabilityZero)
        assertEquals(1.0, Qubit.ONE.probabilityOne)
    }

    @Test
    fun `HALF Qubit measures randomly`() {
        assertEquals(0.5, Qubit.HALF.probabilityZero, 1e-10)
        assertEquals(0.5, Qubit.HALF.probabilityOne, 1e-10)
    }

    @Test
    fun `Measurement probabilities of qubits with negated alpha probabilities are equal`() {
        val a = Qubit(Complex.ONE, Complex.ZERO)
        val b = Qubit(Complex.ONE * -1.0, Complex.ZERO)
        assertEquals(a.probabilityZero, b.probabilityZero)
        assertEquals(a.probabilityOne, b.probabilityOne)
    }

    @Test
    fun `Measurement probabilities of qubits with negated beta probabilities are equal`() {
        val a = Qubit(Complex.ZERO, Complex.ONE)
        val b = Qubit(Complex.ZERO, Complex.ONE * -1.0)
        assertEquals(a.probabilityZero, b.probabilityZero)
        assertEquals(a.probabilityOne, b.probabilityOne)
    }

    @Test
    fun `Measurement probabilities of qubits with HALF probabilities are equal`() {
        val qubits = listOf(
            Qubit(Complex.HALF, Complex.HALF),
            Qubit(Complex.HALF * -1.0, Complex.HALF),
            Qubit(Complex.HALF, Complex.HALF * -1.0),
            Qubit(Complex.HALF * -1.0, Complex.HALF * -1.0)
        )
        assertEquals(1, qubits.map(Qubit::probabilityZero).toSet().size)
        assertEquals(1, qubits.map(Qubit::probabilityOne).toSet().size)
    }
}