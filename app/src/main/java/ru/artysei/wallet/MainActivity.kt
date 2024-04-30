package ru.artysei.wallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.artysei.wallet.navigation.NavScreen
import ru.artysei.wallet.navigation.Screen
import ru.artysei.wallet.ui.theme.WalletTheme

class MainActivity : ComponentActivity() {
    val mvm: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val position by navController.currentBackStackEntryAsState()
            WalletTheme { Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(when(position?.destination?.route){
                            Screen.ADD_CATEGORY.route -> stringResource(R.string.title_add_category)
                            Screen.EDIT_CATEGORY.route -> stringResource(R.string.title_edit_category)
                            Screen.OBJECT.route -> stringResource(R.string.title_object)
                            Screen.ADD_OBJECT.route -> stringResource(R.string.title_add_object)
                            Screen.EDIT_OBJECT.route -> stringResource(R.string.title_edit_object)
                            else -> stringResource(R.string.title_main)
                        })},
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        navigationIcon = {
                            if(position?.destination?.route != Screen.MAIN.route) IconButton(onClick = {navController.popBackStack()}) {
                                Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = null)
                            }
                        }
                    )
                }
            ) {
                NavScreen(
                    navController = navController,
                    modifier = Modifier.padding(it),
                    categories = mvm.categories,
                    objects = mvm.objects,
                    fields = mvm.fields,
                    values = mvm.values,

                    onSelectCategory = mvm::selectCategory,
                    selectedCategory = mvm.selectedCategory,
                    newCategory =mvm.newCategory,
                    onNewCategoryChange = {mvm.newCategory = it},
                    newFields = mvm.newFields,
                    onNewFieldsChange ={mvm.newFields = it},
                    changeCategory = mvm.changeCategory,
                    onCategoryChange ={mvm.changeCategory = it},
                    changeFields = mvm.changeFields,
                    onFieldsChange = {mvm.changeFields = it},

                    onAddCategory = mvm::addCategory,
                    onEditCategory = mvm::editCategory,
                    onDeleteCategory = mvm::deleteCategory,

                    onSelectObject = mvm::selectObject,
                    selectedObject = mvm.selectedObject,
                    newObject = mvm.newObject,
                    onNewObjectNameChange = {mvm.newObject = it},
                    newValues = mvm.newValues,
                    onNewValuesChange = {mvm.newValues = it},
                    changeObjectName = mvm.changeObject,
                    onObjectNameChange = {mvm.changeObject = it},
                    changeValues = mvm.changeValues,
                    onValuesChange = {mvm.changeValues = it},
                    onAddObject = mvm::addObject,
                    onEditObject = mvm::editObject,
                    onDeleteObject = mvm::deleteObject,
                )
            }
            }
        }
    }
}