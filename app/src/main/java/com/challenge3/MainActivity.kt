package com.challenge3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge3.ui.theme.Challenge3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Challenge3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun CalculatorApp(modifier: Modifier){
    var display by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var activeOperation by remember { mutableStateOf<String?>(null) }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.Black))
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = display,
                color = Color.White,
                fontSize = when {
                    display.length > 12 -> 40.sp
                    display.length > 8 -> 50.sp
                    else -> 72.sp
                },
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ButtonGrid(
            activeOperation = activeOperation,
            onNumberClick = { number ->
                onNumberClick(
                    number = number,
                    display = display,
                    firstNumber = firstNumber,
                    secondNumber = secondNumber,
                    operation = operation,
                    setDisplay = { display = it },
                    setFirstNumber = { firstNumber = it },
                    setSecondNumber = { secondNumber = it })
            },
            onOperationClick = { op ->
                onOperationClick(
                    op = op,
                    firstNumber = firstNumber,
                    secondNumber = secondNumber,
                    display = display,
                    operation = operation,
                    setDisplay = { display = it },
                    setFirstNumber = { firstNumber = it },
                    setSecondNumber = { secondNumber = it },
                    setOperation = { operation = it },
                    setActiveOperation = { activeOperation = it })
            },
            onFunctionClick = { func ->
                onFunctionClick(
                    buttonType = func,
                    display = display,
                    firstNumber = firstNumber,
                    secondNumber = secondNumber,
                    operation = operation,
                    setDisplay = { display = it },
                    setFirstNumber = { firstNumber = it },
                    setSecondNumber = { secondNumber = it },
                    setOperation = { operation = it },
                    setActiveOperation = { activeOperation = it })
            }
        )
    }
}

@Composable
fun ButtonGrid(
    activeOperation: String?,
    onNumberClick: (String) -> Unit,
    onOperationClick: (String) -> Unit,
    onFunctionClick: (ButtonType) -> Unit
) {
    val buttonModifier = Modifier
        .aspectRatio(1f)
        .padding(4.dp)

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton("AC", Color.Gray, buttonModifier.weight(1f)) { onFunctionClick(
                ButtonType.Clear
            ) }
            CalculatorButton("+/-", Color.Gray, buttonModifier.weight(1f)) { onFunctionClick(
                ButtonType.Negative
            ) }
            CalculatorButton("%", if (activeOperation == "%") Color.White else Color(0xFFFF9500), buttonModifier.weight(1f)) { onOperationClick("%") }
            CalculatorButton("÷", if (activeOperation == "/") Color.White else Color(0xFFFF9500), buttonModifier.weight(1f)) { onOperationClick("/") }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton("7", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("7") }
            CalculatorButton("8", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("8") }
            CalculatorButton("9", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("9") }
            CalculatorButton("×", if (activeOperation == "*") Color.White else Color(0xFFFF9500), buttonModifier.weight(1f)) { onOperationClick("*") }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton("4", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("4") }
            CalculatorButton("5", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("5") }
            CalculatorButton("6", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("6") }
            CalculatorButton("-", if (activeOperation == "-") Color.White else Color(0xFFFF9500), buttonModifier.weight(1f)) { onOperationClick("-") }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton("1", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("1") }
            CalculatorButton("2", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("2") }
            CalculatorButton("3", Color.DarkGray, buttonModifier.weight(1f)) { onNumberClick("3") }
            CalculatorButton("+", if (activeOperation == "+") Color.White else Color(0xFFFF9500), buttonModifier.weight(1f)) { onOperationClick("+") }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            CalculatorButton("0", Color.DarkGray, Modifier.weight(2f).padding(4.dp).aspectRatio(2f)) { onNumberClick("0") }
            CalculatorButton(".", Color.DarkGray, buttonModifier.weight(1f)) { onFunctionClick(
                ButtonType.Dot
            ) }
            CalculatorButton("=", Color(0xFFFF9500), buttonModifier.weight(1f)) { onFunctionClick(
                ButtonType.Equal
            ) }
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = if (symbol != "0") CircleShape else RoundedCornerShape(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = if(backgroundColor == Color(0xFFFF9500) || backgroundColor == Color.DarkGray) Color.White else Color.Black)
    ) {
        Text(text = symbol, fontSize = 28.sp)
    }
}

enum class ButtonType {
    Clear, Negative, Dot, Equal
}

fun onNumberClick(
    number: String,
    display: String,
    firstNumber: String,
    secondNumber: String,
    operation: String,
    setDisplay: (String) -> Unit,
    setFirstNumber: (String) -> Unit,
    setSecondNumber: (String) -> Unit
) {
    if (operation.isEmpty()) {
        // Update firstNumber
        val updatedFirstNumber = if (display == "0") number else display + number
        setDisplay(updatedFirstNumber)
        setFirstNumber(updatedFirstNumber)
    } else {
        // Update secondNumber
        val updatedSecondNumber = if (secondNumber.isEmpty()) number else display + number
        setDisplay(updatedSecondNumber)
        setSecondNumber(updatedSecondNumber)
    }
}

fun onOperationClick(
    op: String,
    firstNumber: String,
    secondNumber: String,
    display: String,
    operation: String,
    setDisplay: (String) -> Unit,
    setFirstNumber: (String) -> Unit,
    setSecondNumber: (String) -> Unit,
    setOperation: (String) -> Unit,
    setActiveOperation: (String?) -> Unit
) {
    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
        calculate(firstNumber, secondNumber, operation, setDisplay, setFirstNumber, setSecondNumber, setOperation)
    }
    setOperation(op)
    setActiveOperation(op)
}

fun onFunctionClick(
    buttonType: ButtonType,
    display: String,
    firstNumber: String,
    secondNumber: String,
    operation: String,
    setDisplay: (String) -> Unit,
    setFirstNumber: (String) -> Unit,
    setSecondNumber: (String) -> Unit,
    setOperation: (String) -> Unit,
    setActiveOperation: (String?) -> Unit
) {
    when (buttonType) {
        ButtonType.Clear -> {
            setDisplay("0")
            setFirstNumber("")
            setSecondNumber("")
            setOperation("")
            setActiveOperation(null)
        }
        ButtonType.Negative -> {
            setDisplay(if (display.startsWith("-")) display.removePrefix("-") else "-$display")
        }
        ButtonType.Dot -> {
            if (!display.contains(".")) {
                setDisplay("$display.")
            }
        }
        ButtonType.Equal -> {
            calculate(firstNumber, secondNumber, operation, setDisplay, setFirstNumber, setSecondNumber, setOperation)
            setActiveOperation(null) // Reset active button
        }
        else -> {}
    }
}

fun calculate(firstNumber: String, secondNumber: String, operation: String, setDisplay: (String) -> Unit, setFirstNumber: (String) -> Unit, setSecondNumber: (String) -> Unit, setOperation: (String) -> Unit) {
    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operation.isNotEmpty()) {
        val result = when (operation) {
            "+" -> firstNumber.toDouble() + secondNumber.toDouble()
            "-" -> firstNumber.toDouble() - secondNumber.toDouble()
            "*" -> firstNumber.toDouble() * secondNumber.toDouble()
            "/" -> firstNumber.toDouble() / secondNumber.toDouble()
            "%" -> firstNumber.toDouble() % secondNumber.toDouble()
            else -> 0.0
        }
        setDisplay(result.toString())
        setFirstNumber(result.toString())
        setSecondNumber("")
        setOperation("")
    }
}
