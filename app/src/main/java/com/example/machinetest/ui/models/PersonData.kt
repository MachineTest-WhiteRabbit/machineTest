package com.example.machinetest.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PersonData(val id:String?,
                      val name:String?,
                      val username:String?,
                      val email : String?,
                      val profile_image:String?,
                      val address:AddressModel?,
                      val phone:String?,
                      val website:String?,
                      val company:CompanyModel?){}
