package ru.artysei.wallet.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.artysei.wallet.database.entity.Object

@Dao
interface ObjectDao {
    @Insert(Object::class)
    suspend fun addObject(obj: Object)

    @Delete(Object:: class)
    suspend fun deleteObject(obj: Object)

    @Update(Object::class)
    suspend fun updateObject(obj: Object)

    @Query("SELECT * from OBJECT WHERE category_id = :categoryId")
    fun getObjects(categoryId: Int) : Flow<List<Object>>

    @Query("SELECT id FROM OBJECT ORDER BY id DESC LIMIT 1")
    suspend fun getLastObjectId(): Int
}