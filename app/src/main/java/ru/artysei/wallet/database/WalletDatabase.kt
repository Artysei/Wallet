package ru.artysei.wallet.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    [Category::class, Field::class, Object::class, Value::class],
    version = 1,
    exportSchema = false,
)
abstract class WalletDatabase : RoomDatabase(){
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getFieldDao(): FieldDao
    abstract fun getObjectDao(): ObjectDao
    abstract fun getValueDao(): ValueDao

}