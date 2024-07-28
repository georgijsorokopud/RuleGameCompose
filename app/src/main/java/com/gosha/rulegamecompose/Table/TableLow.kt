package com.gosha.rulegamecompose.Table

import android.util.Log
import kotlin.math.floor

fun getCellFromOffsetLow(x: Float, y: Float, width: Int, height: Int): String {
    // Определите размеры и позиции ячеек
    val cellWidth = width / 48
    val cellHeight = height / 16

    val row = floor(x / cellWidth).toInt()
    val column = floor(y / cellHeight).toInt()

    // Возвращает строку или идентификатор ячейки в зависимости от положения
    return when {
        row in 0..4 && column in 0..2 -> "1st12"
        row in 5..9 && column in 0..2 -> "2nd12"
        row in 10..14 && column in 0..2 -> "3rd12"

        row in 0..1 && column in 3..5 -> "1to18"
        row in 2..4 && column in 3..5 -> "even"
        row in 5..7 && column in 3..5 -> "red"
        row in 8..9 && column in 3..5 -> "black"
        row == 11 && column in 3..5 -> "odd"
        row in 13..14 && column in 3..5 -> "19to36"

        else -> "unknown"
    }
}

fun handleCellClickLow(cell: String, bet: MutableList<String>) {
    when (cell) {
        "1st12" -> Log.d("table", "1st12")
        "2nd12" -> Log.d("table", "2nd12")
        "3rd12" -> Log.d("table", "3rd12")
        "1to18" -> Log.d("table", "1to18")
        "even" -> Log.d("table", "even")
        "red" -> {
            Log.d("table", "red")
            if (bet.size < 5) {
                if (bet.contains("Черное")) {
                    Log.d("Pick", "Элемент 'Черное' уже существует в списке. Нельзя добавить 'Красное'.")
                } else if (!bet.contains("Красное")) {
                    bet.add("Красное")
                } else {
                    Log.d("Pick", "Элемент 'Красное' уже существует в списке.")
                }
            } else {
                Log.d("Pick", "Максимальное количество ставок.")
            }
        }
        "black" -> {
            Log.d("table", "black")
            if (bet.size < 5) {
                if (bet.contains("Красное")) {
                    Log.d("Pick", "Элемент 'Красное' уже существует в списке. Нельзя добавить 'Черное'.")
                } else if (!bet.contains("Черное")) {
                    bet.add("Черное")
                } else {
                    Log.d("Pick", "Элемент 'Черное' уже существует в списке.")
                }
            } else {
                Log.d("Pick", "Максимальное количество ставок.")
            }
        }
        "odd" -> Log.d("table", "odd")
        "19to36" -> Log.d("table", "19to36")
        else -> println("Unknown cell clicked")
    }
}
