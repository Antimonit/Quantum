### Global phase and Relative phase of qubit

Because qubits are composed of two complex numbers (which in turn are composed of two real 
numbers), it may seem like there are four *degrees of freedom*, but that is not the case.
The $`\left|\alpha\right|^2 + \left|\beta\right|^2 = 1`$
constraint removes one *degree of freedom*, and as such, there can be multiple qubit definitions 
with different α and β values representing the same state.

All of the following definitions represent the same quantum state with different *global phases* 
(θ, θ+90°, θ+180°, θ+270° and θ+53.13° respectively). *Global phase* shift under no condition
alters the probabilities of measuring **one** or **zero** and can be safely ignored.

$`\begin{bmatrix} 0.8 \\ 0.6i \end{bmatrix}`$
\=
$`\begin{bmatrix} 0.8i \\ -0.6 \end{bmatrix}`$
\=
$`\begin{bmatrix} -0.8 \\ -0.6i \end{bmatrix}`$
\=
$`\begin{bmatrix} -0.8i \\ 0.6 \end{bmatrix}`$
\=
$`\begin{bmatrix} 0.48+0.64i \\ -0.48+0.36i \end{bmatrix}`$

The same cannot be said about the *relative phase*. Two quantum states with different *relative
phases* are not equivalent as we will see in the next section. The following definitions represent
different quantum states with different *relative phases* (90°, 180°, 0°, 270°, 343.74° 
respectively).

$`\begin{bmatrix} 0.8 \\ 0.6i \end{bmatrix}`$
≠
$`\begin{bmatrix} 0.8 \\ -0.6 \end{bmatrix}`$
≠
$`\begin{bmatrix} 0.8 \\ 0.6 \end{bmatrix}`$
≠
$`\begin{bmatrix} 0.8 \\ -0.6i \end{bmatrix}`$
≠
$`\begin{bmatrix} 0.48+0.64i \\ 0.48+0.36i \end{bmatrix}`$
