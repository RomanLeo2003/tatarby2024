package ui.features.mywords

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val MY_WORDS_ROUTE = "my_words_route"

fun NavController.navigateToMyWords(navOptions: NavOptions? = null) {
    this.navigate(MY_WORDS_ROUTE, navOptions)
}

fun NavGraphBuilder.myWordsScreen() {
    composable(route = MY_WORDS_ROUTE) {
        MyWordsRoute()
    }
}