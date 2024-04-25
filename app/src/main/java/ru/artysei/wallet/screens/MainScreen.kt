package ru.artysei.wallet.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.database.entity.Category
import ru.artysei.wallet.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onSelectCategory: (Category) ->Unit = {},
    onDeleteCategory: (Category) -> Unit = {}
)
{
    Scaffold(
        modifier = modifier.padding(bottom = 50.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.ADD_CATEGORY.route)
                },
                content = { Icon(Icons.Filled.Add, contentDescription = "Add Category") }
            )
        },
        content = {
            Column(modifier = Modifier) {
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    items(categories){
                        CategoryInfo(navController,it, onSelectCategory,onDeleteCategory)
                    }
                }

            }
        }
    )

}

@Composable
fun CategoryInfo(
    navController: NavController,
    category: Category,
    onSelect: (Category) -> Unit = {},
    onDelete:(Category) -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp)
            .clickable{
                onSelect(category)
                navController.navigate(Screen.OBJECT.route)
            },
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.categoryName,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                onSelect(category)
                navController.navigate(Screen.EDIT_CATEGORY.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
            IconButton(onClick = {onDelete(category)}) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }

        }
    }
}