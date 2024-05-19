package ui.features.mywords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.WordsRepository
import domain.service.LocalTtsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyWordsViewModel(
    wordsRepository: WordsRepository,
    private val localTtsService: LocalTtsService
): ViewModel() {

    val words = wordsRepository.words.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun pronounce(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            localTtsService.pronounce(text)
        }
    }
}