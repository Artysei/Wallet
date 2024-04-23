package ru.artysei.wallet.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.artysei.wallet.database.Category
import ru.artysei.wallet.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onSelectCategory: (Category) ->Unit = {},
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
                        CategoryInfo(it){
                            onSelectCategory(it)
                            navController.navigate(Screen.OBJECT.route)
                        }
                    }
                }

            }
        }
    )

}

@Composable
fun CategoryInfo(
    category: Category,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(5.dp),
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .clickable {
                onSelect()
            }) {
            Text(text = category.categoryName)
        }
    }
}