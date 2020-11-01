package com.example.machinetest.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.machinetest.ui.models.Person

@Dao
interface PersonDao {

    @Insert
    fun insertProduct(products: Person)

    @Query("SELECT * FROM personTable")
    fun getAll() : List<Person>

}