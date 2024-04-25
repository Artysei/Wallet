package ru.artysei.wallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.artysei.wallet.database.entity.Category
import ru.artysei.wallet.database.entity.Field
import ru.artysei.wallet.database.entity.Object
import ru.artysei.wallet.database.entity.Value
import ru.artysei.wallet.screens.AddCategoryScreen
import ru.artysei.wallet.screens.AddObjectScreen
import ru.artysei.wallet.screens.EditCategoryScreen
import ru.artysei.wallet.screens.EditObjectScreen
import ru.artysei.wallet.screens.MainScreen
import ru.artysei.wallet.screens.ObjectScreen

@Composable
fun NavScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categories: List<Category>,
    objects: List<Object>,
    fields: List<Field>,
    values: List<Value>,

    onSelectCategory: (Category) ->Unit = {},
    selectedCategory: Category?,
    newCategory: String,
    onNewCategoryChange: (String) -> Unit = {},
    newFields: List<String>,
    onNewFieldsChange: (List<String>) -> Unit = {},
    onAddCategory: () -> Unit = {},
    onEditCategory: () -> Unit = {},
    onDeleteCategory: (Category) -> Unit = {},

    onSelectObject: (Object) -> Unit = {},
    selectedObject: Object?,
    newObject: String,
    onNewObjectNameChange: (String) -> Unit = {},
    newValues: List<String>,
    onNewValuesChange: (List<String>) -> Unit = {},
    onAddObject: () -> Unit = {},
    onEditObject: () -> Unit = {},
    onDeleteObject: (Object) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    )
    {
        composable(Screen.MAIN.route)
        {
            MainScreen(
                navController,
                modifier,
                categories,
                onSelectCategory,
                onDeleteCategory
            )
        }
        composable(Screen.ADD_CATEGORY.route)
        {
            AddCategoryScreen(
                navController,
                modifier,
                newCategory,
                onNewCategoryChange,
                newFields,
                onNewFieldsChange,
                onAddCategory,
            )
        }
        composable(Screen.EDIT_CATEGORY.route){
            EditCategoryScreen(
               navController,
                modifier,
                selectedCategory,
                newCategory,
                onNewCategoryChange,
                newFields,
                onNewFieldsChange,
                onEditCategory

            )

        }
        composable(Screen.OBJECT.route)
        {
            ObjectScreen(
                navController,
                modifier,
                objects,
                fields,
                values,
                onSelectObject,
                onDeleteObject
            )
        }
        composable(Screen.ADD_OBJECT.route){
            AddObjectScreen(
                navController,
                modifier,
                selectedCategory,
                newObject,
                onNewObjectNameChange,
                newValues,
                onNewValuesChange,
                fields,
                onAddObject
            )

        }

        composable(Screen.EDIT_OBJECT.route){
            EditObjectScreen(
                navController,
                modifier,
                selectedObject,
                newObject,
                onNewObjectNameChange,
                newValues,
                onNewValuesChange,
                fields,
                onEditObject
            )
        }
    }
}