class Calculator {
  var currentInput: String = "" // Stores the current input
  var operator: Option[String] = None // Stores the current operator
  var operand1: Double = 0.0 // First operand
  var operand2: Double = 0.0 // Second operand
  var inputSequence: String = "" // Stores the entire input sequence

  // Add a digit to the current input
  def inputDigit(digit: String): Unit = {
    currentInput += digit
    inputSequence += digit
  }

  // Set the operator and save the first operand
  def inputOperator(op: String): Unit = {
    if (currentInput.nonEmpty) {
      operand1 = currentInput.toDouble
      operator = Some(op)
      currentInput = ""
      inputSequence += s" $op "
    }
  }

  // Clear the calculator
  def clear(): Unit = {
    currentInput = ""
    operator = None
    operand1 = 0.0
    operand2 = 0.0
    inputSequence = ""
  }

  // Perform the calculation based on the operator
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
        case "%" =>
          if (operand2 != 0) operand1 %= operand2
          else return "Error"
        case "^" => operand1 = Math.pow(operand1, operand2)
      }
      currentInput = operand1.toString
      inputSequence += s"= $currentInput"
    }
    currentInput
  }

  // Calculate the square root
  def sqrt(): String = {
    if (currentInput.nonEmpty) {
      operand1 = currentInput.toDouble
      operand1 = Math.sqrt(operand1)
      currentInput = operand1.toString
      inputSequence += s" sqrt($operand1) = $currentInput"
    }
    currentInput
  }
}
