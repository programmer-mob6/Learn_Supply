package com.example.learnsupply

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.example.learnsupply.navigation.NavigationHost
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