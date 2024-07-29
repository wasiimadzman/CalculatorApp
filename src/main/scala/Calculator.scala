class Calculator {
  var currentInput: String = ""
  var operator: Option[String] = None
  var operand1: Double = 0.0
  var operand2: Double = 0.0

  def inputDigit(digit: String): Unit = {
    currentInput += digit
  }

  def inputOperator(op: String): Unit = {
    if (currentInput.nonEmpty) {
      operand1 = currentInput.toDouble
      operator = Some(op)
      currentInput = ""
    }
  }

  def clear(): Unit = {
    currentInput = ""
    operator = None
    operand1 = 0.0
    operand2 = 0.0
  }

  def calculate(): String = {
    if (operator.isDefined && currentInput.nonEmpty) {
      operand2 = currentInput.toDouble
      operator.get match {
        case "+" => operand1 += operand2
        case "-" => operand1 -= operand2
        case "*" => operand1 *= operand2
        case "/" =>
          if (operand2 != 0) operand1 /= operand2
          else return "Error"
      }
      currentInput = operand1.toString
    }
    currentInput
  }
}
