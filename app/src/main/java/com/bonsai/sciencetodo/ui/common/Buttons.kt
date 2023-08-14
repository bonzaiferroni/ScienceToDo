package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun RowScope.RowIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun RowScope.RowTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .weight(1f)
    ) { Text(text = text) }
}

@Composable
fun StdIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(backgroundColor, MaterialTheme.shapes.extraLarge)
    ) {
        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = contentDescription,
        )
    }
}