package eu.steffo.twom.composables.configureroom

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.activities.ConfigureRoomActivity
import eu.steffo.twom.composables.avatar.AvatarPicker
import eu.steffo.twom.composables.theme.basePadding
import eu.steffo.twom.utils.BitmapUtilities

@Composable
@Preview(showBackground = true)
fun ConfigureRoomForm(
    modifier: Modifier = Modifier,
    initialConfiguration: ConfigureRoomActivity.Configuration? = null,
    onSubmit: (ConfigureRoomActivity.Configuration) -> Unit = {},
) {
    var name by rememberSaveable { mutableStateOf(initialConfiguration?.name ?: "") }
    var description by rememberSaveable { mutableStateOf(initialConfiguration?.description ?: "") }
    // TODO: How to load the original avatar from the URL?
    var avatarBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val activity = context as Activity

    Column(modifier) {
        Row(Modifier.basePadding()) {
            val avatarContentDescription = stringResource(R.string.create_avatar_label)
            AvatarPicker(
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .semantics {
                        this.contentDescription = avatarContentDescription
                    },
                value = avatarBitmap,
                onPick = { avatarBitmap = it },
            )
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(stringResource(R.string.create_name_label))
                },
                value = name,
                onValueChange = { name = it }
            )
        }

        Row(Modifier.basePadding()) {
            TextField(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                label = {
                    Text(stringResource(R.string.create_description_label))
                },
                value = description,
                onValueChange = { description = it }
            )
        }

        Row(Modifier.basePadding()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    val result = ConfigureRoomActivity.Configuration(
                        name = name,
                        description = description,
                        avatarUri = if (avatarBitmap != null) {
                            Uri.fromFile(
                                BitmapUtilities.bitmapToCache("createAvatar", avatarBitmap!!)
                            )
                        } else {
                            null
                        },
                    )
                    activity.setResult(ComponentActivity.RESULT_OK, result.toIntent())
                    activity.finish()
                },
            ) {
                Text(stringResource(R.string.create_complete_text))
            }
        }
    }
}