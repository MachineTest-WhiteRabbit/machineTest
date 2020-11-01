package com.example.machinetest.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CompanyModel(val name:String = "",
                   val catchPhrase:String = "",
                   val bs:String = ""){}