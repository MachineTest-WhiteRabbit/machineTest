package com.example.machinetest.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.machinetest.ui.models.Address

@Dao
interface AddressDao {
    @Insert
    fun insertAddress(address: Address)

    @Query("SELECT * FROM addressTable")
    fun getAll() : List<Address>

    @Query("SELECT * FROM addressTable WHERE pid = :id")
    fun getSelected(id:String) : List<Address>
}

