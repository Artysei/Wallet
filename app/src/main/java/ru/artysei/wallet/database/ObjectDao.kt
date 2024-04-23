package ru.artysei.wallet.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectDao {
    @Insert(Object::class)
    suspend fun addObject(objects: Object)

    @Delete(Object:: class)
    suspend fun deleteObject(objects: Object)

    @Query("SELECT * from OBJECT WHERE category_id = :categoryId")
    fun getObjects(categoryId: Int) : Flow<List<Object>>

    @Query("SELECT id FROM OBJECT ORDER BY id DESC LIMIT 1")
    suspend fun getLastObjectId(): Int
}