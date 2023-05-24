package com.myth.noteapp.repository

import android.content.Context
import com.myth.noteapp.database.NoteDatabase
import com.myth.noteapp.model.Note

class NoteRepository(private val db: NoteDatabase, private val context: Context) {

    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNote(query)
    suspend fun backUpDatabase() = db.backupDatabase(context)
    suspend fun restoreDatabase() = db.restoreDatabase(context)
}