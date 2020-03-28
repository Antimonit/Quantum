package me.khol.quantum

import me.khol.quantum.Qubit.Companion.ONE
import me.khol.quantum.Qubit.Companion.ZERO
import me.khol.quantum.math.Complex
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.random.Random

internal class RegisterTest {

    companion object {
        // Qubits at the ends of Z axis
        private val one = Register(ONE)
        private val zero = Register(ZERO)

        // Qubits at the ends of X axis
        private val plus = Register((ZERO.ket + ONE.ket) * sqrt(0.5))
        private val minus = Register((ZERO.ket - ONE.ket) * sqrt(0.5))

        // Qubits at the ends of Y axis
        private val iPlus = Register((ZERO.ket + ONE.ket * Complex.I) * sqrt(0.5))
        private val iMinus = Register((ZERO.ket - ONE.ket * Complex.I) * sqrt(0.5))
    }

    @Test
    fun `All states are already in normalized global phases`() {
        assertThat(one.normalized, equalTo(one))
        assertThat(zero.normalized, equalTo(zero))
        assertThat(plus.normalized, equalTo(plus))
        assertThat(minus.normalized, equalTo(minus))
        assertThat(iPlus.normalized, equalTo(iPlus))
        assertThat(iMinus.normalized, equalTo(iMinus))
    }

    @Test
    fun `Global phase shifted registers represent the same state`() {
        repeat(100) {
            val register = Register(Qubit.random(), Qubit.random())
            val phased = register * Complex.fromPolar(Random.nextDouble())
            assertThat(phased.normalized, equalTo(register.normalized))
        }
    }
}