package ru.artysei.wallet.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {
    @Insert(Field::class)
    suspend fun addField(field: Field)

    @Delete(Field:: class)
    suspend fun deleteField(field: Field)

    @Query("SELECT * from FIELD where category_id = :categoryId")
    fun getFields(categoryId: Int) : Flow<List<Field>>
}