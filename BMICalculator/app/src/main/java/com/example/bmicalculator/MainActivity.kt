package com.example.bmicalculator

import android.content.Context
import android.media.browse.MediaBrowser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    val viewModel by lazy{ ViewModelProvider(this).get(MyViewmodel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state
            BMICalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xffaec6cf))
                        .padding(20.dp)
                    ,
                ) {
                    BMICalculator(
                        myHeight = state.value.height,
                        myWeight = state.value.weight,
                        onHeightChange = {
                            viewModel.updateHeight(it)

                        },
                        onWeightChange = {
                            viewModel.updateWeight(it)

                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMICalculator(
    myHeight: String,
    myWeight: String,
    onHeightChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffaec6cf))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xff85a1f2), shape = RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
                .shadow(2.dp)
            ,
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "BMI Calculator",
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Enter your height (in cm):",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = myHeight.toString(),
            onValueChange = {
                onHeightChange(it)
                println("myHeight " + myHeight)
            },
            textStyle = TextStyle(Color.Black),
            modifier = Modifier
                .background(Color.White,shape = RoundedCornerShape(25.dp)),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White, // Set background color here
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Enter your weight (in kg):",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = myWeight.toString(),
            onValueChange = {
                onWeightChange(it)
                println("myWeight " + myWeight)
            },
            textStyle = TextStyle(Color.Black),
            modifier = Modifier
                .background(Color.White,shape = RoundedCornerShape(25.dp)),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White, // Set background color here
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        val BMI = if (myHeight.isNotEmpty() && myWeight.isNotEmpty()) {
            myWeight.toFloat() * 100 * 100 / (myHeight.toFloat() * myHeight.toFloat())
        } else {
            0.0f
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Your BMI is: $BMI",
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(80.dp))

            val result = if (BMI < 18.5) "You are underweight" else if (BMI >= 18.8 && BMI <= 24.9) "Good job, you are healthy" else if(BMI >= 25 && BMI <= 29.9) "You need to lose some weight, you are overweight" else "You are obese, you need to lose a lot of weight"
            Text(
                text = result,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
