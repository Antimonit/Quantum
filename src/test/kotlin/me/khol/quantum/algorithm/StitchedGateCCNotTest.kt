package me.khol.quantum.algorithm

import me.khol.quantum.Qubit
import me.khol.quantum.Register
import me.khol.quantum.algorithm
import me.khol.quantum.gate.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class StitchedGateCCNotTest {

    @Test
    fun `CCNot gate made of 2-qubit gates`() {
        val register = Register(Qubit.random(), Qubit.random(), Qubit.random())
        val algorithm = algorithm(3) {
            GateHadamard[2]
            GateCNot[1, 2]
            GateT.adjoint[2]
            GateCNot[0, 2]
            GateT[2]
            GateCNot[1, 2]
            GateT.adjoint[2]
            GateCNot[0, 2]
            step { GateT[1]; GateT[2] }
            step { GateCNot[0, 1]; GateHadamard[2] }
            step { GateT[0]; GateT.adjoint[1] }
            GateCNot[0, 1]
        }
        assertThat(algorithm.asGate(), equalTo<Gate>(GateCCNot))
//        assertThat(algorithm.run(register), equalTo(GateCCNot * register))
    }

    @Test
    fun `CCNot gate made of 2-qubit gates v2`() {
        val algorithm = algorithm(3) {
            step { GateT.adjoint[0]; GateT.adjoint[1]; GateHadamard[2] }
            step { GateCNot[2, 0] }
            step { GateT[0]; GateCNot[1, 2] }
            step { GateCNot[1, 0]; GateT[2] }
            step { GateT.adjoint[0]; GateCNot[1, 2] }
            step { GateCNot[2, 0] }
            step { GateT[0]; GateT.adjoint[2] }
            step { GateCNot[1, 0]; GateHadamard[2] }
        }
        val register = Register(Qubit.random(), Qubit.random(), Qubit.random())
        assertThat(algorithm.asGate(), equalTo<Gate>(GateCCNot))
//        assertThat(algorithm.run(register), equalTo(GateCCNot * register))
    }
}
