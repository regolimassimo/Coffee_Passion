package com.massimoregoli.coffeepassion.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.massimoregoli.coffeepassion.R
import com.massimoregoli.coffeepassion.model.Descriptor
import com.massimoregoli.coffeepassion.model.Order
import com.massimoregoli.coffeepassion.ui.theme.*

const val LARGE_COL: Float = 1f

@Composable
fun MainScreen(order: MutableState<Order>, onOk: () -> Unit) {
    val configuration = LocalConfiguration.current

    val grid = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            false
        }
        else -> {
            true
        }
    }

    Card(elevation = smallDP,
        modifier = Modifier.padding(smallDP)) {
        Column(
            modifier = Modifier
                .background(CardLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Coffee Shop",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = largeSP,
                color = Purple200
            )
            Divider(
                modifier = Modifier
                    .padding(start = 80.dp, end = 80.dp),
                thickness = tinyDP,
                color = Purple500
            )
            if (grid) {
                Grid(order)
            } else {
                Linear(order)
            }
            Button(
                colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = Purple200,
                        contentColor = Color.Black
                    ),
                onClick = onOk
            ) {
                Text(text = "OK")
            }
        }
    }
}

@Composable
fun Linear(order: MutableState<Order>) {

}

@Composable
fun Grid(order: MutableState<Order>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(smallDP)
                .weight(LARGE_COL)
                .border(tinyDP, Color.LightGray, Shapes.medium)
                .padding(tinyDP)
        ) {
            TypeBox(order)
        }
        Box(
            modifier = Modifier
                .padding(smallDP)
                .weight(LARGE_COL)
                .border(tinyDP, Color.LightGray, Shapes.medium)
                .padding(tinyDP)
        ) {
            TempBox(order)
        }
        Box(
            modifier = Modifier
                .padding(smallDP)
                .weight(LARGE_COL)
                .border(tinyDP, Color.LightGray, Shapes.medium)
                .padding(tinyDP)
        ) {
            SizeBox(order)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(smallDP)
                .weight(LARGE_COL)
                .border(tinyDP, Color.LightGray, Shapes.medium)
                .padding(tinyDP)
        ) {
            AddOnBox(order)
        }
        Box(
            modifier = Modifier
                .padding(smallDP)
                .weight(LARGE_COL)
                .border(tinyDP, Color.LightGray, Shapes.medium)
                .padding(tinyDP)
        ) {
            SugarBox(order)
        }
    }
}

@Composable
fun TypeBox(order: MutableState<Order>) {
    val desc = Descriptor(LocalContext.current)
    val type = rememberSaveable { mutableStateOf(order.value.type) }
    Column(modifier = Modifier.fillMaxWidth()) {
        ColumnTitle(
            "Type", R.drawable.coffee_outline
        )
        for (state in 1 until desc.TYPE.size) {
            MyCheck(
                checker = type,
                state = state,
                text = desc.TYPE[state]
            ) {
                order.value.type = it
            }
        }
    }
}

@Composable
fun TempBox(order: MutableState<Order>) {
    val desc = Descriptor(LocalContext.current)
    val temp = rememberSaveable { mutableStateOf(order.value.temp) }
    Column(modifier = Modifier.fillMaxWidth()) {
        ColumnTitle(
            "Temp", R.drawable.snowflake
        )
        for (state in 1 until desc.TEMP.size) {
            MyCheck(
                checker = temp,
                state = state,
                text = desc.TEMP[state]
            ) {
                order.value.temp = it
            }
        }
    }
}

@Composable
fun SizeBox(order: MutableState<Order>) {
    val desc = Descriptor(LocalContext.current)
    val size = rememberSaveable { mutableStateOf(order.value.size) }
    Column(modifier = Modifier.fillMaxWidth()) {
        ColumnTitle(
            "Size", R.drawable.coffee_to_go_outline
        )
        for (state in 1 until desc.SIZE.size) {
            MyCheck(
                checker = size,
                state = state,
                text = desc.SIZE[state]
            ) {
                order.value.size = it
            }
        }
    }
}

@Composable
fun AddOnBox(order: MutableState<Order>) {
    val desc = Descriptor(LocalContext.current)

    val addon = rememberSaveable {
        mutableStateOf(order.value.addon)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ColumnTitle(
            "Add On", R.drawable.puzzle_plus_outline
        )
        for (state in 1 until desc.ADDON.size) {
            MyCheck(addon, state, desc.ADDON[state]) {
                order.value.addon = it
            }
        }
        if (addon.value == 3) {
            CorrectionBox(order)
        } else {
            order.value.correction = 0
        }
    }

}

@Composable
fun SugarBox(order: MutableState<Order>) {

}

@Composable
fun CorrectionBox(order: MutableState<Order>) {
    val desc = Descriptor(LocalContext.current)

    val correction = rememberSaveable {
        mutableStateOf(order.value.correction)
    }
    Column(modifier = Modifier.padding(start = mediumDP)) {
        for ((state, label) in desc.CORRECTION.withIndex()) {
            if (state > 0)
                MyCheck(correction, state, label) {
                    order.value.correction = it
                }
        }
    }
}

@Composable
fun ColumnTitle(title: String, drawable: Int) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = drawable),
                contentDescription = "ICON",
                tint = Purple200,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
            Text(
                text = title,
                fontSize = largeSP,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = smallDP),
                color = Purple200
            )
        }
        Divider(
            color = Purple500,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp)
        )
    }
}

@Composable
fun MyCheck(
    checker: MutableState<Int>,
    state: Int,
    text: String,
    onChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(smallDP)
            .clickable {
                if (checker.value != state)
                    checker.value = state
                else
                    checker.value = 0
                onChange(checker.value)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            colors = SwitchDefaults.colors(
                checkedThumbColor = Purple200,
                uncheckedThumbColor = Purple500
            ),
            modifier = Modifier
                .padding(start = tinyDP, end = smallDP),
            checked = checker.value == state,
            onCheckedChange = null,
        )
        Text(
            text = text,
            maxLines = 1,
            fontSize = mediumSP,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(smallDP),
            color = Purple200
        )
    }
}