package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    modifier = Modifier.fillMaxSize(),
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
    var task: LemonScreens by remember {
        mutableStateOf(LemonScreens.SELECT)
    }
    val textResource: Int = when (task) {
        LemonScreens.SELECT -> R.string.select_lemon
        LemonScreens.SQUEEZE -> R.string.squeeze_lemon
        LemonScreens.DRINK -> R.string.drink_lemonade
        else -> R.string.start_again
    }
    val imageResource: Int = when (task) {
        LemonScreens.SELECT -> R.drawable.lemon_tree
        LemonScreens.SQUEEZE -> R.drawable.lemon_squeeze
        LemonScreens.DRINK -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val imageDescription: Int = when (task) {
        LemonScreens.SELECT -> R.string.lemon_tree
        LemonScreens.SQUEEZE -> R.string.lemon
        LemonScreens.DRINK -> R.string.lemonade_glass
        else -> R.string.empty_glass
    }
    var squeezes: Int = (2..4).random()
    var countSqueezes by remember {
        mutableStateOf(0)
    }
    val resetSqueezes = {
        squeezes = (2..4).random()
        countSqueezes = 0
    }
/*    val onImageClicked = {
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
    }*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = textResource),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = imageDescription),
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 2.dp,
                    color = Color(105, 205, 216),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable(onClick = {
                    if (task == LemonScreens.SELECT) {
                        task = LemonScreens.SQUEEZE
                    } else if (task == LemonScreens.SQUEEZE) {
                        if (countSqueezes < squeezes) {
                            task = LemonScreens.SQUEEZE
                            countSqueezes += 1
                        } else {
                            task = LemonScreens.DRINK
                        }
                    } else if (task == LemonScreens.DRINK) {
                        task = LemonScreens.RESTART
                        resetSqueezes()
                    } else {
                        task = LemonScreens.SELECT
                    }
                })
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}