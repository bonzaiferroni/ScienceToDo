package com.bonsai.sciencetodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bonsai.sciencetodo.ui.ScienceToDoApp
import com.bonsai.sciencetodo.ui.theme.ScienceToDoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScienceToDoTheme {
                ScienceToDoApp()
            }
        }
    }
}