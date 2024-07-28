package com.gosha.rulegamecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gosha.rulegamecompose.ruleScreen.RuleScreen
import com.gosha.rulegamecompose.scroll.Scroll
import com.gosha.rulegamecompose.ui.theme.RuleGameComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RuleGameComposeTheme {
                SideEffect {
                    window.statusBarColor = getColor(R.color.poker_green)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0f6d39)
                ) {
                    val scrollState = Scroll()
                    RuleScreen(scrollState = scrollState)
                }
            }
        }
    }
}


