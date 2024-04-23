package ru.artysei.wallet.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(Category::class)
    suspend fun addCategory(category: Category)

    @Delete(Category:: class)
    suspend fun deleteCategory(category: Category)

    @Query("Select * from CATEGORY")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT id FROM CATEGORY ORDER BY id DESC LIMIT 1")
    suspend fun getLastCategoryId(): Int

    @Query("SELECT id FROM CATEGORY WHERE category_name = :name")
    fun getCategoryByName(name: String): Int

}