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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.R
import ru.artysei.wallet.database.entity.Category

@Composable
fun EditCategoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedCategory: Category?,
    changeCategory: String,
    onCategoryChange: (String) -> Unit = {},
    changeFields: List<String>,
    onFieldsChange: (List<String>) -> Unit = {},
    onEditCategory: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (selectedCategory != null) {
            TextField(
                value = changeCategory,
                onValueChange = onCategoryChange,
                label = { Text(stringResource(R.string.category_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            changeFields.forEachIndexed { index, fieldName ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = fieldName,
                        onValueChange = { newValue ->
                            val updatedFields =
                                changeFields.toMutableList().also { it[index] = newValue }
                            onFieldsChange(updatedFields)
                        },
                        label = { Text(stringResource(R.string.field, index + 1)) },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            val updatedFields =
                                changeFields.toMutableList().also { it.removeAt(index) }
                            onFieldsChange(updatedFields)
                        }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }

            Button(
                onClick = {
                    val updatedFields = changeFields.toMutableList() + ""
                    onFieldsChange(updatedFields)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.add_field))
            }

            Button(
                onClick = {
                    if (changeCategory.isNotBlank() && changeFields.all { it.isNotBlank() }){
                        onEditCategory()
                        navController.popBackStack()

                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.save_changes))
            }
        }
    }
}