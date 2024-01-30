package eu.steffo.twom.composables.errorhandling

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

data class LocalizableError(
    @StringRes val stringResourceId: Int,
    val throwable: Throwable? = null,
)

@Composable
fun LocalizableError?.render(): String? {
    return if (this == null) {
        null
    } else if (this.throwable == null) {
        stringResource(stringResourceId)
    } else {
        stringResource(stringResourceId, throwable.toString())
    }
}

@Composable
fun LocalizableError?.Display(contents: @Composable (rendered: String) -> Unit) {
    val rendered = this.render() ?: return
    contents(rendered)
}
