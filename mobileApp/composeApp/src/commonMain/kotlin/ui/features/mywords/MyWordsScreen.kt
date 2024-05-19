package ui.features.mywords

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import data.repository.FakeWords
import domain.model.Word
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.loading
import tatarby.composeapp.generated.resources.my_words
import tatarby.composeapp.generated.resources.volume
import ui.TatarskiAppBackground
import ui.noRippleClickable
import ui.theme.Green200
import ui.theme.Green800
import ui.theme.TatarskiTheme

@Composable
internal fun MyWordsRoute(
    viewModel: MyWordsViewModel = koinInject()
) {
    val words by viewModel.words.collectAsState()

    MyWordsScreen(modifier = Modifier.fillMaxSize(), words = words, pronounce = viewModel::pronounce)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun MyWordsScreen(
    words: List<Word>,
    pronounce: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier, backgroundColor = Color.Transparent, floatingActionButton = {
        FloatingActionButton(backgroundColor = Green800, onClick = { }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(it.calculateTopPadding()))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(Res.string.my_words) + ":",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(words) { word ->

                    WordCard(word = word, pronounce = pronounce)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
private fun WordCard(word: Word, modifier: Modifier = Modifier, pronounce: (String) -> Unit = {}) {
    Card(
        modifier = modifier,
        backgroundColor = Green800,
        onClick = {},
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 3.dp, start = 3.dp, end = 3.dp, bottom = 7.dp)
                .background(Green200, RoundedCornerShape(18.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = word.iconUrl,
                placeholder = painterResource(Res.drawable.loading),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column {
                Text(text = word.word, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = word.translation, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(Res.drawable.volume),
                contentDescription = null,
                modifier = Modifier.size(30.dp).noRippleClickable { pronounce(word.word) }
            )
        }
    }
}

@Preview
@Composable
private fun WordCardPreview() {
    val word = Word(
        iconUrl = "https://cdn-icons-png.flaticon.com/128/2597/2597216.png",
        word = "can",
        translation = "может"
    )

    TatarskiTheme {
        WordCard(word = word)
    }
}

@Composable
@Preview
private fun MyWordsScreenPreview() {
    TatarskiTheme {
        TatarskiAppBackground {
            MyWordsScreen(modifier = Modifier.fillMaxSize(), words = FakeWords.words)
        }
    }
}




