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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.R
import ru.artysei.wallet.database.entity.Category
import ru.artysei.wallet.database.entity.Field

@Composable
fun AddObjectScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedCategory: Category?,
    newObjectName: String,
    onNewObjectNameChange: (String) -> Unit = {},
    newValues: List<String>,
    onNewValuesChange: (List<String>) -> Unit = {},
    fields: List<Field>,
    onAddObject: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (selectedCategory != null) {
            TextField(
                value = newObjectName,
                onValueChange = onNewObjectNameChange,
                label = { Text(stringResource(R.string.object_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            fields.forEachIndexed { index, field ->
                val value = if (index < newValues.size) newValues[index] else ""
                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        val updatedValues = newValues.toMutableList()
                        if (index < updatedValues.size) {
                            updatedValues[index] = newValue
                        } else {
                            updatedValues.add(newValue)
                        }
                        onNewValuesChange(updatedValues)
                    },
                    label = { Text(stringResource(R.string.value_for, field.fieldName)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    if (newObjectName.isNotBlank() && newValues.any { it.isNotBlank() }) {
                        onAddObject()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.add_object))
            }
        }
    }
}