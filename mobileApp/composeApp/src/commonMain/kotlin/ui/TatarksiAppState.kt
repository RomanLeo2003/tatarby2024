package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ui.features.home.navigateToHome
import ui.features.mywords.navigateToMyWords
import ui.features.textbooks.navigateToTextbooks


@Composable
fun rememberTatarksiAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    TatarksiAppState(
        navController = navController
    )
}

@Stable
class TatarksiAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    val selectedBottomDestination
        @Composable get() = BottomBarDestination.entries.find { it.route == currentDestination?.route }

    val shouldShowBottomBar @Composable get() = currentDestination?.route in BottomBarDestination.routes


    fun navigateToTopLevelDestination(topLevelDestination: BottomBarDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(BottomBarDestination.HOME.route) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                BottomBarDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                BottomBarDestination.MY_WORDS -> navController.navigateToMyWords(topLevelNavOptions)
                BottomBarDestination.TEXTBOOKS -> navController.navigateToTextbooks(topLevelNavOptions)
            }
        }
    }

}