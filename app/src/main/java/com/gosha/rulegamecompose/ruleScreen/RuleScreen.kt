package com.gosha.rulegamecompose.ruleScreen

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gosha.rulegamecompose.R
import com.gosha.rulegamecompose.utils.ValueList
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun RuleScreen() {

    // Состояние для значения вращения
    var rotationValue by remember { mutableStateOf(0f) }

    // Состояние для выбранного номера
    var number by remember { mutableStateOf(0) }

    // Состояние для отрисовки ставки
    val bet = remember { mutableStateListOf<String>() }

    // Состояние для текста победы или поражения
    var winLose by remember { mutableStateOf("") }

    // Состояние для цвета текста победы или поражения
    var winLoseColor by remember { mutableStateOf(Color.White) }

    // Анимация вращения с длительностью 2000 мс
    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 2000
        ), label = "",
        // Слушатель, вызываемый по завершении анимации
        finishedListener = { finalValue ->
            // Вычисление индекса на основе конечного значения вращения
            val index = ((360 - (finalValue % 360)) / (360f / ValueList.list.size)).roundToInt()
            Log.d("RuleScreen", "Final rotation value: $finalValue, Computed index: $index")

            // Проверка, что индекс находится в пределах списка
            if (index in ValueList.list.indices) {
                number = ValueList.list[index]
                Log.d("RuleScreen", "Selected number: $number")
            } else {
                Log.e("RuleScreen", "Index out of bounds: $index")
            }
            val color = ValueList.colors[index]
            // Проверяем, был ли цвет черным и делаем проверку ставки
            if (color == Color.Black) {
                if (bet.contains("Черное")) {
                    winLose = "Победа"
                    winLoseColor = Color(0xFF44DA43)
                } else if (bet.isEmpty()) {
                    winLose = ""
                    winLoseColor = Color(0xFFDA4343)
                } else {
                    winLose = "Поражение"
                    winLoseColor = Color(0xFFDA4343)
                }
            } else if (color == Color.Red) {
                if (bet.contains("Красное")) {
                    winLose = "Победа"
                    winLoseColor = Color(0xFF44DA43)
                } else if (bet.isEmpty()) {
                    winLose = ""
                    winLoseColor = Color(0xFFDA4343)
                } else {
                    winLose = "Поражение"
                    winLoseColor = Color(0xFFDA4343)
                }
            } else if (color == Color.Green) {
                if (bet.contains("Зеленое")) {
                    winLose = "Победа"
                    winLoseColor = Color(0xFF44DA43)
                } else if (bet.isEmpty()) {
                    winLose = ""
                    winLoseColor = Color(0xFFDA4343)
                } else {
                    winLose = "Поражение"
                    winLoseColor = Color(0xFFDA4343)
                }
            }
        }
    )
    val bitmapLow = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.table_low)
        .asImageBitmap()

    val bitmapTop = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.table_top)
        .asImageBitmap()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Определение цвета текста на основе выбранного номера
        val textColor = if (number in ValueList.list) {
            ValueList.colors[ValueList.list.indexOf(number)]
        } else {
            Color.White
        }

        // Текстовое поле для отображения выбранного номера
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentWidth(),
            text = "Выпало: " + number.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = textColor
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            // Изображение правила, вращающееся на заданный угол
            Image(
                painter = painterResource(R.drawable.rule),
                contentDescription = "Rule",
                Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .rotate(angle)
            )
            // Неподвижное изображение указателя
            Image(
                painter = painterResource(R.drawable.pointer),
                contentDescription = "Pointer",
                Modifier
                    .width(300.dp)
                    .height(300.dp),
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Вы поставили на: ",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = bet.joinToString(separator = ", "),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = winLose,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                fontSize = 18.sp,
                color = winLoseColor,
                fontWeight = FontWeight.Bold,
            )
        }

        Image(
            bitmap = bitmapTop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cell =
                            getCellFromOffsetTop(offset.x, offset.y, bitmapTop.width, bitmapTop.height)
                        handleCellClickTop(cell)
                    }
                }
                .wrapContentSize(align = Alignment.Center)
        )

        Image(
            bitmap = bitmapLow,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth().padding(start = 25.dp, end = 25.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cell =
                            getCellFromOffsetLow(offset.x, offset.y, bitmapLow.width, bitmapLow.height)
                        handleCellClickLow(cell, bet)
                    }
                }
                .wrapContentSize(align = Alignment.Center)
        )

        Button(
            onClick = {
                bet.clear()
                winLose = ""
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFB22222)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        ) {
            Text(
                text = "Сбросить ставку",
                color = Color.White
            )
        }
        // Кнопка для запуска вращения
        Button(
            onClick = {
                // Установка нового значения вращения
                rotationValue = (720..1440).random().toFloat() + angle
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFB22222)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            // Текст на кнопке
            Text(
                text = "Крутить",
                color = Color.White
            )
        }
    }
}

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
                // Если в списке есть элемент "Черное", не добавляем "Красное"
                if (bet.contains("Черное")) {
                    Log.d(
                        "Pick",
                        "Элемент 'Черное' уже существует в списке. Нельзя добавить 'Красное'."
                    )
                } else if (!bet.contains("Красное")) {
                    bet.add("Красное")
                } else {
                    Log.d("Pick", "Элемент 'Красное' уже существует в списке.")
                }
            } else {
                Log.d("Pick", "Максимально количество ставок.")
            }
        }
        "black" -> {
            Log.d("table", "black")

            if (bet.size < 5) {
                // Если в списке есть элемент "Красное", не добавляем "Черное"
                if (bet.contains("Красное")) {
                    Log.d(
                        "Pick",
                        "Элемент 'Красное' уже существует в списке. Нельзя добавить 'Черное'."
                    )
                } else if (!bet.contains("Черное")) {
                    bet.add("Черное")
                } else {
                    Log.d("Pick", "Элемент 'Черное' уже существует в списке.")
                }
            } else {
                Log.d("Pick", "Максимально количество ставок.")
            }
        }
        "odd" -> Log.d("table", "odd")
        "19to36" -> Log.d("table", "19to36")
        // Добавьте действия для каждой ячейки
        else -> println("Unknown cell clicked")
    }
}