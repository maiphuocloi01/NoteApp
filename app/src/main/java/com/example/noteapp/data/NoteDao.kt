package com.example.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_data ORDER BY id ASC")
    fun getAllNote(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteItem(note: Note)

    @Query("DELETE FROM note_data")
    suspend fun deleteAll()
}