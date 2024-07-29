import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.geometry.Pos

// Main application object
object CalculatorApp extends JFXApp {
  // Create a new Calculator instance
  val calculator = new Calculator()

  // Create a TextField for the display
  val display: TextField = new TextField {
    alignment = Pos.CenterRight // Align text to the right
    editable = false // Make the display read-only
    text = "" // Initialize with an empty string
    style = "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;" // Set display style
  }

  // Define the button labels in a grid structure
  val buttons = Seq(
    Seq("C"),
    Seq("7", "8", "9", "/"),
    Seq("4", "5", "6", "*"),
    Seq("1", "2", "3", "-"),
    Seq("%", "0", "+", "="),
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
          prefWidth = 50 // Set button width
          prefHeight = 50 // Set button height
          style = if (Seq("/", "*", "-", "+", "=", "%").contains(buttonText)) {
            "-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 18px;" // Set operator button style
          } else if (buttonText == "C") {
            "-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 18px;" // Set clear button style
          } else {
            "-fx-background-color: white; -fx-text-fill: blue; -fx-font-size: 18px;" // Set operand button style
          }
          onAction = _ => handleInput(buttonText) // Handle button click
        }
        add(button, colIndex, rowIndex)
      }
    }
  }

  // Function to handle button input
  def handleInput(input: String): Unit = {
    input match {
      case "=" =>
        // Calculate the result and update display
        display.text.value = calculator.calculate()
      case "C" =>
        // Clear the calculator and display
        calculator.clear()
        display.text.value = ""
      case op if Seq("+", "-", "*", "/", "%").contains(op) =>
        // Handle operator input
        calculator.inputOperator(op)
      case digit =>
        // Handle digit input
        calculator.inputDigit(digit)
        display.text.value = calculator.currentInput
    }
  }

  // Define the primary stage (main window)
  stage = new PrimaryStage {
    title = "Calculator" // Set the window title
    scene = new Scene(300, 400) { // Set the window size
      root = new VBox {
        alignment = Pos.Center // Center the VBox
        spacing = 20 // Space between elements
        children = Seq(display, buttonGrid) // Add display and button grid to the VBox
        style = "-fx-background-color: black;" // Set background color of the VBox
      }
    }
  }
}
