
package com.gosha.rulegamecompose.scroll

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.gosha.rulegamecompose.utils.ValueList
import kotlin.math.roundToInt

@Composable
fun Scroll(): ScrollState {
    var rotationValue by remember { mutableStateOf(0f) }
    val bet = remember { mutableStateListOf<String>() }
    var number by remember { mutableStateOf(0) }
    var winLose by remember { mutableStateOf("") }
    var winLoseColor by remember { mutableStateOf(Color.White) }

    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(durationMillis = 2000),
        label = "",
        finishedListener = { finalValue ->
            val index = ((360 - (finalValue % 360)) / (360f / ValueList.list.size)).roundToInt()
            Log.d("RuleScreen", "Final rotation value: $finalValue, Computed index: $index")

            if (index in ValueList.list.indices) {
                number = ValueList.list[index]
                Log.d("RuleScreen", "Selected number: $number")
            } else {
                Log.e("RuleScreen", "Index out of bounds: $index")
            }
            val color = ValueList.colors[index]
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

    return ScrollState(
        rotationValue = rotationValue,
        bet = bet,
        number = number,
        winLose = winLose,
        winLoseColor = winLoseColor,
        angle = angle,
        setRotationValue = { rotationValue = it },
        setWinLose = { winLose = it },
        clearBet = { bet.clear() }
    )
}



data class ScrollState(
    var rotationValue: Float,
    val bet: MutableList<String>,
    var number: Int,
    var winLose: String,
    var winLoseColor: Color,
    val angle: Float,
    val setRotationValue: (Float) -> Unit,
    val setWinLose: (String) -> Unit,
    val clearBet: () -> Unit
)


