package com.gosha.rulegamecompose.Table

import android.util.Log
import kotlin.math.floor

    fun getCellFromOffsetTop(x: Float, y: Float, width: Int, height: Int): String {
        // Определите размеры и позиции ячеек
        val cellWidth = width / 48
        val cellHeight = height / 16

        val row = floor(x / cellWidth).toInt()
        val column = floor(y / cellHeight).toInt()

        // Возвращает строку или идентификатор ячейки в зависимости от положения
        return when {
            row == 0 && column in 0..5 -> "0"
            row in 0..1 && column in 3..5 -> "1"
            row in 0..1 && column in 2..4 -> "2"
            row in 0..1 && column in 0..1 -> "3"
            row == 2 && column in 3..5 -> "4"
            row == 2 && column in 1..2 -> "5"
            row == 2 && column in 0..1 -> "6"
            row == 3 && column in 3..5 -> "7"
            row == 3 && column in 1..2 -> "8"
            row == 3 && column in 0..1 -> "9"
            row == 4 && column in 3..5 -> "10"
            row == 4 && column in 1..2 -> "11"
            row == 4 && column in 0..1 -> "12"
            row == 5 && column in 3..5 -> "13"
            row == 5 && column in 1..2 -> "14"
            row == 5 && column in 0..1 -> "15"
            row == 6 && column in 3..5 -> "16"
            row == 6 && column in 1..2 -> "17"
            row == 6 && column in 0..1 -> "18"
            row == 7 && column in 3..5 -> "19"
            row == 7 && column in 1..2 -> "20"
            row == 7 && column in 0..1 -> "21"
            row == 8 && column in 3..5 -> "22"
            row == 8 && column in 1..2 -> "23"
            row == 8 && column in 0..1 -> "24"
            row == 9 && column in 3..5 -> "25"
            row == 9 && column in 1..2 -> "26"
            row == 9 && column in 0..1 -> "27"
            row == 10 && column in 3..5 -> "28"
            row == 10 && column in 1..2 -> "29"
            row == 10 && column in 0..1 -> "30"
            row == 11 && column in 3..5 -> "31"
            row == 11 && column in 1..2 -> "32"
            row == 11 && column in 0..1 -> "33"
            row == 12 && column in 3..5 -> "34"
            row == 12 && column in 1..2 -> "35"
            row == 12 && column in 0..1 -> "36"
            row == 13 && column in 3..5 -> "2to1L"
            row == 13 && column in 1..2 -> "2to1M"
            row == 13 && column in 0..1 -> "2to1T"

            else -> "unknown"
        }
    }

    fun handleCellClickTop(cell: String) {
        when (cell) {
            "0" -> Log.d("table", "0")
            "1" -> Log.d("table", "1")
            "2" -> Log.d("table", "2")
            "3" -> Log.d("table", "3")
            "4" -> Log.d("table", "4")
            "5" -> Log.d("table", "5")
            "6" -> Log.d("table", "6")
            "7" -> Log.d("table", "7")
            "8" -> Log.d("table", "8")
            "9" -> Log.d("table", "9")
            "10" -> Log.d("table", "10")
            "11" -> Log.d("table", "11")
            "12" -> Log.d("table", "12")
            "13" -> Log.d("table", "13")
            "14" -> Log.d("table", "14")
            "15" -> Log.d("table", "15")
            "16" -> Log.d("table", "16")
            "17" -> Log.d("table", "17")
            "18" -> Log.d("table", "18")
            "19" -> Log.d("table", "19")
            "20" -> Log.d("table", "20")
            "21" -> Log.d("table", "21")
            "22" -> Log.d("table", "22")
            "23" -> Log.d("table", "23")
            "24" -> Log.d("table", "24")
            "25" -> Log.d("table", "25")
            "26" -> Log.d("table", "26")
            "27" -> Log.d("table", "27")
            "28" -> Log.d("table", "28")
            "29" -> Log.d("table", "29")
            "30" -> Log.d("table", "30")
            "31" -> Log.d("table", "31")
            "32" -> Log.d("table", "32")
            "33" -> Log.d("table", "33")
            "34" -> Log.d("table", "34")
            "35" -> Log.d("table", "35")
            "36" -> Log.d("table", "36")
            "2to1L" -> Log.d("table", "2to1L")
            "2to1M" -> Log.d("table", "2to1M")
            "2to1T" -> Log.d("table", "2to1T")

            // Добавьте действия для каждой ячейки
            else -> println("Unknown cell clicked")
        }
    }
