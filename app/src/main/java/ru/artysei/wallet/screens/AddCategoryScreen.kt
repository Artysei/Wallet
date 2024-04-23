package ru.artysei.wallet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.navigation.Screen

@Composable
fun AddCategoryScreen(
    navController: NavController,
    newCategory: String,
    onNewCategoryChange: (String) -> Unit = {},
    newFields: List<String>,
    onNewFieldsChange: (List<String>) -> Unit = {},
    onAddCategory: () -> Unit = {}

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = newCategory,
            onValueChange = onNewCategoryChange,
            label = { Text("Название категории") },
            modifier = Modifier.fillMaxWidth()
        )

        newFields.forEachIndexed { index, fieldName ->
            TextField(
                value = fieldName,
                onValueChange = { newValue ->
                    val updatedFields = newFields.toMutableList().also { it[index] = newValue }
                    onNewFieldsChange(updatedFields)
                },
                label = { Text("Поле ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                val updatedFields = newFields.toMutableList() + ""
                onNewFieldsChange(updatedFields)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Добавить поле")
        }

        Button(
            onClick = {
                onAddCategory()
                navController.navigate(Screen.MAIN.route)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Добавить категорию")
        }
    }
}