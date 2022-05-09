package com.massimoregoli.coffeepassion

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.massimoregoli.coffeepassion.model.Descriptor
import com.massimoregoli.coffeepassion.model.Order
import com.massimoregoli.coffeepassion.screen.MainScreen
import com.massimoregoli.coffeepassion.ui.theme.CoffeePassionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeePassionTheme {
                val order = rememberSaveable {
                    mutableStateOf(Order(0, 0, 0,
                        0, 2, 0))
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(order) {
                        speak(this, order.value)
                    }
                }
            }
        }
    }

    private fun speak(context: Context, o: Order)
    {
        lateinit var tts: TextToSpeech
        val desc = Descriptor(context)

        val coffee = desc.describe(o)
        tts = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS)
                tts.speak(coffee, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        CoroutineScope(Dispatchers.IO).launch {
            tts.speak(coffee, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        Toast.makeText(context, coffee, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoffeePassionTheme {
        Greeting("Android")
    }
}