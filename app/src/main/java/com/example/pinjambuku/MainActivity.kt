package com.example.pinjambuku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pinjambuku.ui.Banner
import com.example.pinjambuku.ui.LoginScreen
import com.example.pinjambuku.ui.Profile
import com.example.pinjambuku.ui.SignUp
import com.example.pinjambuku.ui.theme.PinjamBukuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PinjamBukuTheme {

                //LoginScreen()
                SignUp()
                //Profile()

            }
        }
    }
}



/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PinjamBukuTheme {
        Greeting("Android")
    }
}

 */