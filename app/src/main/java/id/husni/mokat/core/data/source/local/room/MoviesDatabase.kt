package id.husni.mokat.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.husni.mokat.core.data.source.local.entity.MoviesEntity

@Database(entities = [MoviesEntity::class],version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun moviesDao(): MoviesDao

    companion object{
        @Volatile
        private var instance: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase =
            instance ?: synchronized(this){
                instance ?: Room
                    .databaseBuilder(context.applicationContext,MoviesDatabase::class.java,"movies_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}