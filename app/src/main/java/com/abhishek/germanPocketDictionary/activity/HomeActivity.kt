package com.abhishek.germanPocketDictionary.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.abhishek.germanPocketDictionary.activity.home.ui.HomeScreen
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GPDTheme {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}