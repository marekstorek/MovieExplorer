package com.example.movieexplorer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieexplorer.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object{
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }

}