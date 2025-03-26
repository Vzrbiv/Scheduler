package com.example.balanceme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.*


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    color = Color(0xFFE3F2FD)
                ) {
                    Elem()
                }
            }
        }
    }
}

@Composable
fun Elem() {
    var isTableView by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Основное содержимое", modifier = Modifier.padding(16.dp))

        TableSection(isTableView)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { isTableView = !isTableView },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(if (isTableView) "Переключить на график" else "Переключить на таблицу")
        }

        Spacer(modifier = Modifier.height(8.dp))

        BottomColumns()
    }
}

@Composable
fun TableSection(isTableView: Boolean) {
    if (isTableView) {
        Table()
    } else {
        // TODO: Реализовать график
        Text("График данных (не реализован)", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun Table() {
    val data = remember { generateMockData() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Row(Modifier.fillMaxWidth().background(Color.Gray)) {
            TableHeaderCell("Дата и время")
            TableHeaderCell("Глюкоза")
            TableHeaderCell("Прием пищи")
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(data) { row ->
                TableRow(row)
            }
        }
    }
}

@Composable
fun TableHeaderCell(text: String) {
    Text(
        text,
        modifier = Modifier
            .padding(8.dp)
    )
}

@Composable
fun TableRow(row: Triple<String, String, String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TableCell(row.first)
        TableCell(row.second)
        TableCell(row.third)
    }
}

@Composable
fun TableCell(text: String) {
    Text(
        text,
        modifier = Modifier
            .padding(8.dp)
    )
}

@Composable
fun BottomColumns() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.Blue)
        ) {
            Text("aa", modifier = Modifier.padding(8.dp))
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.Green)
        ) {
            Text("aa", modifier = Modifier.padding(8.dp))
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color.Blue)
        ) {
            Text("aa", modifier = Modifier.padding(8.dp))
        }
    }
}

fun generateMockData(): List<Triple<String, String, String>> {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return List(20) { index ->
        Triple(
            dateFormat.format(Date()),
            "${Random.nextDouble(3.5, 7.5)} ммоль/л",
            if (index % 2 == 0) "Да" else "Нет"
        )
    }
}