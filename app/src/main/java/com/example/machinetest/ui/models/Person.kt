package com.example.machinetest.ui.models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "personTable")
data class Person(@PrimaryKey(autoGenerate = true) var pid: Int = 0,
                  @ColumnInfo(name = "id") val id:String?,
                  @ColumnInfo(name = "name") @Nullable val name:String?,
                  @ColumnInfo(name = "username") val username:String?,
                  @ColumnInfo(name = "email") val email:String?,
                  @ColumnInfo(name = "profile_image") val profile_image:String?,
                  @ColumnInfo(name = "phone") val phone:String?,
                  @ColumnInfo(name = "website") val website:String?) {
}
