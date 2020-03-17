package me.khol.quantum.algorithm

import me.khol.quantum.algorithm
import me.khol.quantum.gate.Gate
import me.khol.quantum.gate.GateCNot
import me.khol.quantum.gate.GateHadamard
import me.khol.quantum.gate.GateSwap
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class StitchedGateSwapTest {

    @Test
    fun `Swap gate made of CNot gates`() {
        assertThat(algorithm(2) {
            GateCNot[0, 1]
            GateCNot[1, 0]
            GateCNot[0, 1]
        }.asGate(), equalTo<Gate>(GateSwap))
    }

    @Test
    fun `Swap gate made of CNot gates v2`() {
        assertThat(algorithm(2) {
            GateCNot[1, 0]
            GateCNot[0, 1]
            GateCNot[1, 0]
        }.asGate(), equalTo<Gate>(GateSwap))
    }

    @Test
    fun `Swap gate made of CNot and Hadamard gates`() {
        assertThat(algorithm(2) {
            GateCNot[0, 1]
            step { GateHadamard[0]; GateHadamard[1] }
            GateCNot[0, 1]
            step { GateHadamard[0]; GateHadamard[1] }
            GateCNot[0, 1]
        }.asGate(), equalTo<Gate>(GateSwap))
    }
}
