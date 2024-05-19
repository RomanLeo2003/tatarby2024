package ui.features.textbooks

import androidx.lifecycle.ViewModel
import domain.service.NavigateToLink

class TextBooksViewModel(
    private val navigateToLink: NavigateToLink
): ViewModel() {

    fun navigateToLink(url: String) {
        navigateToLink.navigate(url)
    }

}