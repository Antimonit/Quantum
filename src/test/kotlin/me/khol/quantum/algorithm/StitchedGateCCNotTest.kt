package me.khol.quantum.algorithm

import me.khol.quantum.gateAlgorithm
import me.khol.quantum.gate.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import me.khol.quantum.gate.GateCCNot as CCNot
import me.khol.quantum.gate.GateCNot as CNot
import me.khol.quantum.gate.GateHadamard as H
import me.khol.quantum.gate.GateS as S
import me.khol.quantum.gate.GateT as T

class StitchedGateCCNotTest {

    @Test
    fun `CCNot gate made of 2-qubit gates`() {
        val gate = gateAlgorithm(3) {
            H[2]
            CNot[1, 2]
            T.adjoint[2]
            CNot[0, 2]
            T[2]
            CNot[1, 2]
            T.adjoint[2]
            CNot[0, 2]
            step { T[1]; T[2] }
            step { CNot[0, 1]; H[2] }
            step { T[0]; T.adjoint[1] }
            CNot[0, 1]
        }
        assertThat(gate, equalTo<Gate>(CCNot))
    }

    @Test
    fun `CCNot gate made of 2-qubit gates v3`() {
        val gate = gateAlgorithm(3) {
            H[2]
            CNot[1, 2]
            T.adjoint[2]
            CNot[0, 2]
            T[2]
            CNot[1, 2]
            T.adjoint[2]
            CNot[0, 2]
            step { T.adjoint[1]; T[2] }
            step { CNot[0, 1]; H[2] }
            T.adjoint[1]
            CNot[0, 1]
            step { T[0]; S[1] }
        }
        assertThat(gate, equalTo<Gate>(CCNot))
    }

    @Test
    fun `CCNot gate made of 2-qubit gates v2`() {
        val gate = gateAlgorithm(3) {
            step { T.adjoint[0]; T.adjoint[1]; H[2] }
            step { CNot[2, 0] }
            step { T[0]; CNot[1, 2] }
            step { CNot[1, 0]; T[2] }
            step { T.adjoint[0]; CNot[1, 2] }
            step { CNot[2, 0] }
            step { T[0]; T.adjoint[2] }
            step { CNot[1, 0]; H[2] }
        }
        assertThat(gate, equalTo<Gate>(CCNot))
    }
}
