package com.example.machinetest.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addressTable")
data class Address(
    @PrimaryKey(autoGenerate = true) var aid: Int = 0,
    @ColumnInfo(name = "pid") val pid: String?,
    @ColumnInfo(name = "street") val street: String?,
    @ColumnInfo(name = "suite") val suite: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "zipcode") val zipcode: String?
) {
}