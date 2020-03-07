package me.khol.quantum

import me.khol.quantum.math.Complex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class QubitTest {

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

    @Test
    fun `Qubits with normalized global phase represent the same state`() {
        repeat(100) {
            val qubit = Qubit.random()
            val phased = qubit.normalized
            assertThat(phased, equalTo(qubit))
        }
    }
}