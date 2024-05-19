package ui.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToChatBotScreen: () -> Unit,
    navigateToQuizStoryScreen: () -> Unit,
    navigateToEssayScreen: () -> Unit,
    navigateToPronunciation: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            navigateToChatBotScreen = navigateToChatBotScreen,
            navigateToQuizStoryScreen = navigateToQuizStoryScreen,
            navigateToEssayScreen = navigateToEssayScreen,
            navigateToPronunciation = navigateToPronunciation
        )
    }
}