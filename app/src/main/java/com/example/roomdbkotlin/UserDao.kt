package com.example.roomdbkotlin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// Dao is interface in which you provide all queries you will fire on database
@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Insert
    fun insertAll(list: List<User>)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAllUser(): List<User>

    @Query("SELECT * from user Where age>=:age")
    fun getUserWithAge(age: Int): List<User>

}