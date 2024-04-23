package ru.artysei.wallet.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.artysei.wallet.database.Object
import ru.artysei.wallet.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ObjectScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    objects: List<Object>,

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
                        ObjectCard(navController,it)
                    }

                }

            }
        }

    )
}

@Composable
fun ObjectCard(
    navController: NavController,
    objectCard: Object,
    )
{
    ElevatedCard()
    {
        Text(text = objectCard.objectName)
    }
}