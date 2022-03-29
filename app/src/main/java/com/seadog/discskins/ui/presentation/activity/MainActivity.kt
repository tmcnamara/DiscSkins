package com.seadog.discskins.ui.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.seadog.discskins.ui.presentation.DiscSkinsApp
import com.seadog.discskins.viewmodels.SkinsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SkinsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscSkinsApp(
                intent,
                viewModel,
                contentResolver
            )
        }
    }
}

