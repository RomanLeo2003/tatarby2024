package com.my.tatarski.ui.features.essay

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ui.features.essay.EssayRoute

const val ESSAY_ROUTE = "essay_route"

fun NavController.navigateToEssay(navOptions: NavOptions? = null) {
    this.navigate(ESSAY_ROUTE, navOptions)
}

fun NavGraphBuilder.essayScreen(navigateBack: () -> Unit) {
    composable(route = ESSAY_ROUTE) {
        EssayRoute(navigateBack = navigateBack)
    }
}