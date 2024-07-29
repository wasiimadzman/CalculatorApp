import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.layout.{GridPane, VBox, Priority}
import scalafx.geometry.Pos
import scala.collection.JavaConverters._

object CalculatorApp extends JFXApp {
  // Create an instance of the Calculator class
  val calculator = new Calculator()
  // Create an instance of the History class
  val history = new History()

  // Create a display for showing the current input and result
  val display: TextField = new TextField {
    alignment = Pos.CenterRight
    editable = false
    text = ""
    style = "-fx-background-color: white; -fx-text-fill: black;"
  }

  // Create a display for showing the history of calculations
  val historyDisplay: TextArea = new TextArea {
    editable = false
    style = "-fx-background-color: white; -fx-text-fill: black;"
    wrapText = true
  }

  // Define the buttons to be displayed on the calculator
  val buttons = Seq(
    Seq("C", "CL"),
    Seq("7", "8", "9", "/", "sqrt"),
    Seq("4", "5", "6", "*", "^"),
    Seq("1", "2", "3", "-", "+"),
    Seq("", "0", "%", "=")
  )

  // Create a grid to hold the buttons
  val buttonGrid: GridPane = new GridPane {
    hgap = 10
    vgap = 10
    alignment = Pos.Center

    // Iterate through the rows and columns to create buttons
    buttons.zipWithIndex.foreach { case (row, rowIndex) =>
      row.zipWithIndex.foreach { case (buttonText, colIndex) =>
        val button = new Button(buttonText) {
          maxWidth = Double.MaxValue
          maxHeight = Double.MaxValue
          style = if (Seq("/", "*", "-", "+", "=", "%", "sqrt", "^").contains(buttonText)) {
            // Style for operator buttons
            "-fx-background-color: blue; -fx-text-fill: white; -fx-border-radius: 10px; -fx-background-radius: 10px;"
          } else if (Seq("C", "CL").contains(buttonText)) {
            // Style for clear buttons
            "-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 10px; -fx-background-radius: 10px;"
          } else if (buttonText.isEmpty) {
            // Empty button style
            ""
          } else {
            // Style for digit buttons
            "-fx-background-color: white; -fx-text-fill: blue; -fx-border-radius: 10px; -fx-background-radius: 10px;"
          }
          // Set the action to handle button clicks
          onAction = _ => handleInput(buttonText)
        }

        // Adjust the font size dynamically based on button height
        button.layoutBoundsProperty().addListener { (_, _, bounds) =>
          val newFontSize = bounds.getHeight * 0.5
          button.setStyle(s"-fx-font-size: ${newFontSize}px; ${button.getStyle}")
        }

        // Make the buttons grow to fill the available space
        GridPane.setHgrow(button, Priority.Always)
        GridPane.setVgrow(button, Priority.Always)
        add(button, colIndex, rowIndex)
      }
    }
  }

  // Handle input from the buttons
  def handleInput(input: String): Unit = {
    input match {
      case "=" =>
        // Calculate the result and update the display and history
        val result = calculator.calculate()
        display.text.value = result
        history.addEntry(calculator.inputSequence)
        historyDisplay.text = history.getEntries().mkString("\n")
      case "C" =>
        // Clear the current input
        calculator.clear()
        display.text.value = ""
      case "CL" =>
        // Clear the history
        history.clear()
        historyDisplay.text = ""
      case "sqrt" =>
        // Calculate the square root and update the display
        display.text.value = calculator.sqrt()
      case "^" =>
        // Input the power operator
        calculator.inputOperator("^")
      case op if Seq("+", "-", "*", "/", "%").contains(op) =>
        // Input other operators
        calculator.inputOperator(op)
      case digit =>
        // Input digits
        calculator.inputDigit(digit)
        display.text.value = calculator.currentInput
    }
  }

  // Set up the main stage (window) of the application
  stage = new PrimaryStage {
    title = "Calculator"
    scene = new Scene {
      root = new VBox {
        alignment = Pos.Center
        spacing = 20
        children = Seq(display, buttonGrid, historyDisplay)
        style = "-fx-background-color: black;"
        VBox.setVgrow(display, Priority.Always)
        VBox.setVgrow(buttonGrid, Priority.Always)
        VBox.setVgrow(historyDisplay, Priority.Always)
      }

      // Adjust the font sizes when the window is resized
      width.onChange { (_, _, newWidth) =>
        val newFontSize = newWidth.doubleValue() / 30
        display.setStyle(s"-fx-font-size: ${newFontSize}px; -fx-background-color: white; -fx-text-fill: black;")
        historyDisplay.setStyle(s"-fx-font-size: ${newWidth.doubleValue() / 50}px; -fx-background-color: white; -fx-text-fill: black;")
      }

      height.onChange { (_, _, newHeight) =>
        val newFontSize = newHeight.doubleValue() / 30
        display.setStyle(s"-fx-font-size: ${newFontSize}px; -fx-background-color: white; -fx-text-fill: black;")
        historyDisplay.setStyle(s"-fx-font-size: ${newHeight.doubleValue() / 50}px; -fx-background-color: white; -fx-text-fill: black;")
      }
    }
  }
}
