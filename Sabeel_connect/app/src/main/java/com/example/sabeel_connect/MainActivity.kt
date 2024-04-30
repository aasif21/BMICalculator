package com.example.sabeel_connect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sabeel_connect.ui.theme.Sabeel_connectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sabeel_connectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //UserOrCouLayout()
                    println("jl")
                }
            }
        }
    }
}
//
@Composable
fun UserOrCouLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFCDF8E1),
                        Color(0xFFF5E0F2),
                        Color(0xFFD5EBF3)
                    )
                )
            ),
        contentAlignment = Alignment.TopCenter // Added alignment for content
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_2),
            contentDescription = null,
            modifier = Modifier
                .paddingFromBaseline(top = 8.dp)
                .size(240.dp)
        )
        Column(
            modifier = Modifier
                .offset(x = 120.dp)
        ) {
            Box(
                modifier = Modifier
                    .paddingFromBaseline(top = 200.dp)
                    .border( // Removed size modifier, as it's redundant when using paddingFromBaseline
                        border = BorderStroke(2.dp, Color.Black)
                    )
            ) {
                BasicText(text = "hello")
            }
        }
    }
}
