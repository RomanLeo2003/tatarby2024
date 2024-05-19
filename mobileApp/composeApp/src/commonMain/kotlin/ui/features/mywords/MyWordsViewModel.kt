package ui.features.mywords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.WordsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MyWordsViewModel(
    wordsRepository: WordsRepository
): ViewModel() {

    val words = wordsRepository.words.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
}