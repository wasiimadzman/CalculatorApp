import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.geometry.Pos

object CalculatorApp extends JFXApp {
  val calculator = new Calculator()

  val display: TextField = new TextField {
    alignment = Pos.CenterRight
    editable = false
    text = ""
  }

  val buttons = Seq(
    Seq("7", "8", "9", "/"),
    Seq("4", "5", "6", "*"),
    Seq("1", "2", "3", "-"),
    Seq("0", ".", "=", "+"),
    Seq("C")
  )

  val buttonGrid: GridPane = new GridPane {
    hgap = 10
    vgap = 10
    alignment = Pos.Center
    buttons.zipWithIndex.foreach { case (row, rowIndex) =>
      row.zipWithIndex.foreach { case (buttonText, colIndex) =>
        add(new Button(buttonText) {
          prefWidth = 50
          prefHeight = 50
          onAction = _ => handleInput(buttonText)
        }, colIndex, rowIndex)
      }
    }
  }

  def handleInput(input: String): Unit = {
    input match {
      case "=" => display.text.value = calculator.calculate()
      case "C" =>
        calculator.clear()
        display.text.value = ""
      case op if Seq("+", "-", "*", "/").contains(op) => calculator.inputOperator(op)
      case digit =>
        calculator.inputDigit(digit)
        display.text.value = calculator.currentInput
    }
  }

  stage = new PrimaryStage {
    title = "Calculator"
    scene = new Scene(300, 400) {
      root = new VBox {
        alignment = Pos.Center
        spacing = 20
        children = Seq(display, buttonGrid)
      }
    }
  }
}
