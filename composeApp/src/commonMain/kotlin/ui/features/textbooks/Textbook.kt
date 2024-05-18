package ui.features.textbooks

data class Textbook(
    val imageModel: Any,
    val title: String,
    val link: String
)

val textBooks = listOf(
    Textbook(
        imageModel = "https://www.tatshop.ru/images/stories/virtuemart/product/book-0182-learn-tatar-beginners-cd.jpg",
        title = "Татарский язык для начинающих.",
        link = ""
    ),

    Textbook(
        imageModel = "https://s1.livelib.ru/boocover/1003626329/200x305/f88e/Igor_Lvovich_Litvinov__Ya_nachinayu_govorit_potatarski.jpg",
        title = "Я начинаю говорить по-татарски (Литвинов И.Л)",
        link = ""
    ),
    Textbook(
        imageModel = "https://www.tatshop.ru/images/stories/virtuemart/product/book-0192-tatar-russian-dictionary7.jpg",
        title = "Татарско-русский словарь новых слов",
        link = ""
    ),
    Textbook(
        imageModel = "https://cc02.twirpx.net/1513/1513485.jpg?t=20141016225844",
        title = "Шаяхметова Л.Х. Татарский язык для начинающих. Часть 2",
        link = ""
    ),
    Textbook(
        imageModel = "https://www.tatshop.ru/images/stories/virtuemart/product/resized/book-0179-learning-tatar-lessons_500x500.jpg",
        title = "Самоучители татарского языка, словари и разговорники",
        link = ""
    ),
    Textbook(
        imageModel = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWseO8vk94Ck_lJ8p9Bz6m1JDFIVs8SnfJMQ-sRU8Ibr0vPW6X1FjYo7rutdb4F5euh1E&usqp=CAU",
        title = "Давайте говорить по-татарски, К. С. Фатхуллова",
        link = ""
    ),
)
