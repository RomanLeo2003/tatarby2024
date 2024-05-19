package domain.service

expect fun buildNavigateToLink(): NavigateToLink

fun interface NavigateToLink {
    fun navigate(url: String)
}