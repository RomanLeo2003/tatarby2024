package domain.model

data class Message(
    val text: String,
    val isSenderMe: Boolean = true,
)
