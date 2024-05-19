package domain.service

import android.content.Intent
import android.net.Uri


actual fun buildNavigateToLink(): NavigateToLink = Nav()


class Nav: NavigateToLink {
    override fun navigate(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        AppContext.get().startActivity(browserIntent)
    }
}