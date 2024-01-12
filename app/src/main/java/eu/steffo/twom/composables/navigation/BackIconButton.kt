package eu.steffo.twom.composables.navigation

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R

@Composable
fun BackIconButton(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val activity = context as Activity

    fun cancelActivity() {
        activity.setResult(Activity.RESULT_CANCELED)
        activity.finish()
    }

    IconButton(
        modifier = modifier,
        onClick = { cancelActivity() }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = LocalContext.current.getString(R.string.back)
        )
    }
}