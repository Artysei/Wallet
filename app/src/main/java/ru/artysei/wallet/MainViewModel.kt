package ru.artysei.wallet

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.artysei.wallet.database.Category
import ru.artysei.wallet.database.Field
import ru.artysei.wallet.database.Object
import ru.artysei.wallet.database.Value
import ru.artysei.wallet.database.WalletDatabase

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val db = Room.databaseBuilder(getApplication(),WalletDatabase::class.java, "WALLET_DB").build()

    private val categoryDao = db.getCategoryDao()
    private val fieldDao = db.getFieldDao()
    private val objectDao = db.getObjectDao()
    private val valueDao = db.getValueDao()

    var newCategory  by mutableStateOf("")
    var newFields by mutableStateOf(listOf<String>())
    var newObject  by mutableStateOf("")
    var newValues  by mutableStateOf(listOf<String>())



    var categories by mutableStateOf(listOf<Category>())
    var selectedCategory by mutableStateOf<Category?>(null)
    var objects by mutableStateOf(listOf<Object>())
    var fields by mutableStateOf(listOf<Field>())


    private var objectCollector: Job? = null
    private var fieldCollector: Job? = null


    private val objectsFlow: Flow<List<Object>>
        get() {
            return objectDao.getObjects(selectedCategory?.id ?: -1)
        }
    private val fieldsFlow: Flow<List<Field>>
        get() {
            return fieldDao.getFields(selectedCategory?.id ?: -1)
        }



    init {
        viewModelScope.launch {
            categoryDao.getAllCategories().collect {
                categories = it
            }
        }
    }

    fun addCategory() {
        viewModelScope.launch {
            try {
                categoryDao.addCategory(Category(categoryName = newCategory))
                val categoryId = categoryDao.getLastCategoryId()
                for (fieldName in newFields) {
                    fieldDao.addField(Field(categoryId = categoryId, fieldName = fieldName))
                }
                newCategory = ""
                newFields = emptyList()
            }
            catch (e: Exception){
                Log.e("MainViewModel", "Error adding category with fields", e)
            }
        }

    }

    fun addObject()
    {
        viewModelScope.launch {
            try {
                val categoryId = selectedCategory!!.id
                objectDao.addObject(Object(categoryId = categoryId, objectName = newObject))
                val objectId = objectDao.getLastObjectId()
                for((index,field) in fields.withIndex()){
                    val value = newValues.getOrNull(index) ?: ""
                    valueDao.addValue(Value(objectId = objectId, fieldId = field.id,value = value))
                }
                newObject = ""
                newValues = emptyList()
            }
            catch (e: Exception){
                Log.e("MainViewModel", "Error adding object with values:", e)
            }
        }

    }

    fun selectCategory(category: Category)
    {
        selectedCategory = category
        viewModelScope.launch {
            try {
                objectCollector?.cancelAndJoin()
            } catch (_:Throwable){ }
            try {
                fieldCollector?.cancelAndJoin()
            } catch (_:Throwable){ }
            objectCollector = launch {
                objectsFlow.collect {objects = it}
            }
            fieldCollector = launch {
                fieldsFlow.collect {fields = it}
            }
        }
    }
}