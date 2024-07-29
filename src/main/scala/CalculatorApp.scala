import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.DoubleProperty
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.layout.{GridPane, VBox, Priority}
import scalafx.geometry.Pos

// Main application object
object CalculatorApp extends JFXApp {
  // Create a new Calculator instance
  val calculator = new Calculator()
  val history = new History()

  // Create a TextField for the display
  val display: TextField = new TextField {
    alignment = Pos.CenterRight // Align text to the right
    editable = false // Make the display read-only
    text = "" // Initialize with an empty string
    style = "-fx-background-color: white; -fx-text-fill: black;" // Set display style
  }

  // Create a TextArea for the history log
  val historyDisplay: TextArea = new TextArea {
    editable = false // Make the history log read-only
    style = "-fx-background-color: white; -fx-text-fill: black;" // Set history display style
    wrapText = true // Ensure text wraps within the TextArea
  }

  // Define the button labels in a grid structure
  val buttons = Seq(
    Seq("C", "CL"),
    Seq("7", "8", "9", "/", "sqrt"),
    Seq("4", "5", "6", "*", "^"),
    Seq("1", "2", "3", "-", "+"),
    Seq("", "0", "%", "=")
  )

  // Create a GridPane for the buttons
  val buttonGrid: GridPane = new GridPane {
    hgap = 10 // Horizontal gap between buttons
    vgap = 10 // Vertical gap between buttons
    alignment = Pos.Center // Center the grid

    // Add buttons to the grid
    buttons.zipWithIndex.foreach { case (row, rowIndex) =>
      row.zipWithIndex.foreach { case (buttonText, colIndex) =>
        val button = new Button(buttonText) {
          maxWidth = Double.MaxValue // Allow button to grow horizontally
          maxHeight = Double.MaxValue // Allow button to grow vertically
          style = if (Seq("/", "*", "-", "+", "=", "%", "sqrt", "^").contains(buttonText)) {
            "-fx-background-color: blue; -fx-text-fill: white;" // Set operator button style
          } else if (Seq("C", "CL").contains(buttonText)) {
            "-fx-background-color: red; -fx-text-fill: white;" // Set clear button style
          } else if (buttonText.isEmpty) {
            "" // No style for empty button space
          } else {
            "-fx-background-color: white; -fx-text-fill: blue;" // Set operand button style
          }
          onAction = _ => handleInput(buttonText) // Handle button click
        }
        GridPane.setHgrow(button, Priority.Always)
        GridPane.setVgrow(button, Priority.Always)
        add(button, colIndex, rowIndex)
      }
    }
  }

  // Function to handle button input
  def handleInput(input: String): Unit = {
    input match {
      case "=" =>
        val result = calculator.calculate()
        display.text.value = result
        history.addEntry(calculator.inputSequence)
        historyDisplay.text = history.getEntries().mkString("\n")
      case "C" =>
        calculator.clear()
        display.text.value = ""
      case "CL" =>
        history.clear()
        historyDisplay.text = ""
      case "sqrt" =>
        display.text.value = calculator.sqrt()
      case "^" =>
        calculator.inputOperator("^")
      case op if Seq("+", "-", "*", "/", "%").contains(op) =>
        calculator.inputOperator(op)
      case digit =>
        calculator.inputDigit(digit)
        display.text.value = calculator.currentInput
    }
  }

  // Define the primary stage (main window)
  stage = new PrimaryStage {
    title = "Calculator" // Set the window title
    scene = new Scene {
      root = new VBox {
        alignment = Pos.Center // Center the VBox
        spacing = 20 // Space between elements
        children = Seq(display, buttonGrid, historyDisplay) // Add display, button grid, and history log to the VBox
        style = "-fx-background-color: black;" // Set background color of the VBox
        VBox.setVgrow(display, Priority.Always)
        VBox.setVgrow(buttonGrid, Priority.Always)
        VBox.setVgrow(historyDisplay, Priority.Always)
      }

      // Listener to adjust the font sizes based on window size
      width.onChange { (_, _, newValue) =>
        val newFontSize = newValue.doubleValue() / 30
        display.setStyle(s"-fx-font-size: ${newFontSize}px; -fx-background-color: white; -fx-text-fill: black;")
        historyDisplay.setStyle(s"-fx-font-size: ${newValue.doubleValue() / 50}px; -fx-background-color: white; -fx-text-fill: black;")
      }
    }
  }
}
