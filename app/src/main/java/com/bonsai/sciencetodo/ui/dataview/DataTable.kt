package com.bonsai.sciencetodo.ui.dataview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableDefaults.dimensions
import eu.wewox.lazytable.LazyTableDimensions
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions

@Composable
fun DataTable(
    tableContent: DataTableContent,
    onClickCell: (column: Int, row: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val columns = tableContent.columnCount
    val rows = tableContent.rowCount
    LazyTable(
        dimensions = dimensions(),
        modifier = modifier
    ) {
        items(
            count = columns * rows,
            layoutInfo = {
                LazyTableItem(
                    column = it % columns,
                    row = it / columns,
                )
            }
        ) { index ->
            val column = index % columns
            val row = index / columns

            val text = tableContent.getMatrixValue(column, row)
            Box(
                modifier = Modifier.clickable {
                    onClickCell(column, row)
                }
            ) {
                if (row == 0) {
                    HeaderCell(text)
                } else {
                    Cell(text)
                }
            }
        }
    }
}

private fun customDimensions(): LazyTableDimensions =
    lazyTableDimensions(
        columnSize = {
            when (it) {
                0 -> 148.dp
                1 -> 48.dp
                else -> 96.dp
            }
        },
        rowSize = {
            if (it == 0) {
                32.dp
            } else {
                48.dp
            }
        }
    )

@Composable
private fun Cell(
    content: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .border(Dp.Hairline, MaterialTheme.colorScheme.onTertiary)
    ) {
        Text(
            text = content,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Composable
private fun HeaderCell(content: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(Dp.Hairline, MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Text(
            text = content.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataViewGrid() {

}