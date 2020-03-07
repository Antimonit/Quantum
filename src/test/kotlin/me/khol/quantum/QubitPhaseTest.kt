package me.khol.quantum

import me.khol.quantum.QubitFixtures.left
import me.khol.quantum.QubitFixtures.minus
import me.khol.quantum.QubitFixtures.one
import me.khol.quantum.QubitFixtures.plus
import me.khol.quantum.QubitFixtures.right
import me.khol.quantum.QubitFixtures.zero
import me.khol.quantum.gate.GateHadamard
import me.khol.quantum.gate.GateX
import me.khol.quantum.gate.GateY
import me.khol.quantum.gate.GateZ
import me.khol.quantum.math.Complex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.random.Random

/**
 * @see RegisterPhaseTest
 */
internal class QubitPhaseTest {

    @Test
    fun `Normalized qubits are equal to the original qubits`() {
        assertThat(one.normalized, equalTo(one))
        assertThat(zero.normalized, equalTo(zero))
        assertThat(plus.normalized, equalTo(plus))
        assertThat(minus.normalized, equalTo(minus))
        assertThat(right.normalized, equalTo(right))
        assertThat(left.normalized, equalTo(left))
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
    fun `Hadamard gate rotates about XZ axis`() {
        assertThat(GateHadamard * one, equalTo(minus))
        assertThat(GateHadamard * zero, equalTo(plus))
        assertThat(GateHadamard * plus, equalTo(zero))
        assertThat(GateHadamard * minus, equalTo(one))
    }

    @Test
    fun `Hadamard gate flips qubits on Y axis`() {
//        val halfRadian = Complex(sqrt(0.5), sqrt(0.5))
        assertThat(GateHadamard * right, equalTo(left)) // * halfRadian
        assertThat(GateHadamard * left, equalTo(right)) // * -halfRadian * Complex.I
    }

    @Test
    fun `Z gate rotates about Z axis`() {
        assertThat(GateZ * right, equalTo(left))
        assertThat(GateZ * left, equalTo(right))
        assertThat(GateZ * plus, equalTo(minus))
        assertThat(GateZ * minus, equalTo(plus))
    }

    @Test
    fun `Z gate ignores qubits on Z axis`() {
        assertThat(GateZ * one, equalTo(one)) // * -Complex.ONE
        assertThat(GateZ * zero, equalTo(zero)) // * Complex.ONE
    }

    @Test
    fun `Y gate rotates about Y axis`() {
        assertThat(GateY * one, equalTo(zero)) // * -Complex.I
        assertThat(GateY * zero, equalTo(one)) // * Complex.I
        assertThat(GateY * plus, equalTo(minus)) // * -Complex.I
        assertThat(GateY * minus, equalTo(plus)) // * Complex.I
    }

    @Test
    fun `Y gate ignores qubits on Y axis`() {
        assertThat(GateY * right, equalTo(right)) // * Complex.ONE
        assertThat(GateY * left, equalTo(left)) // * -Complex.ONE
    }

    @Test
    fun `X gate rotates about X axis`() {
        assertThat(GateX * one, equalTo(zero)) // * Complex.ONE
        assertThat(GateX * zero, equalTo(one)) // * Complex.ONE
        assertThat(GateX * right, equalTo(left)) // * Complex.I
        assertThat(GateX * left, equalTo(right)) // * -Complex.I
    }

    @Test
    fun `X gate ignores qubits on X axis`() {
        assertThat(GateX * plus, equalTo(plus)) // * Complex.ONE
        assertThat(GateX * minus, equalTo(minus)) // * -Complex.ONE
    }
}
