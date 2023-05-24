package com.myth.noteapp.database

import android.content.Context
import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myth.noteapp.model.Note
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionMethod
import java.io.File

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }

    suspend fun backupDatabase(context: Context){
        val dbFile =  context.getDatabasePath("note_db")
        val backupDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"QNotesBackup")
        backupDir.mkdirs()

        val backUpFile = File(backupDir, "QNotes.sqlite")
        dbFile.copyTo(backUpFile, overwrite = true)

        //Compress the backup file into a zip folder
        val zipPath = File(backupDir, "QNotesBackup.zip").absolutePath
        val zipFile = ZipFile(zipPath)

        val parameters = ZipParameters()
        parameters.compressionMethod = CompressionMethod.DEFLATE
        parameters.fileNameInZip = ""
        zipFile.addFile(backUpFile, parameters)
    }

    suspend fun restoreDatabase(context: Context){
        val dbFile = context.getDatabasePath("note_db")
        val backupDir =  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "QNotesBackup")
        val zipPath = File(backupDir,"QNotesBackup.zip").absolutePath

        //Extract the backup file from the zip folder
        val zipFile = ZipFile(zipPath)
        val extractedFile = File(backupDir, "QNotes.sqlite")
        zipFile.extractFile("QNotes.sqlite", backupDir.absolutePath)

        extractedFile.copyTo(dbFile, overwrite = true)
    }

}