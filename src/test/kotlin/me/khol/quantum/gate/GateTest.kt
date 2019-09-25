package me.khol.quantum.gate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Mathematical properties of gates")
class GateTest {

    @DisplayName("Gate should be reversible")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateHadamard::class,
        GateIdentity1::class
    ])
    fun <T: Gate> isGateReversible(clazz: Class<T>) {
        val matrix = clazz.kotlin.objectInstance!!.matrix
        val squared = matrix * matrix
        assertEquals(GateIdentity1.matrix, squared)
    }

    @DisplayName("Gates should be normal")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateHadamard::class,
        GateIdentity1::class
    ])
    fun <T: Gate> isGateNormal(clazz: Class<T>) {
        val matrix = clazz.kotlin.objectInstance!!.matrix
        val adjoint = matrix.conjugate().transpose()
        assertEquals(matrix * adjoint, adjoint * matrix)
    }

    @DisplayName("Gates should be unitary")
    @ParameterizedTest
    @ValueSource(classes = [
        GateX::class,
        GateY::class,
        GateZ::class,
        GateHadamard::class,
        GateIdentity1::class
    ])
    fun <T: Gate> isGateUnitary(clazz: Class<T>) {
        val matrix = clazz.kotlin.objectInstance!!.matrix
        val adjoint = matrix.conjugate().transpose()
        assertEquals(GateIdentity1.matrix, matrix * adjoint)
        assertEquals(GateIdentity1.matrix, adjoint * matrix)
    }
}
