package ru.artysei.wallet.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface ValueDao {
    @Insert(Value::class)
    suspend fun addValue(value: Value)

    @Delete(Value:: class)
    suspend fun deleteValue(value: Value)
}