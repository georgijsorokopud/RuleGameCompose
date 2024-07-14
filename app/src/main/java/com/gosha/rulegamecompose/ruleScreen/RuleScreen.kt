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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gosha.rulegamecompose.R
import com.gosha.rulegamecompose.utils.ValueList
import kotlin.math.roundToInt

@Composable
fun RuleScreen() {
    // Состояние для значения вращения
    var rotationValue by remember {
        mutableStateOf(0f)
    }

    // Состояние для выбранного номера
    var number by remember {
        mutableStateOf(0)
    }

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
                .padding(top = 150.dp)
                .wrapContentHeight()
                .wrapContentWidth(),
            text = number.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = textColor
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            // Изображение правила, вращающееся на заданный угол
            Image(
                painter = painterResource(R.drawable.rule),
                contentDescription = "Rule",
                Modifier
                    .fillMaxSize()
                    .rotate(angle)
            )
            // Неподвижное изображение указателя
            Image(
                painter = painterResource(R.drawable.pointer),
                contentDescription = "Pointer",
                Modifier.fillMaxSize()
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
