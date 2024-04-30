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
import ru.artysei.wallet.database.entity.Field
import ru.artysei.wallet.database.entity.Object
import ru.artysei.wallet.navigation.Screen

@Composable
fun EditObjectScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    selectedObject: Object?,
    changeObjectName: String,
    onObjectNameChange: (String) -> Unit = {},
    changeValues: List<String>,
    onChangeValuesChange: (List<String>) -> Unit = {},
    fields: List<Field>,
    onEditObject: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (selectedObject != null) {
            TextField(
                value = changeObjectName,
                onValueChange = onObjectNameChange,
                label = { Text(stringResource(R.string.object_name))},
                modifier = Modifier.fillMaxWidth()
            )

            fields.forEachIndexed { index, field ->
                val value = if (index < changeValues.size) changeValues[index] else ""
                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        val updatedValues = changeValues.toMutableList()
                        if (index < updatedValues.size) {
                            updatedValues[index] = newValue
                        } else {
                            updatedValues.add(newValue)
                        }
                        onChangeValuesChange(updatedValues)
                    },
                    label = { Text(stringResource(R.string.value_for, field.fieldName)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    if (changeObjectName.isNotBlank() && changeValues.all { it.isNotBlank() }) {
                        onEditObject()
                        navController.navigate(Screen.MAIN.route)
                    }

                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.edit_object))
            }
        }
    }
}