package ru.artysei.wallet

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.artysei.wallet.database.entity.Category
import ru.artysei.wallet.database.entity.Field
import ru.artysei.wallet.database.entity.Object
import ru.artysei.wallet.database.entity.Value
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
    var selectedObject by mutableStateOf<Object?>(null)

    var fields by mutableStateOf(listOf<Field>())
    var values by mutableStateOf(listOf<Value>())



    private var objectCollector: Job? = null
    private var fieldCollector: Job? = null
    private var valueCollector: Job? = null



    private val objectsFlow: Flow<List<Object>>
        get() {
            return objectDao.getObjects(selectedCategory?.id ?: -1)
        }
    private val fieldsFlow: Flow<List<Field>>
        get() {
            return fieldDao.getFields(selectedCategory?.id ?: -1)
        }

    private val valuesFlow: Flow<List<Value>>
        get() {
            return valueDao.getValues(selectedObject?.id ?: -1)
        }

    private val allValuesFlow: Flow<List<Value>>
        get() {
            return valueDao.getAllValues()
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

    fun editCategory() {
        viewModelScope.launch {
            try {
                selectedCategory?.let { category ->
                    category.categoryName = newCategory
                    categoryDao.updateCategory(category)
                    val categoryId = category.id
                    fieldDao.deleteFieldsByCategoryId(categoryId)
                    for (fieldName in newFields) {
                        fieldDao.addField(Field(categoryId = categoryId, fieldName = fieldName))
                    }

                    newCategory = ""
                    newFields = emptyList()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error editing category with fields:", e)
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                categoryDao.deleteCategory(category)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting Category:", e)
            }
        }
    }



    fun addObject() {
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

    fun editObject() {
        viewModelScope.launch {
            try {
                selectedObject?.let { obj ->
                    obj.objectName = newObject
                    objectDao.updateObject(obj)
                    for ((index, field) in fields.withIndex()) {
                        val value = newValues.getOrNull(index) ?: ""
                        val existingValue = valueDao.getValueByObjectIdAndFieldId(obj.id, field.id)
                        Log.e("MainViewModel", "fields: $existingValue")
                        if (existingValue != null) {
                            existingValue.value = value
                            valueDao.updateValue(existingValue)
                        }
                        else valueDao.addValue(Value(objectId = obj.id, fieldId = field.id, value = value))
                    }
                    newObject = ""
                    newValues = emptyList()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error editing object with values:", e)
            }
        }
    }

    fun deleteObject(obj: Object) {
        viewModelScope.launch {
            try {
                objectDao.deleteObject(obj)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting object:", e)
            }
        }
    }

    /*fun editObject() {
        viewModelScope.launch {
            try {
                selectedObject?.let { obj ->
                    obj.objectName = newObject
                    objectDao.updateObject(obj)
                    for ((index, field) in fields.withIndex()) {
                        val value = newValues.getOrNull(index) ?: ""
                        if (valueDao.getValueByObjectIdAndFieldId(obj.id, field.id) != null) {
                            valueDao.updateValue(Value(objectId = obj.id, fieldId = field.id, value = value))
                        } else {
                            valueDao.addValue(Value(objectId = obj.id, fieldId = field.id, value = value))
                        }
                    }
                    newObject = ""
                    newValues = emptyList()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error editing object with values:", e)
            }
        }*/

    fun selectObject(thisObject: Object){
        selectedObject = thisObject
        newObject = selectedObject!!.objectName
        viewModelScope.launch {
            try {
                valueCollector?.cancelAndJoin()
            } catch (_: Throwable) {
            }
            valueCollector = launch {
                valuesFlow.collect {
                    values = it
                    newValues = it.map { value -> value.value }
                }
            }

        }

    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        newCategory = selectedCategory!!.categoryName
        viewModelScope.launch {
            try {
                objectCollector?.cancelAndJoin()
            } catch (_: Throwable) {
            }
            try {
                fieldCollector?.cancelAndJoin()
            } catch (_: Throwable) {
            }
            try {
                valueCollector?.cancelAndJoin()
            } catch (_: Throwable) {
            }
            objectCollector = launch {
                objectsFlow.collect { objects = it }
            }
            fieldCollector = launch {
                fieldsFlow.collect { fields = it
                    newFields = it.map { field -> field.fieldName}
                }
            }
            valueCollector = launch {
                allValuesFlow.collect{values = it}
            }
        }
    }
}