# Quantum computing simulator

## Brief introduction to quantum computing 

Classical computers, as we know them, work by manipulating bits that hold the value of either 
**zero** or **one**. 

Quantum computers, on the other hand, utilize qubits&mdash;a quantum-world counterpart of bits. 
Qubits also can hold values of **zero** and **one**, but it can also be in a **superposition**
of the two states, meaning that it can be partially in both states at once.

Qubits are typically represented using a pair of complex numbers labeled **&alpha;** and **&beta;**. 
These two values relate to the probabilities at which the given qubit will be measured as **zero** 
or **one**.
 
Typically we represent the **zero** state as

![\left\lvert 0 \right\rangle = \begin{bmatrix} 0 \\ 1 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%200%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%200%20%5C%5C%201%20%5Cend%7Bbmatrix%7D),

the **one** state as

![\left\lvert 1 \right\rangle = \begin{bmatrix} 1 \\ 0 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%201%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%201%20%5C%5C%200%20%5Cend%7Bbmatrix%7D),

while **superposed** qubits can be represented as

![\left\lvert \psi \right\rangle = \begin{bmatrix} \alpha \\ \beta \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cleft%5Clvert%20%5Cphi%20%5Cright%5Crangle%20%3D%20%5Cbegin%7Bbmatrix%7D%20%5Calpha%20%5C%5C%20%5Cbeta%20%5Cend%7Bbmatrix%7D)

where squares of α and β add up to 1

