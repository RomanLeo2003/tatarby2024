package data.repository

import domain.model.Word
import domain.repository.WordsRepository
import kotlinx.coroutines.flow.flow

class FakeWordsRepository : WordsRepository {
    override val words = flow {
        emit(FakeWords.words)
    }
}

object FakeWords {
    val words = listOf(
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3655/3655580.png",
            word = "фән",
            translation = "наука"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/7314/7314483.png",
            word = "сәүдә",
            translation = "торговля"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/2792/2792361.png",
            word = "төрле",
            translation = "различный"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/2579/2579243.png",
            word = "характер",
            translation = "характер"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/1753/1753543.png",
            word = "башлы",
            translation = "главный"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3504/3504837.png",
            word = "чынаяк",
            translation = "чашка"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3785/3785338.png",
            word = "түбәнрәк",
            translation = "ниже"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/1598/1598196.png",
            word = "табигый",
            translation = "естественный"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/2329/2329248.png",
            word = "үзенчәлек",
            translation = "свойство"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3500/3500896.png",
            word = "сыйфат",
            translation = "качество"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/9298/9298510.png",
            word = "җибәрү",
            translation = "отправлять"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3189/3189456.png",
            word = "тавыш",
            translation = "голосование"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3368/3368863.png",
            word = "тәмам",
            translation = "полный"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/4648/4648404.png",
            word = "хуҗа",
            translation = "господин"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/4264/4264676.png",
            word = "май",
            translation = "масло"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/864/864820.png",
            word = "сәхнә",
            translation = "этап"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/4497/4497102.png",
            word = "шартлар",
            translation = "условия"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/2800/2800039.png",
            word = "башлык",
            translation = "заголовок"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3208/3208892.png",
            word = "мәкалә",
            translation = "статья"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/9305/9305711.png",
            word = "һөҗүм",
            translation = "атака"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/5778/5778499.png",
            word = "тамагы",
            translation = "чертов"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/5625/5625773.png",
            word = "бөтен",
            translation = "весь"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/5581/5581166.png",
            word = "бәлки",
            translation = "возможно"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/2765/2765477.png",
            word = "ярлы",
            translation = "бедный"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/3463/3463098.png",
            word = "азат итү",
            translation = "выпускать"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/1041/1041974.png",
            word = "сайлау",
            translation = "выбор"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/853/853710.png",
            word = "дәвам итү",
            translation = "продолжать"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/1535/1535032.png",
            word = "шура",
            translation = "Совет"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/6301/6301418.png",
            word = "капларга",
            translation = "накрыть"
        ),
        Word(
            iconUrl = "https://cdn-icons-png.flaticon.com/128/5122/5122363.png",
            word = "чит",
            translation = "иностранный"
        )
    )
}