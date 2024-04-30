package com.example.learningcompose

//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Stack

var postfixExpression=""
var result=""
val gradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFF0F5FB), // Hex code for a shade of light blue (cyan)
        Color(0xFF0C8AE1) // Hex code for a shade of blue (change as necessary)
    ),
    start = androidx.compose.ui.geometry.Offset(0f, 0f),
    end = androidx.compose.ui.geometry.Offset(1000f, 1000f) // Adjust as needed
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Fill the available height and width
                    .padding(16.dp)
                    .background(gradientBrush)// Optional padding around the column
            ) {
                // First box with a red background
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Fill the available width
                        .weight(1f) // Take up 50% of the available height

                )
                {
                    BasicTextFieldExample()
                }
                // Second box with a blue background
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Fill the available width
                        .weight(1f) // Take up 50% of the available height
                )
                {
                   // But()
                    Calculator()
                }
            }


//            CreateButton(10,modifier = Modifier)

        }
    }
}
@Composable
fun BasicTextFieldExample() {
    // Use remember to maintain the state of the text field
    var textState by remember { mutableStateOf(TextFieldValue(" ")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

    ) {
        // Display a label for the text field
        Text(text = "Enter text:")

        // Create a BasicTextField
        BasicTextField(
            value = textState,
            onValueChange = { newText ->
                // Update the text state with the new text input
                textState = newText
            },
            modifier = Modifier
                .fillMaxWidth() // Allow the text field to take up the full width of the screen
                .padding(8.dp) // Add padding around the text field
                .background(Color.Gray.copy(alpha = 0.1f)) // Light gray background
                .padding(8.dp) // Padding inside the text field
        )

        // Display the entered text below the text field
        Text(
            text = "You entered: ${textState.text}",
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 50.sp,
        )
    }
}

// Function to evaluate the postfix expression
fun evaluatePostfix(expression: String): Double {
    val stack = Stack<Double>()

    for (token in expression.split(" ")) {
        when {
            token.matches("-?\\d+(\\.\\d+)?".toRegex()) -> stack.push(token.toDouble()) // If token is a number
            token == "+" -> {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                stack.push(operand1 + operand2)
            }
            token == "-" -> {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                stack.push(operand1 - operand2)
            }
            token == "*" -> {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                stack.push(operand1 * operand2)
            }
            token == "/" -> {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                stack.push(operand1 / operand2)
            }
        }
    }
    return stack.pop()
}

 //Function to clear the postfix expression
fun clearPostfix() {
    // Clear any variables or data structures used for storing the postfix expression
    postfixExpression=""
}
@Composable
fun Calculator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp)
    ) {
        // Calculator display
        Text(
            text = result,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.End
        )
        {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                val itemsList = listOf(
                    "AC", "Del", "%", "/",
                    "7", "8", "9", "x",
                    "4", "5", "6", "-",
                    "1", "2", "3", "+",
                    "00", "0", ".", "="
                )
                items(itemsList) { item ->
                    CalculatorButton(
                        item = item,
                        onButtonClick = {
                            when (item) {
                                "=" -> {
                                    val result = evaluatePostfix(postfixExpression)
                                    // Display the result
                                }

                                "AC" -> {
                                    postfixExpression = ""
                                }

                                "Del" -> {
                                    postfixExpression = postfixExpression.dropLast(1)
                                }

                                else -> {
                                    postfixExpression += " $item"
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun CalculatorButton(item: String, onButtonClick: () -> Unit) {
    Button(
        onClick = onButtonClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Text(text = item)
    }
}

// Sample usage of the functions


//@Composable
//fun But() {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(4), // Use 2 columns in the grid, adjust as needed
//            modifier = Modifier
//                .fillMaxSize() // Fill the entire screen
//                .background(gradientBrush) // Apply the gradient as the background
//                .padding(16.dp), // Padding around the grid
//            contentPadding = PaddingValues(8.dp), // Padding inside the grid
//            horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between columns
//            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between rows
//        ) {
//            //  Greeting("Calculator")
//            // Iterate through the list and create a button for each item
//            val itemsList = listOf(
//                "AC",
//                "Del",
//                "%",
//                "/",
//                "7",
//                "8",
//                "9",
//                "x",
//                "4",
//                "5",
//                "6",
//                "-",
//                "1",
//                "2",
//                "3",
//                "+",
//                "00",
//                "0",
//                ".",
//                "="
//            )
//            items(itemsList) { item ->
//                Button(
//                    onClick = {
//                        when (item) {
//                            "=" -> {
//                                val result = evaluatePostfix(postfixExpression)
//                                Text(
//                                    text = "You answer is : ${result}",
//                                    modifier = Modifier.padding(top = 16.dp),
//                                    fontSize = 50.sp,
//                                )
//                            }
//                            "AC" -> {
//                                clearPostfix()
//                            }
//                            else -> {
//                                // Assuming you have some mechanism to build the postfix expression as the user clicks on items
//                                // and store it in a variable called postfixExpression
//                                postfixExpression += " $item"
//                            }
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth() // Fill the available width of the grid cell
//                        .height(65.dp), // Adjust button height as needed
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White, // Set the button background color to white
//                        contentColor = Color.Black
//                    )
//                ) {
//                    Text(text = item)
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxHeight(0.1f)
//                .fillMaxWidth()
//                .clickable { }
//        ) {
//            Text(
//                text = name,
//                modifier = Modifier
//                    .offset(0.dp, 10.dp)
//                    .border(1.dp, Color.Black, RectangleShape)
//                    .padding(5.dp),
//                fontSize = 50.sp// Adjust the text size to your preference, e.g. 24.sp
//            )
//        }
//    }
//
