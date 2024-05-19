package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.background
import ui.theme.Green200
import ui.theme.TatarskiTheme

@Composable
internal fun TatarskiApp(appState: TatarksiAppState, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar)
                TatarskiBottomBar(
                    destinations = BottomBarDestination.entries,
                    selectedDestination = appState.selectedBottomDestination,
                    onDestinationClick = appState::navigateToTopLevelDestination,
                    modifier = Modifier.background(Green200)
                )
        }
    ) {
        TatarskiAppBackground {
            TatarskiNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(it),
                navController = appState.navController
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TatarskiAppBackground(content: @Composable BoxScope.() -> Unit) {
    Box {
        Image(
            painter = painterResource(Res.drawable.background),
            contentDescription = null,
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        content()
    }
}


@Preview
@Composable
private fun TatarskiAppPrev() {
    TatarskiTheme {
        val appState = rememberTatarksiAppState()
        TatarskiApp(appState)
    }
}



