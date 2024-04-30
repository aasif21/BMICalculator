package com.example.bmicalculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewmodel: ViewModel() {
    val state = mutableStateOf(MyScreenState(height = "", weight = ""))
    fun updateHeight(inputHeight: String){
        state.value = state.value.copy(height = inputHeight)
    }
    fun updateWeight(inputWeight: String){
        state.value = state.value.copy(weight = inputWeight)
    }
}