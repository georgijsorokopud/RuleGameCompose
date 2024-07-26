package com.gosha.rulegamecompose.ruleScreen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gosha.rulegamecompose.R
import com.gosha.rulegamecompose.utils.ValueList
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
            if (number == 0) { // Индекс 0 соответствует номеру 1 (нулю на рулетке)
                if (bet.isNotEmpty()) {
                    winLose = ""
                    winLoseColor = Color(0xFFDA4343) // Красный цвет для поражения
                } else {
                    winLose = ""
                }
            } else {
                if (color == Color.Black) {
                    if (bet.contains("Черное")) {
                        winLose = "Победа"
                        winLoseColor = Color(0xFF44DA43)
                    } else {
                        winLose = "Поражение"
                        winLoseColor = Color(0xFFDA4343)
                    }
                } else if (color == Color.Red) {
                    if (bet.contains("Красное")) {
                        winLose = "Победа"
                        winLoseColor = Color(0xFF44DA43)
                    } else {
                        winLose = "Поражение"
                        winLoseColor = Color(0xFFDA4343)
                    }
                } else {
                    winLose = ""
                    winLoseColor = Color(0xFFDA4343)
                }
            }
        }
    )
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
                .padding(top = 10.dp)
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
                    .height(300.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                //кнопки, которые добавляют текст ставки
                Button(
                    onClick = {
                        if (bet.size < 5) {
                            // Если в списке есть элемент "Красное", не добавляем "Черное"
                            if (bet.contains("Красное")) {
                                Log.d("BlackPick", "Элемент 'Красное' уже существует в списке. Нельзя добавить 'Черное'.")
                            } else if (!bet.contains("Черное")) {
                                bet.add("Черное")
                            } else {
                                Log.d("BlackPick", "Элемент 'Черное' уже существует в списке.")
                            }
                        } else {
                            Log.d("BlackPick", "Максимально количество ставок.")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier.padding(end = 14.dp)
                ) {
                    Text(
                        text = "Черное",
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                        if (bet.size < 5) {
                            // Если в списке есть элемент "Черное", не добавляем "Красное"
                            if (bet.contains("Черное")) {
                                Log.d("RedPick", "Элемент 'Черное' уже существует в списке. Нельзя добавить 'Красное'.")
                            } else if (!bet.contains("Красное")) {
                                bet.add("Красное")
                            } else {
                                Log.d("RedPick", "Элемент 'Красное' уже существует в списке.")
                            }
                        } else {
                            Log.d("RedPick", "Максимально количество ставок.")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFB22222)),
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        text = "Красное",
                        color = Color.White
                    )
                }
                Text(
                    text = "Вы поставили на: ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 190.dp, start = 10.dp),
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
                .padding(10.dp)
        ) {
            // Текст на кнопке
            Text(
                text = "Start",
                color = Color.White
            )
        }
    }
}
