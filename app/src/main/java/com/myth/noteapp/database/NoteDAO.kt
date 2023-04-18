package com.myth.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myth.noteapp.model.Note

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNote(query: String?):LiveData<List<Note>>
}