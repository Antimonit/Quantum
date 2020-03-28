package me.khol.quantum.program

import me.khol.quantum.gate
import me.khol.quantum.gate.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class StitchedGateCNotTest {

    @Test
    fun `CNot gate control and target switched using Hadamard gates`() {
        assertThat(gate(2) {
            step { GateHadamard[0]; GateHadamard[1] }
            GateCNot[0, 1]
            step { GateHadamard[0]; GateHadamard[1] }
        }, equalTo(gate(2) {
            GateCNot[1, 0]
        }))
    }

    @Test
    fun `CNot02 made with help of Swap gates`() {
        assertThat(gate(3) {
            GateSwap[0, 1]
            GateCNot[1, 2]
            GateSwap[0, 1]
        }, equalTo(gate(3) {
            GateCNot[0, 2]
        }))
    }
}