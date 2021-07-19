package com.example.noteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun nodeDao(): NoteDao
    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java, "note_database"
            ).build()
    }
}