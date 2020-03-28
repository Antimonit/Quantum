package me.khol.quantum.program

import me.khol.quantum.gate
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
        assertThat(gate(2) {
            GateCNot[0, 1]
            GateCNot[1, 0]
            GateCNot[0, 1]
        }, equalTo<Gate>(GateSwap))
    }

    @Test
    fun `Swap gate made of CNot gates v2`() {
        assertThat(gate(2) {
            GateCNot[1, 0]
            GateCNot[0, 1]
            GateCNot[1, 0]
        }, equalTo<Gate>(GateSwap))
    }

    @Test
    fun `Swap gate made of CNot and Hadamard gates`() {
        assertThat(gate(2) {
            GateCNot[0, 1]
            step { GateHadamard[0]; GateHadamard[1] }
            GateCNot[0, 1]
            step { GateHadamard[0]; GateHadamard[1] }
            GateCNot[0, 1]
        }, equalTo<Gate>(GateSwap))
    }
}
