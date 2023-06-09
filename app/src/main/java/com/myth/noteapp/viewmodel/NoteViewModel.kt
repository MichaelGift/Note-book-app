package com.myth.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myth.noteapp.model.Note
import com.myth.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNotes(query: String) = noteRepository.searchNote(query)

    fun backUpDatabase() = viewModelScope.launch {
        noteRepository.backUpDatabase()
    }

    fun restoreDatabase() = viewModelScope.launch {
        noteRepository.restoreDatabase()
    }
}