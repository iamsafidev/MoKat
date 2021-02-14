package id.husni.mokat.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.husni.mokat.core.data.source.local.entity.MoviesEntity

@Database(entities = [MoviesEntity::class],version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun moviesDao(): MoviesDao

}