package com.abhishek.germanPocketDictionary.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.abhishek.germanPocketDictionary.activity.home.ui.HomeScreen
import com.abhishek.germanPocketDictionary.activity.search.SearchResultActivity
import com.abhishek.germanPocketDictionary.core.ui.theme.GPDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GPDTheme {
                Surface {
                    HomeScreen(
                        onSearchClick = ::onSearchClick,
                        onRateThisAppClick = ::onRateThisAppClick,
                    )
                }
            }
        }
    }

    private fun onSearchClick() {
        Intent(this, SearchResultActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun onRateThisAppClick() {
        val playStoreAppUri =
            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreAppUri)
        Intent.createChooser(playStoreIntent, "Please select to rate the app.").also {
            startActivity(it)
        }
    }
}