package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.book
import tatarby.composeapp.generated.resources.games
import tatarby.composeapp.generated.resources.my_words
import tatarby.composeapp.generated.resources.presentation
import tatarby.composeapp.generated.resources.trainings
import ui.features.home.HOME_ROUTE
import ui.features.mywords.MY_WORDS_ROUTE
import ui.features.textbooks.TEXTBOOKS_ROUTE
import ui.theme.Green500
import ui.theme.TatarskiTheme

@Composable
internal fun TatarskiBottomBar(
    destinations: List<BottomBarDestination>,
    selectedDestination: BottomBarDestination?,
    onDestinationClick: (BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.forEach { destination ->
            BottomBarItem(
                destination = destination,
                isSelected = destination == selectedDestination,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .noRippleClickable { onDestinationClick(destination) }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomBarItem(
    destination: BottomBarDestination,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val selectedColor = when {
        isSelected -> Green500
        else -> Color.Transparent
    }

    Surface(
        shape = RoundedCornerShape(20),
        border = BorderStroke(2.dp, selectedColor),
        color = Color.Transparent,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(destination.iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(6.dp)
                .size(30.dp)
        )
    }
}

enum class BottomBarDestination @OptIn(ExperimentalResourceApi::class) constructor(
    val iconRes: DrawableResource,
    val titleRes: StringResource,
    val route: String
) {
    @OptIn(ExperimentalResourceApi::class)
    HOME(iconRes = Res.drawable.presentation, titleRes = Res.string.trainings, route = HOME_ROUTE),

    @OptIn(ExperimentalResourceApi::class)
    MY_WORDS(iconRes = Res.drawable.games, titleRes = Res.string.my_words, route = MY_WORDS_ROUTE),

    @OptIn(ExperimentalResourceApi::class)
    TEXTBOOKS(iconRes = Res.drawable.book, titleRes = Res.string.my_words, route = TEXTBOOKS_ROUTE);

    companion object {
        val routes = BottomBarDestination.entries.map { it.route }
    }
}

@Preview
@Composable
private fun TatarskiBottomBarPrev() {
    TatarskiTheme {
        TatarskiBottomBar(
            destinations = BottomBarDestination.entries,
            selectedDestination = BottomBarDestination.HOME,
            onDestinationClick = {},
        )
    }
}