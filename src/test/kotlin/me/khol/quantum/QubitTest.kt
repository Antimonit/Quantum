package me.khol.quantum

import me.khol.quantum.math.scalar.Complex
import me.khol.quantum.math.scalar.Number
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class QubitTest {

    @Test
    fun `ZERO Qubit always measures to 0`() {
        val qubit = Qubit.ZERO
        assertEquals(Number.ONE, qubit.probabilityZero)
        assertEquals(Number.ZERO, qubit.probabilityOne)
    }

    @Test
    fun `ONE Qubit always measures to 1`() {
        val qubit = Qubit.ONE
        assertEquals(Number.ZERO, qubit.probabilityZero)
        assertEquals(Number.ONE, qubit.probabilityOne)
    }

    @Test
    fun `HALF Qubit measures randomly`() {
        val qubit = Qubit.HALF
        assertEquals(Number.HALF, qubit.probabilityZero)
        assertEquals(Number.HALF, qubit.probabilityOne)
    }

    @Test
    fun `Measurement probabilities of qubits with negated alpha probabilities are equal`() {
        val a = Qubit(Complex.ONE, Complex.ZERO)
        val b = Qubit(-Complex.ONE, Complex.ZERO)
        assertEquals(a.probabilityZero, b.probabilityZero)
        assertEquals(a.probabilityOne, b.probabilityOne)
    }

    @Test
    fun `Measurement probabilities of qubits with negated beta probabilities are equal`() {
        val a = Qubit(Complex.ZERO, Complex.ONE)
        val b = Qubit(Complex.ZERO, -Complex.ONE)
        assertEquals(a.probabilityZero, b.probabilityZero)
        assertEquals(a.probabilityOne, b.probabilityOne)
    }

    @Test
    fun `Measurement probabilities of qubits with HALF probabilities are equal`() {
        val qubits = listOf(
            Qubit(Complex.HALF, Complex.HALF),
            Qubit(-Complex.HALF, Complex.HALF),
            Qubit(Complex.HALF, -Complex.HALF),
            Qubit(-Complex.HALF, -Complex.HALF)
        )
        assertEquals(1, qubits.map(Qubit::probabilityZero).toSet().size)
        assertEquals(1, qubits.map(Qubit::probabilityOne).toSet().size)
    }
}