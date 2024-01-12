package eu.steffo.twom.composables.errorhandling

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class LocalizableError {
    @StringRes
    var stringResourceId: Int? = null
        private set

    var throwable: Throwable? = null
        private set

    fun set(stringResourceId: Int) {
        this.stringResourceId = stringResourceId
        this.throwable = null
    }

    fun set(stringResourceId: Int, throwable: Throwable) {
        this.stringResourceId = stringResourceId
        this.throwable = throwable
    }

    fun clear() {
        this.stringResourceId = null
        this.throwable = null
    }

    fun occurred(): Boolean {
        return stringResourceId != null
    }

    @Composable
    fun renderString(): String? {
        val stringResourceId = this.stringResourceId
        val throwable = this.throwable

        return if (stringResourceId == null) {
            null
        } else if (throwable == null) {
            stringResource(stringResourceId)
        } else {
            stringResource(stringResourceId, throwable.toString())
        }
    }

    @Composable
    fun Show(contents: @Composable (rendered: String) -> Unit) {
        val rendered = renderString()

        if (rendered != null) {
            contents(rendered)
        }
    }
}