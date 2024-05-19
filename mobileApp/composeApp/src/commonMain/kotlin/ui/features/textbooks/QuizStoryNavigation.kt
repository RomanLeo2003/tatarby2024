package ui.features.textbooks

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val TEXTBOOKS_ROUTE = "textbooks_route"

fun NavController.navigateToTextbooks(navOptions: NavOptions? = null) {
    this.navigate(TEXTBOOKS_ROUTE, navOptions)
}

fun NavGraphBuilder.textbooksScreen(navigateBack: () -> Unit) {
    composable(route = TEXTBOOKS_ROUTE) {
        TextbooksRoute(navigateBack = navigateBack)
    }
}