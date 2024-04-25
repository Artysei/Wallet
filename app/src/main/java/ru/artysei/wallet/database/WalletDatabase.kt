package ru.artysei.wallet.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.artysei.wallet.database.dao.CategoryDao
import ru.artysei.wallet.database.dao.FieldDao
import ru.artysei.wallet.database.dao.ObjectDao
import ru.artysei.wallet.database.dao.ValueDao
import ru.artysei.wallet.database.entity.Category
import ru.artysei.wallet.database.entity.Field
import ru.artysei.wallet.database.entity.Object
import ru.artysei.wallet.database.entity.Value

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