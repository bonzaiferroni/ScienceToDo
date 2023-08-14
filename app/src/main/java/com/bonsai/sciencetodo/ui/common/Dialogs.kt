package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bonsai.sciencetodo.R

@Composable
fun StdDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    enableAccept: Boolean,
    modifier: Modifier = Modifier,
    headerText: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (!showDialog) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column {
                Column(
                    verticalArrangement = Arrangement
                        .spacedBy(dimensionResource(R.dimen.gap_medium)),
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    if (headerText != null) {
                        Text(
                            text = headerText.uppercase(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    content()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement
                            .spacedBy(dimensionResource(R.dimen.gap_medium)),
                    ) {
                        RowIconButton(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close dialog",
                            onClick = onDismiss
                        )
                        RowIconButton(
                            imageVector = Icons.Default.Check,
                            contentDescription = "add variable",
                            onClick = onAccept,
                            enabled = enableAccept
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStdDialog() {
    StdDialog(
        showDialog = true,
        onAccept = { },
        onDismiss = { },
        headerText = "Preview Dialog",
        enableAccept = false,
    ) {
        Text(text = "this will be different")
    }
}