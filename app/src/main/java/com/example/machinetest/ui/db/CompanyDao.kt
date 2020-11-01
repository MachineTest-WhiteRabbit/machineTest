package com.example.machinetest.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.machinetest.ui.models.Company

@Dao
interface CompanyDao {
    @Insert
    fun insertCompnay(company: Company)

    @Query("SELECT * FROM companyTable")
    fun getAll() : List<Company>

    @Query("SELECT * FROM companyTable WHERE pid = :id")
    fun getSelected(id : String) : List<Company>


}