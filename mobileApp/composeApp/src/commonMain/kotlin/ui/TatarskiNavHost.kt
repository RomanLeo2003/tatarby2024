package ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.my.tatarski.ui.features.essay.essayScreen
import com.my.tatarski.ui.features.essay.navigateToEssay
import ui.features.chatbot.chatBotScreen
import ui.features.chatbot.navigateToChatBot
import ui.features.home.HOME_ROUTE
import ui.features.home.homeScreen
import ui.features.mywords.myWordsScreen
import ui.features.pronunciation.navigateToPronunciation
import ui.features.pronunciation.pronunciationScreen
import ui.features.quizstory.navigateToQuizStory
import ui.features.quizstory.quizStoryScreen
import ui.features.textbooks.textbooksScreen

@Composable
fun TatarskiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_ROUTE,
        enterTransition = { fadeIn(animationSpec = tween(400)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) },
    ) {
        homeScreen(
            navigateToChatBotScreen = navController::navigateToChatBot,
            navigateToQuizStoryScreen = navController::navigateToQuizStory,
            navigateToEssayScreen = navController::navigateToEssay,
            navigateToPronunciation = navController::navigateToPronunciation
        )
        myWordsScreen()
        chatBotScreen(navigateBack = navController::popBackStack)
        quizStoryScreen(navigateBack = navController::popBackStack)
        essayScreen(navigateBack = navController::popBackStack)
        textbooksScreen(navigateBack = navController::popBackStack)
        pronunciationScreen(navigateBack = navController::popBackStack)
    }
}