![\left|\alpha\right|^2 + \left|\beta\right|^2 = 1](https://render.githubusercontent.com/render/math?math=%5Cleft%7C%5Calpha%5Cright%7C%5E2%20%2B%20%5Cleft%7C%5Cbeta%5Cright%7C%5E2%20%3D%201)

It is important to note that even though a qubit can hold any of virtually infinite states, when we
measure it, it always collapses into one of the two basic states&mdash;**zero** or **one**. 

### Global phase and Relative phase of qubit

Because qubits are composed of two complex numbers (which in turn are composed of two real 
numbers), it may seem like there are four *degrees of freedom*, but that is not the case.
The ![\left|\alpha\right|^2 + \left|\beta\right|^2 = 1](https://render.githubusercontent.com/render/math?math=%5Cleft%7C%5Calpha%5Cright%7C%5E2%20%2B%20%5Cleft%7C%5Cbeta%5Cright%7C%5E2%20%3D%201)
constraint removes one *degree of freedom*, and as such, there can be multiple qubit definitions 
with different α and β values representing the same state.

All of the following definitions represent the same quantum state with different *global phases* 
(θ, θ+90°, θ+180°, θ+270° and θ+53.13° respectively). *Global phase* shift under no condition
alters the probabilities of measuring **one** or **zero** and can be safely ignored.

![\begin{bmatrix} 0.8 \\ 0.6i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8%20%5C%5C%200.6i%20%5Cend%7Bbmatrix%7D)
\=
![\begin{bmatrix} 0.8i \\ -0.6 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8i%20%5C%5C%20-0.6%20%5Cend%7Bbmatrix%7D)
\=
![\begin{bmatrix} -0.8 \\ -0.6i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%20-0.8%20%5C%5C%20-0.6i%20%5Cend%7Bbmatrix%7D)
\=
![\begin{bmatrix} -0.8i \\ 0.6 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%20-0.8i%20%5C%5C%200.6%20%5Cend%7Bbmatrix%7D)
\=
![\begin{bmatrix} 0.48+0.64i \\ -0.48+0.36i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.48%2B0.64i%20%5C%5C%20-0.48%2B0.36i%20%5Cend%7Bbmatrix%7D)

The same cannot be said about the *relative phase*. Two quantum states with different *relative
phases* are not equivalent as we will see in the next section. The following definitions represent
different quantum states with different *relative phases* (90°, 180°, 0°, 270°, 343.74° 
respectively).

![\begin{bmatrix} 0.8 \\ 0.6i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8%20%5C%5C%200.6i%20%5Cend%7Bbmatrix%7D)
≠
![\begin{bmatrix} 0.8 \\ -0.6 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8%20%5C%5C%20-0.6%20%5Cend%7Bbmatrix%7D)
≠
![\begin{bmatrix} 0.8 \\ 0.6 \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8%20%5C%5C%200.6%20%5Cend%7Bbmatrix%7D)
≠
![\begin{bmatrix} 0.8 \\ -0.6i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.8%20%5C%5C%20-0.6i%20%5Cend%7Bbmatrix%7D)
≠
![\begin{bmatrix} 0.48+0.64i \\ 0.48+0.36i \end{bmatrix}](https://render.githubusercontent.com/render/math?math=%5Cbegin%7Bbmatrix%7D%200.48%2B0.64i%20%5C%5C%200.48%2B0.36i%20%5Cend%7Bbmatrix%7D)

## Usage

### [Complex numbers](src/main/kotlin/me/khol/quantum/math/Complex.kt)
Complex numbers can be created either from cartesian coordinates:
```kotlin
Complex(re: Number = 0, im: Number = 0)
```
or from polar coordinates:
```kotlin
Complex.fromPolar(theta: Number, radius: Number = 1)
```
For most commonly used Complex numbers there are three predefined values:
```kotlin
val ONE = Complex(1, 0)
val ZERO = Complex(0, 0)
val I = Complex(0, 1)
```
There are several overloaded operators that allow for easier addition, subtraction, multiplication 
and division of complex numbers.

### [Matrices](src/main/kotlin/me/khol/quantum/math/Matrix.kt)
Matrices can be created from number of rows and cols and 1D array of complex numbers:
```kotlin
Matrix(rows: Int, cols: Int, vararg values: Complex)
```
or directly from 2D array of complex numbers:
```kotlin
Matrix(m: List<List<Complex>>)
```
Identity matrix n×n can be created using:
```kotlin
Matrix.identity(size: Int)
```
There are many overloaded operators that allow for easier *addition* and *subtraction* of two 
matrices, *multiplication* and *division* by a number, complex number or another matrix, 
*conjugate transpose* and *tensor* product. 

### [Qubits](src/main/kotlin/me/khol/quantum/Qubit.kt) 
Qubits are defined by two complex numbers which define their probability amplitudes:
```kotlin
Qubit(alpha: Complex, beta: Complex)
```
Two commonly used qubit states are predefined:
```kotlin
val ZERO = Qubit(Complex.ONE, Complex.ZERO)
val ONE = Qubit(Complex.ZERO, Complex.ONE)
```

Qubits can be converted to *bra* ⟨&phi;∣ or *ket* ∣&phi;⟩ matrices and define *dot*, *cross* 
and *tensor* products.

Because two qubits may have different alpha and beta values although they represent the same
physical state they can be *normalized* so that two qubits can be programmatically compared.
This cannot be observed in the physical world but is invaluable in testing.

### [Registers](src/main/kotlin/me/khol/quantum/Register.kt)
Registers are created from a list of qubits:
```kotlin
Register(vararg qubits: Qubit)
```

In contrast to a simple `List<Qubit>` that can only hold independent qubits, a `Register` can
also hold *entangled* qubits, such as *Bell states*.

With the same reasoning as for qubits, registers can be *normalized* for testing purposes.

### [Gates](src/main/kotlin/me/khol/quantum/gate/Gate.kt)
Most of the commonly used gates are predefined:

* [Identity](src/main/kotlin/me/khol/quantum/gate/GateIdentity.kt)
* [Hadamard](src/main/kotlin/me/khol/quantum/gate/GateHadamard.kt)
* [X (Not)](src/main/kotlin/me/khol/quantum/gate/GateX.kt)
* [Y](src/main/kotlin/me/khol/quantum/gate/GateY.kt)
* [Z](src/main/kotlin/me/khol/quantum/gate/GateZ.kt)
* [S](src/main/kotlin/me/khol/quantum/gate/GateS.kt)
* [T](src/main/kotlin/me/khol/quantum/gate/GateT.kt)
* [Phase](src/main/kotlin/me/khol/quantum/gate/GatePhase.kt)
* [Swap](src/main/kotlin/me/khol/quantum/gate/GateSwap.kt)
* [CNot (CX)](src/main/kotlin/me/khol/quantum/gate/GateCNot.kt)
* [Toffoli (CCNot)](src/main/kotlin/me/khol/quantum/gate/GateCCNot.kt)
* [Fredkin (CSwap)](src/main/kotlin/me/khol/quantum/gate/GateCSwap.kt)
* [Custom controlled gate](src/main/kotlin/me/khol/quantum/gate/GateControlled.kt)

It should be straightforward to create custom gates.

Gates can be applied to a single Qubit or Register. 
The size of the gate must match the number of qubits stored by Register or 1 in the case of Qubit.
```kotlin
GateX * Qubit.ONE // Qubit.ZERO

GateSwap * Register(Qubit.ONE, Qubit.ZERO) // Register(Qubit.ZERO, Qubit.ONE)
``` 

### [Algorithms](src/main/kotlin/me/khol/quantum/Algorithm.kt)
Instead of manually applying gates to registers and qubits like this:
```kotlin
val bellState = CNot * Register(Hadamard * ZERO, ZERO)
```
algorithm classes provide a less cluttered and more natural way to 
* combine multiple gates into one,
* reorder inputs of a gate,
* apply gates to registers with different sizes and
* apply multiple gates to a register.

#### GateAlgorithm
Pre-computes application of multiple gates as a standalone gate. As we apply gates 
within the algorithm their transformation matrices are combined.
It allows us to pre-compute a part of an algorithm that is executed repeatedly and 
apply the result gate instead.
    
```kotlin
// Swap gate made using CNot gates 
val swap: Gate = gateAlgorithm(2) {
    CNot[0, 1]
    CNot[1, 0]
    CNot[0, 1]
}
```

#### RunnableAlgorithm
Applies multiple gates to a register, changing the state of its qubits with each step.

```kotlin
// Fully entangled Bell state (∣00⟩ + ∣11⟩) / sqrt(2)  
val bellState: Register = runnableAlgorithm(2) {
    GateHadamard[0]
    GateCNot[0, 1]
}
```

Compared to `gateAlgorithm` that does not have any state per se, `runnableAlgorithm` has a register
that we can measure at any point. 

```kotlin
fun measureAndCollapse(vararg qubitIndices: Int): List<Qubit>
```

Doing so will collapse the state of specified qubits to ∣0⟩ or ∣1⟩ based on their probabilities. 
Other qubits in the register entangled with any of the measured qubits will have their probabilities
updated as well to satisfy constraints imposed by entangled states before the measurement.

