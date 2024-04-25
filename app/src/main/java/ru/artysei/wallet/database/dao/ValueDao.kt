package ru.artysei.wallet.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.artysei.wallet.database.entity.Value

@Dao
interface ValueDao {
    @Insert(Value::class)
    suspend fun addValue(value: Value)

    @Delete(Value:: class)
    suspend fun deleteValue(value: Value)

    @Update(Value:: class)
    suspend fun updateValue(value: Value)
    @Query("SELECT * FROM Value WHERE object_id = :objectId AND field_id = :fieldId LIMIT 1")
    suspend fun getValueByObjectIdAndFieldId(objectId: Int, fieldId: Int): Value?
    @Query("DELETE FROM Value WHERE object_id = :objectId")
    suspend fun deleteValuesByObjectId(objectId: Int)

    @Query("SELECT * from VALUE WHERE object_id = :objectId")
    fun getValues(objectId: Int) : Flow<List<Value>>

    @Query("Select * from VALUE")
    fun getAllValues(): Flow<List<Value>>
}