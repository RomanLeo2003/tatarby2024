package ui.features.textbooks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.tatarski_book
import tatarby.composeapp.generated.resources.textbooks
import ui.TatarskiAppBackground
import ui.theme.TatarskiTheme

@Composable
fun TextbooksRoute(navigateBack: () -> Unit) {

    TextbooksScreen(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp, start = 16.dp, end = 16.dp),
        navigateBack = navigateBack,
        textbooks = textBooks
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextbooksScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    textbooks: List<Textbook> = emptyList()
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.textbooks) + ":",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(20.dp))


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(textbooks) { item ->
                TextBookItem(textbook = item)
            }
        }
    }
}

@Composable
fun TextBookItem(modifier: Modifier = Modifier, textbook: Textbook) {
    Card(modifier = modifier, shape = RoundedCornerShape(15.dp)) {
        Box {
            AsyncImage(
                model = textbook.imageModel,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )


            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0, 0, 0, 153))
                    .align(Alignment.BottomCenter),
            ) {
                Text(
                    text = textbook.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun TextBookItemPreview() {
    val textbook = Textbook(
        imageModel = Res.drawable.tatarski_book,
        title = "Татарский язык для начинающих.",
        link = "https://www.tatshop.ru/kibet/kitaplar/book-0182-learn-tatar-beginners-cd-zakaz"
    )

    TatarskiTheme {
        TextBookItem(textbook = textbook)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun TextbookScreenPreview() {
    val textbooks = Textbook(
        imageModel = Res.drawable.tatarski_book,
        title = "Татарский язык для начинающих.",
        link = "https://www.tatshop.ru/kibet/kitaplar/book-0182-learn-tatar-beginners-cd-zakaz"
    ).let { book ->
        (1..4).map { book }
    }

    TatarskiTheme {
        TatarskiAppBackground {
            TextbooksScreen(textbooks = textbooks)
        }
    }
}