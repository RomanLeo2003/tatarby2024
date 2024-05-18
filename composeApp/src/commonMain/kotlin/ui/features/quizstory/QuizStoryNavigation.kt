package ui.features.quizstory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val QUIZ_STORY_ROUTE = "quiz_story_route"

fun NavController.navigateToQuizStory(navOptions: NavOptions? = null) {
    this.navigate(QUIZ_STORY_ROUTE, navOptions)
}

fun NavGraphBuilder.quizStoryScreen(navigateBack: () -> Unit) {
    composable(route = QUIZ_STORY_ROUTE) {
        QuizStoryRoute(navigateBack = navigateBack)
    }
}