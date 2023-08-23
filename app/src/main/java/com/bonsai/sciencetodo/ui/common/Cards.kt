package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bonsai.sciencetodo.R

@Composable
fun StdRowCard(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            content()
        }
    }
}

@Composable
fun StdColumnCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            content()
        }
    }
}