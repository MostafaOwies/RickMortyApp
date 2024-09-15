package com.aqua_waterfliter.rickmorty.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.aqua_waterfliter.rickmorty.core.ui.navigation.NavGraph
import com.aqua_waterfliter.rickmorty.core.ui.theme.RickMortyTheme
import com.aqua_waterfliter.rickmorty.homeScreen.presentation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyTheme {
                NavGraph()
            }
        }
    }
}

