package ru.artysei.wallet.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.artysei.wallet.MainViewModel
import ru.artysei.wallet.database.Category
import ru.artysei.wallet.screens.AddCategoryScreen
import ru.artysei.wallet.screens.AddObjectScreen
import ru.artysei.wallet.screens.MainScreen
import ru.artysei.wallet.screens.ObjectScreen

@Composable
fun NavScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categories: List<Category>,
    mvm: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    )
    {
        composable(Screen.MAIN.route)
        {
            MainScreen(navController,
                modifier,
                categories,
                mvm::selectCategory
            )
        }
        composable(Screen.ADD_CATEGORY.route)
        {
            AddCategoryScreen(navController,
                mvm.newCategory,
                {mvm.newCategory = it},
                mvm.newFields,
                { mvm.newFields = it},
                mvm::addCategory
            )
        }
        composable(Screen.OBJECT.route)
        {
            ObjectScreen(navController = navController, objects = mvm.objects)
        }
        composable(Screen.ADD_OBJECT.route){
            AddObjectScreen(
                navController,
                mvm.selectedCategory,
                mvm.newObject,
                {mvm.newObject = it},
                mvm.newValues,
                {mvm.newValues = it},
                mvm.fields,
                mvm::addObject
            )

        }
    }
}

@Composable
fun Screen1()
{
    Text(text = "fsdfsdfsdfsdfsdfsd")
}