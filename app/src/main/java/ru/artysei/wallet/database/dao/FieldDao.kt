package ru.artysei.wallet.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.artysei.wallet.database.entity.Field

@Dao
interface FieldDao {
    @Insert(Field::class)
    suspend fun addField(field: Field)

    @Delete(Field:: class)
    suspend fun deleteField(field: Field)

    @Update(Field:: class)
    suspend fun updateField(field: Field)


    @Query("DELETE FROM Field WHERE category_id = :categoryId")
    suspend fun deleteFieldsByCategoryId(categoryId: Int)

    @Query("SELECT * from FIELD where category_id = :categoryId")
    fun getFields(categoryId: Int) : Flow<List<Field>>
}