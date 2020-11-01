package com.example.machinetest.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.machinetest.ui.models.Address
import com.example.machinetest.ui.models.Company
import com.example.machinetest.ui.models.Person


@Database(entities = arrayOf(Person::class,Company::class,Address::class), version = 1)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun prDao(): PersonDao
    abstract fun cmDao(): CompanyDao
    abstract fun addDao(): AddressDao
    companion object {
        private var INSTANCE: PersonDatabase? = null
        fun getDatabase(context: Context): PersonDatabase? {
            if (INSTANCE == null) {
                synchronized(PersonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PersonDatabase::class.java, "person.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}