package me.khol.quantum.gate

import me.khol.quantum.math.Complex
import me.khol.quantum.math.Complex.Companion.ONE
import me.khol.quantum.math.Complex.Companion.ZERO
import me.khol.quantum.math.Matrix
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PhaseShiftGatesTest {

    @Test
    fun gateSOutOfGateT() {
        assertThat(GateT * GateT, equalTo<Gate>(GateS))
    }

    @Test
    fun gateZOutOfgateS() {
        assertThat(GateS * GateS, equalTo<Gate>(GateZ))
    }

    @Test
    fun adjointOfTGate() {
        assertThat(
            GateT.adjoint.matrix,
            equalTo(Matrix(listOf(
                listOf(ONE, ZERO),
                listOf(ZERO, Complex.fromPolar(-PI / 4))
            )))
        )
    }
    @Test
    fun adjointOfSGate() {
        assertThat(
            GateS.adjoint.matrix,
            equalTo(Matrix(listOf(
                listOf(ONE, ZERO),
                listOf(ZERO, Complex.fromPolar(-PI / 2))
            )))
        )
    }
}
