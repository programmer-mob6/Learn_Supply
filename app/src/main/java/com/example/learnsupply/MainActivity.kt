package com.example.learnsupply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.learnsupply.navigation.NavigationHost
import com.example.learnsupply.ui.theme.LearnSupplyTheme
import com.tagsamurai.tscomponents.navigation.ModuleScreen
import com.tagsamurai.tscomponents.theme.LocalTheme
import com.tagsamurai.tscomponents.theme.TagSamuraiTheme
import com.tagsamurai.tscomponents.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalTheme provides Theme(
                    isDark = isSystemInDarkTheme(),
                    module = ModuleScreen.SupplyAsset
                )
            ) {
                TagSamuraiTheme {
                    NavigationHost()
                }
            }
        }
    }
}