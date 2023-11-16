package com.serdararici.besinlerkitabi.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.serdararici.besinlerkitabi.model.Besin

@Dao
interface BesinDAO {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg besin : Besin) : List<Long>

    //Insert -> Room, insert into -  database işlemi
    // suspend -> coroutine scope
    // vararg -> birden fazla ve istediğimiz sayıda besin alabilmek için
    // List<Long> -> long, id'ler

    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>    // tüm besinleri veritabanından getiriyor

    @Query("SELECT * FROM besin WHERE uuid = :besinId")
    suspend fun getBesin(besinId :Int ) : Besin         //id verilen besini getirir

    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()
}