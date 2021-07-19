package com.example.noteapp.data

import androidx.lifecycle.LiveData

class Repository(private val noteDao: NoteDao) {
    val getAllNote: LiveData<List<Note>> = noteDao.getAllNote()
    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: Note){
        noteDao.deleteItem(note)
    }
    suspend fun deleteAll(){
        noteDao.deleteAll()
    }
}