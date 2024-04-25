package ru.artysei.wallet.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.artysei.wallet.database.entity.Category

@Entity(tableName = "OBJECT", foreignKeys = [
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )
])
data class Object(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0,

    @ColumnInfo(name = "object_name")
    var objectName: String = "",
)