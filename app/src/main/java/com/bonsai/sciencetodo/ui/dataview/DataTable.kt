package com.bonsai.sciencetodo.ui.dataview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.wewox.lazytable.LazyTable
import eu.wewox.lazytable.LazyTableDefaults.dimensions
import eu.wewox.lazytable.LazyTableDimensions
import eu.wewox.lazytable.LazyTableItem
import eu.wewox.lazytable.lazyTableDimensions

@Composable
fun DataTable(
    dataTableContent: DataTableContent
) {
    val columns = dataTableContent.columnCount
    val rows = dataTableContent.rowCount
    LazyTable(
        dimensions = dimensions()
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

            val text = dataTableContent.getMatrixValue(column, row)
            Text(text = text)
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

@Preview(showBackground = true)
@Composable
fun PreviewDataViewGrid(){
    // DataTable()
}