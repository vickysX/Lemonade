package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

enum class LemonScreens {
    SELECT, SQUEEZE, DRINK, RESTART
}

@Composable
fun LemonadeApp() {
    var task : LemonScreens by remember {
        mutableStateOf(LemonScreens.SELECT)
    }
    val textResource : Int = when (task) {
        LemonScreens.SELECT -> R.string.select_lemon
        LemonScreens.SQUEEZE -> R.string.squeeze_lemon
        LemonScreens.DRINK -> R.string.drink_lemonade
        else -> R.string.start_again
    }
    val imageResource : Int = when (task) {
        LemonScreens.SELECT -> R.drawable.lemon_tree
        LemonScreens.SQUEEZE -> R.drawable.lemon_squeeze
        LemonScreens.DRINK -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val imageDescription : Int = when (task) {
        LemonScreens.SELECT -> R.string.lemon_tree
        LemonScreens.SQUEEZE -> R.string.lemon
        LemonScreens.DRINK -> R.string.lemonade_glass
        else -> R.string.empty_glass
    }
    val squeezes : Int = (2..4).random()
    var countSqueezes by remember {
        mutableStateOf(0)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = textResource),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (task == LemonScreens.SELECT) {
                    task = LemonScreens.SQUEEZE
                } else if (task == LemonScreens.SQUEEZE) {
                    if (countSqueezes <= squeezes) {
                        task = LemonScreens.SQUEEZE
                        countSqueezes += 1
                    } else {
                        task = LemonScreens.DRINK
                    }
                } else if (task == LemonScreens.DRINK) {
                    task = LemonScreens.RESTART
                } else {
                    task = LemonScreens.SELECT
                }
/*                task = when (task) {
                    LemonScreens.SELECT -> LemonScreens.SQUEEZE
                    LemonScreens.SQUEEZE -> {
                        var
                    }
                    LemonScreens.DRINK -> LemonScreens.RESTART
                    else -> LemonScreens.SELECT
                }*/
            }
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = stringResource(id = imageDescription)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}