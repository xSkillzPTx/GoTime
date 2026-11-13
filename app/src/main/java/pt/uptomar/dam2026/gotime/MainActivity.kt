package pt.uptomar.dam2026.gotime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.uptomar.dam2026.gotime.ui.navigation.goTimeNavigation
import pt.uptomar.dam2026.gotime.ui.theme.GoTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoTimeTheme {
                goTimeNavigation()
            }
        }
    }
}
