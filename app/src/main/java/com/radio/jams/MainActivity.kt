package com.radio.jams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.radio.jams.ui.MainScreen

class MainActivity : ComponentActivity() {

    // private val api: GithubApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
