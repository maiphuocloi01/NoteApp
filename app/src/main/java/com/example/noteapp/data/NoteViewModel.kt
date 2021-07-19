package com.example.noteapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).nodeDao()
    private val repository: Repository
    val getAllNote: LiveData<List<Note>>

    init {
        repository = Repository(noteDao)
        getAllNote = repository.getAllNote
    }

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }
    fun updateNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}