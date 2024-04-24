package ru.artysei.wallet.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.database.Field
import ru.artysei.wallet.database.Object
import ru.artysei.wallet.database.Value
import ru.artysei.wallet.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ObjectScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    objects: List<Object>,
    fields: List<Field>,
    values: List<Value>,
    onSelectObject: (Object) -> Unit = {},
    onDeleteObject: (Object) -> Unit = {} // Добавляем параметр для обработки нажатия кнопки удаления
) {
    Scaffold(
        modifier = modifier.padding(bottom = 50.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.ADD_OBJECT.route)
                },
                content = { Icon(Icons.Filled.Add, contentDescription = "Add Object") }
            )
        },
        content = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                LazyColumn{
                    items(objects){
                        ObjectCard(navController, it, values, fields, onSelectObject, onDeleteObject) // Передаем функцию onDeleteObject
                    }
                }
            }
        }
    )
}

// В ObjectCard

@Composable
fun ObjectCard(
    navController: NavController,
    obj: Object,
    values: List<Value>,
    fields: List<Field>,
    onSelect: (Object) -> Unit,
    onDelete: (Object) -> Unit // Добавляем параметр для обработки нажатия кнопки удаления
) {
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onSelect(obj)
                navController.navigate(Screen.EDIT_OBJECT.route)
            }

    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = obj.objectName)
            Spacer(modifier = Modifier.height(8.dp))
            for (field in fields) {
                val value = values.find { it.objectId == obj.id && it.fieldId == field.id }?.value ?: ""
                Row {
                    Text(text = "${field.fieldName}: ")
                    Text(text = value)
                }
            }
            // Добавляем кнопку для удаления объекта
            IconButton(onClick = { onDelete(obj) }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }


        }
    }
}
