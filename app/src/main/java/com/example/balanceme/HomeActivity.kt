package com.example.balanceme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    color = Color(0xFFE3F2FD)
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var isTableView by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TableSection(isTableView)

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Button(
                onClick = { isTableView = !isTableView },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(if (isTableView) "Переключить на график" else "Переключить на таблицу")
            }

            Spacer(modifier = Modifier.height(32.dp))

            BottomColumns()
        }
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
    val data = remember { Data() }
    val horizontalPadding = 8.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableHeaderCell(
                    "Дата и время",
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(start = horizontalPadding)
                )
                TableHeaderCell(
                    "Глюкоза\nммоль/л",
                    modifier = Modifier.weight(1f)
                )
                TableHeaderCell(
                    "Температура",
                    modifier = Modifier.weight(0.8f)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(data) { row ->
                    TableRow(row, horizontalPadding)
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun TableHeaderCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = 12.dp),
        textAlign = TextAlign.Center,
        fontSize = 14.sp
    )
}

@Composable
fun TableRow(
    row: Triple<String, String, String>,
    horizontalPadding: Dp = 8.dp
) {
    val glucoseValue = row.second.toDoubleOrNull() ?: 0.0
    val glucoseColor = when {
        glucoseValue > 6.5 -> MaterialTheme.colorScheme.error
        glucoseValue < 4.0 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(
            text = row.first,
            modifier = Modifier
                .weight(1.5f)
                .padding(start = horizontalPadding),
            textAlign = TextAlign.Start
        )
        TableCell(
            text = "%.1f".format(glucoseValue),
            color = glucoseColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        TableCell(
            text = row.third,
            color = if (row.third == "Да") MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.weight(0.8f)
        )
    }
}

@Composable
fun TableCell(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier.padding(vertical = 12.dp),
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp
    )
}

fun Data(): List<Triple<String, String, String>> {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return List(20) { index ->
        Triple(
            dateFormat.format(Date(System.currentTimeMillis() - index * 3600000L)),
            "%.2f".format(Random.nextDouble(3.5, 7.5)),
            "33"
        )
    }
}

@Preview
@Composable
fun BottomColumns() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TestStrips()
            IconButton(
                onClick = {
                    val intent = Intent(context, NoteActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_note),
                    contentDescription = "Главная",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = {
                    val intent = Intent(context, SearchActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Поиск",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = {
                    val intent = Intent(context, ProfileActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_account),
                    contentDescription = "Профиль",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun TestStrips() {
    var num by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = num.toString(),
        modifier = Modifier
            .clickable { showDialog = true }
            .padding(32.dp)
    )

    if (showDialog) {
        var inputText by remember { mutableStateOf(num.toString()) }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Изменить число") },
            text = {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(onClick = {
                    num = inputText.toIntOrNull() ?: num
                    showDialog = false
                }) {
                    Text("OK")
                }
            }
        )
    }
}