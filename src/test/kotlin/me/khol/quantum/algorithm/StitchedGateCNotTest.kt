package me.khol.quantum.algorithm

import me.khol.quantum.algorithm
import me.khol.quantum.gate.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class StitchedGateCNotTest {

    @Test
    fun `CNot gate control and target switched using Hadamard gates`() {
        assertThat(algorithm(2) {
            GateHadamard[0] + GateHadamard[1]
            GateCNot[0, 1]
            GateHadamard[0] + GateHadamard[1]
        }.asGate().matrix, equalTo(algorithm(2) {
            GateCNot[1, 0]
        }.asGate().matrix))
    }

    @Test
    fun `CNot02 made with help of Swap gates`() {
        assertThat(algorithm(3) {
            GateSwap[0, 1]
            GateCNot[1, 2]
            GateSwap[0, 1]
        }.asGate().matrix, equalTo(algorithm(3) {
            GateCNot[0, 2]
        }.asGate().matrix))
    }
}