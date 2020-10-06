package com.example.myapplication.utils

fun String.toCelsius() = this.toFloat().toInt().toString() + " °C"
fun Double.toCelsius() = this.toInt().toString() + " °C"