package com.bonsai.sciencetodo.ui.dataview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DataViewGrid() {
    val data = List(50) { it } // Sample data

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Fixed number of columns
        modifier = Modifier.padding(16.dp)
    ) {
        items(data) { item ->
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                val text = if (item % 3 == 0) {
                    "$item"
                } else {
                    "Item $item"
                }
                Text(
                    text = text,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataViewGrid(){
    DataViewGrid()
}