package ui.features.pronunciation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val PRONUNCIATION_ROUTE = "pronunciation_route"

fun NavController.navigateToPronunciation(navOptions: NavOptions? = null) {
    this.navigate(PRONUNCIATION_ROUTE, navOptions)
}

fun NavGraphBuilder.pronunciationScreen(navigateBack: () -> Unit) {
    composable(route = PRONUNCIATION_ROUTE) {
        PronunciationRoute(navigateBack = navigateBack)
    }
}