package eu.steffo.twom.errorhandling.utils

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.errorhandling.components.ErrorText
import kotlinx.coroutines.CancellationException

private const val TAG = "LocalizableError"

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

@Composable
fun LocalizableError?.DisplayErrorText(modifier: Modifier = Modifier) {
    this.Display {
        ErrorText(
            modifier = modifier,
            text = it,
        )
    }
}

suspend fun MutableState<LocalizableError?>.capture(
    @StringRes error: Int,
    coroutine: suspend () -> Unit,
): Unit? {
    try {
        coroutine()
    } catch (e: CancellationException) {
        Log.v(TAG, "Cancelled coroutine execution", e)
        return null
    } catch (e: Throwable) {
        Log.e(TAG, "Captured error during coroutine execution", e)
        this.value = LocalizableError(error, e)
        return null
    }
    return Unit
}