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
