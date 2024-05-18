package ui.features.chatbot

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val CHAT_BOT_ROUTE = "chat_bot_route"

fun NavController.navigateToChatBot(navOptions: NavOptions? = null) {
    this.navigate(CHAT_BOT_ROUTE, navOptions)
}

fun NavGraphBuilder.chatBotScreen(navigateBack: () -> Unit) {
    composable(route = CHAT_BOT_ROUTE) {
        ChatBotRoute(navigateBack = navigateBack)
    }
}