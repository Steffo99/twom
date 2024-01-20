package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.composables.theme.basePadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteSheet(
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissed: () -> Unit = {},
    onCompleted: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = {
            // Not super sure what this is for
            // https://developer.android.com/jetpack/compose/components/bottom-sheets
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissed()
                }
            }
        },
    ) {
        // Hack required as it seems that ModalBottomSheet does not take in account screen insets yet
        Column(Modifier.padding(bottom = 80.dp)) {

            Text(
                modifier = Modifier
                    .basePadding()
                    .fillMaxWidth(),
                text = stringResource(R.string.room_invite_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            InviteUserForm(
                onDone = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onCompleted()
                        }
                    }
                }
            )

        }
    }
}