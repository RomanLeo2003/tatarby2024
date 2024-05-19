package domain.repository

import domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    val words: Flow<List<Word>>
}