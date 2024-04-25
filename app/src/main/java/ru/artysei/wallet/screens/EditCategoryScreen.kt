package ru.artysei.wallet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.database.entity.Category

@Composable
fun EditCategoryScreen(
    navController: NavController,
    selectedCategory: Category?,
    newCategory: String,
    onNewCategoryChange: (String) -> Unit = {},
    newFields: List<String>,
    onNewFieldsChange: (List<String>) -> Unit = {},
    onEditCategory: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (selectedCategory != null) {
            TextField(
                value = newCategory,
                onValueChange = onNewCategoryChange,
                label = { Text("Название категории") },
                modifier = Modifier.fillMaxWidth()
            )

            newFields.forEachIndexed { index, fieldName ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = fieldName,
                        onValueChange = { newValue ->
                            val updatedFields =
                                newFields.toMutableList().also { it[index] = newValue }
                            onNewFieldsChange(updatedFields)
                        },
                        label = { Text("Поле ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            val updatedFields =
                                newFields.toMutableList().also { it.removeAt(index) }
                            onNewFieldsChange(updatedFields)
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Удалить поле")
                    }
                }
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
                    onEditCategory()
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Сохранить изменения")
            }
        }
    }
}