package com.example.balanceme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.balanceme.ui.theme.BalanceMeTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BalanceMeTheme {
                MaterialTheme {
                    Surface(
                        color = Color(0xFFE3F2FD)
                    ) {

                    }
                }
            }
        }

    }
}