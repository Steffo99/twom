package eu.steffo.twom.create

import android.net.Uri
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.theme.TwoMPadding

@Composable
@Preview
fun CreateActivityContent(
    modifier: Modifier = Modifier,
    onClickCreate: (name: String, description: String, avatarUri: Uri?) -> Unit = { _, _, _ -> },
) {

    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var avatarUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // val avatarBitmap = if(avatarUri != null) BitmapFactory.decodeFile(avatarUri.toString()).asImageBitmap() else null

    Column(modifier) {
        Row(TwoMPadding.base) {
            val avatarContentDescription = stringResource(R.string.create_avatar_label)
            AvatarSelector(
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .semantics {
                        this.contentDescription = avatarContentDescription
                    },
                onSelectAvatar = SelectAvatar@{
                    val cache = ImageHandler.bitmapToCache("createAvatar", it)
                    avatarUri = Uri.fromFile(cache)
                },
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

        Row(TwoMPadding.base) {
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

        Row(TwoMPadding.base) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClickCreate(name, description, avatarUri)
                },
            ) {
                Text(stringResource(R.string.create_complete_text))
            }
        }
    }
}