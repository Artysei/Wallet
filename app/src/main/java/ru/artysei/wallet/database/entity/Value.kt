package ru.artysei.wallet.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.artysei.wallet.database.entity.Field
import ru.artysei.wallet.database.entity.Object

@Entity(tableName = "VALUE", foreignKeys = [
    ForeignKey(
        entity = Object::class,
        parentColumns = ["id"],
        childColumns = ["object_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    ),
    ForeignKey(
        entity = Field::class,
        parentColumns = ["id"],
        childColumns = ["field_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    ),
])
data class Value(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "object_id")
    var objectId: Int = 0,

    @ColumnInfo(name = "field_id")
    var fieldId: Int = 0,

    var value: String = "",
)