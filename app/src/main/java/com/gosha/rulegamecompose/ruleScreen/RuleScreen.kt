// RuleScreen.kt
package com.gosha.rulegamecompose.ruleScreen

import android.graphics.BitmapFactory
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
import com.gosha.rulegamecompose.Table.getCellFromOffsetLow
import com.gosha.rulegamecompose.Table.getCellFromOffsetTop
import com.gosha.rulegamecompose.Table.handleCellClickLow
import com.gosha.rulegamecompose.Table.handleCellClickTop
import com.gosha.rulegamecompose.scroll.ScrollState
import com.gosha.rulegamecompose.utils.ValueList

@Composable
fun RuleScreen(scrollState: ScrollState) {

    val bitmapLow = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.table_low)
        .asImageBitmap()

    val bitmapTop = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.table_top)
        .asImageBitmap()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val textColor = if (scrollState.number in ValueList.list) {
            ValueList.colors[ValueList.list.indexOf(scrollState.number)]
        } else {
            Color.White
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentWidth(),
            text = "Выпало: " + scrollState.number.toString(),
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
            Image(
                painter = painterResource(R.drawable.rule),
                contentDescription = "Rule",
                Modifier
                    .width(300.dp)
                    .height(300.dp)
                    .rotate(scrollState.angle)
            )
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
                text = scrollState.bet.joinToString(separator = ", "),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = scrollState.winLose,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                fontSize = 18.sp,
                color = scrollState.winLoseColor,
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
                        handleCellClickLow(cell, scrollState.bet)
                    }
                }
                .wrapContentSize(align = Alignment.Center)
        )

        Button(
            onClick = {
                scrollState.clearBet()
                scrollState.setWinLose("")
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
                scrollState.setRotationValue((720..1440).random().toFloat() + scrollState.angle)
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

