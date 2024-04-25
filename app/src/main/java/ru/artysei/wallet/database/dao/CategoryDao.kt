package ru.artysei.wallet.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.artysei.wallet.database.entity.Category

@Dao
interface CategoryDao {
    @Insert(Category::class)
    suspend fun addCategory(category: Category)

    @Delete(Category:: class)
    suspend fun deleteCategory(category: Category)
    @Update(Category:: class)
    suspend fun updateCategory(category: Category)

    @Query("Select * from CATEGORY")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT id FROM CATEGORY ORDER BY id DESC LIMIT 1")
    suspend fun getLastCategoryId(): Int

    @Query("SELECT id FROM CATEGORY WHERE category_name = :name")
    fun getCategoryByName(name: String): Int

}