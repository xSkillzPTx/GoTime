package pt.uptomar.dam2026.gotime.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pt.uptomar.dam2026.gotime.R
import pt.uptomar.dam2026.gotime.ui.home.homeScreen
import pt.uptomar.dam2026.gotime.ui.profile.profileScreen

@PreviewScreenSizes
@Composable
fun goTimeNavigation(modifier: Modifier = Modifier) {
  val navController = rememberNavController()
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = backStackEntry?.destination?.route

  Scaffold(
    modifier = modifier,
    bottomBar = {
      NavigationBar {
        Destination.entries.forEach { destination ->
          NavigationBarItem(
            selected = currentRoute == destination.route,
            onClick = {
              navController.navigate(destination.route) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
              }
            },
            icon = { Icon(painterResource(destination.icon), destination.contentDescription) },
            label = { Text(destination.label) }
          )
        }
      }
    }
  ) { innerPadding ->
    NavHost(
      navController = navController,
      startDestination = Destination.HOME.route,
      modifier = Modifier.padding(innerPadding)
    ) {
      composable(Destination.HOME.route) { homeScreen() }
      composable(Destination.PROFILE.route) { profileScreen() }
    }
  }
}

enum class Destination(
  val label: String,
  @DrawableRes val icon: Int,
  val route: String,
  val contentDescription: String
) {
  HOME("Home", R.drawable.ic_home, "home", "Home page"),
  PROFILE("Profile", R.drawable.ic_profile, "profile", "Perfil")
}
