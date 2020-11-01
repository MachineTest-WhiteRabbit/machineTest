package com.example.machinetest.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companyTable")
data class Company(@PrimaryKey(autoGenerate = true) var cid: Int = 0,
                   @ColumnInfo(name = "pid") val pid:String?,
                   @ColumnInfo(name = "name") val name:String?,
                   @ColumnInfo(name = "catchPhrase") val catchPhrase:String?,
                   @ColumnInfo(name = "bs") val bs:String?){}