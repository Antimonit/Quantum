package me.khol.quantum.gate

import me.khol.quantum.Qubit
import me.khol.quantum.math.Complex
import me.khol.quantum.math.Matrix
import me.khol.quantum.times
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.PI

class GatePhaseTest {

    @Test
    fun `Phase by PI radians is Z gate`() {
        assertThat(GatePhase(PI), equalTo<Gate>(GateZ))
    }

    @Test
    fun `Phase by half PI radians is S gate`() {
        assertThat(GatePhase(PI / 2), equalTo<Gate>(GateS))
    }

    @Test
    fun `Phase by quarter PI radians is T gate`() {
        assertThat(GatePhase(PI / 4), equalTo<Gate>(GateT))
    }

    @Test
    fun `Global phase gate has no observable effect`() {
        val qubit = Qubit.random()
        val globalPhaseGate = object : Gate() {
            override val qubits: Int = 1
            override val matrix: Matrix = Matrix(listOf(
                listOf(Complex.fromPolar(1.234), Complex.ZERO),
                listOf(Complex.ZERO, Complex.fromPolar(1.234))
            ))
        }
        assertThat(globalPhaseGate * qubit, equalTo(qubit))
    }
}
