package ipca.example.calculator
import kotlin.math.sqrt

class CalculatorBrain {

    enum class Operation(op: String) {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷"),
        EQUALS("="),
        PERCENTAGE("%"),
        SQUARE_ROOT("√"),
        CLEAR("C"),
        ALL_CLEAR("AC");

        companion object {
            fun parse(string: String): Operation {
                return when (string) {
                    "+" -> ADDITION
                    "-" -> SUBTRACTION
                    "×" -> MULTIPLICATION
                    "÷" -> DIVISION
                    "=" -> EQUALS
                    "%" -> PERCENTAGE
                    "√" -> SQUARE_ROOT
                    "C" -> CLEAR
                    "AC" -> ALL_CLEAR
                    else -> throw IllegalArgumentException("Invalid operation")
                }
            }
        }
    }

    var acumulator: Double = 0.0
    var operator: Operation? = null
    private var operandForPendingOperation: Double = 0.0
    var displayValue: String = "0"

    fun doOperation(operand: Double) {
        when (operator) {
            Operation.ADDITION -> acumulator += operand
            Operation.SUBTRACTION -> acumulator -= operand
            Operation.MULTIPLICATION -> acumulator *= operand
            Operation.DIVISION -> acumulator /= operand
            Operation.PERCENTAGE -> acumulator = operand / 100
            Operation.SQUARE_ROOT -> acumulator = sqrt(operand)
            Operation.CLEAR -> {
                displayValue = "0"
                return
            }
            Operation.ALL_CLEAR -> {
                acumulator = 0.0
                operandForPendingOperation = 0.0
                operator = null
                displayValue = "0"
                return
            }
            Operation.EQUALS -> {
                operandForPendingOperation = operand
                operator = null
                return
            }

            else -> acumulator = operand
        }
        displayValue = acumulator.toString()
    }
}
