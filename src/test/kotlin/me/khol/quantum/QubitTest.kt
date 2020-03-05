package me.khol.quantum

import me.khol.quantum.math.Complex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.random.Random

internal class QubitTest {

    @Test
    fun `ZERO Qubit always measures to 0`() {
        val qubit = Qubit.ZERO
        assertThat(qubit.probabilityZero, closeTo(1.0, 1e-10))
        assertThat(qubit.probabilityOne, closeTo(0.0, 1e-10))
    }

    @Test
    fun `ONE Qubit always measures to 1`() {
        val qubit = Qubit.ONE
        assertThat(qubit.probabilityZero, closeTo(0.0, 1e-10))
        assertThat(qubit.probabilityOne, closeTo(1.0, 1e-10))
    }

    @Test
    fun `HALF Qubit measures randomly`() {
        val qubit = Qubit(Complex(re = sqrt(0.5)), Complex(im = sqrt(0.5)))
        assertThat(qubit.probabilityZero, closeTo(0.5, 1e-10))
        assertThat(qubit.probabilityOne, closeTo(0.5, 1e-10))
    }

    @Test
    fun `Measurement probabilities of qubits with negated alpha probabilities are equal`() {
        val a = Qubit(Complex.ONE, Complex.ZERO)
        val b = Qubit(-Complex.ONE, Complex.ZERO)
        assertThat(a.probabilityZero, closeTo(b.probabilityZero, 1e-10))
        assertThat(a.probabilityOne, closeTo(b.probabilityOne, 1e-10))
    }

    @Test
    fun `Measurement probabilities of qubits with negated beta probabilities are equal`() {
        val a = Qubit(Complex.ZERO, Complex.ONE)
        val b = Qubit(Complex.ZERO, -Complex.ONE)
        assertThat(a.probabilityZero, closeTo(b.probabilityZero, 1e-10))
        assertThat(a.probabilityOne, closeTo(b.probabilityOne, 1e-10))
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
        assertThat(qubits.map(Qubit::probabilityZero), everyItem(closeTo(0.5, 1e-10)))
        assertThat(qubits.map(Qubit::probabilityOne), everyItem(closeTo(0.5, 1e-10)))
    }

    @Test
    fun `Random qubit is always valid`() {
        repeat(100) {
            Qubit.random()
        }
    }

    @Test
    fun `Global phase shifted qubits retain their measurement probabilities`() {
        repeat(100) {
            val qubit = Qubit.random()
            val phased = qubit * Complex.fromPolar(Random.nextDouble())
            assertThat(phased.probabilityOne, closeTo(qubit.probabilityOne, 1e-10))
            assertThat(phased.probabilityZero, closeTo(qubit.probabilityZero, 1e-10))
        }
    }

    @Test
    fun `Global phase shifted qubits represent the same state`() {
        repeat(100) {
            val qubit = Qubit.random()
            val phased = qubit * Complex.fromPolar(Random.nextDouble())
            assertThat(phased, equalTo(qubit))
        }
    }
}