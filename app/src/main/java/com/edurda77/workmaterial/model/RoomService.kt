package com.edurda77.workmaterial.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import com.edurda77.blocknote2021.domain.NoteDao

private const val DB_PATH = "notes.db"

class RoomService(context: Context) {
    private val noteDao: NoteDao = Room.databaseBuilder(
        context,
        NoteRoomDb::class.java,
        DB_PATH
    ).build().noteDao()

    fun add(note: ModelNote) {
        noteDao.add(note)
    }

    fun delete(id: Int) {
        noteDao.delete(id)
    }

    fun update(id: Int, title: String, content: String) {
        noteDao.update(id, title, content)
    }

    fun getNotesLiveData(): LiveData<List<ModelNote>> {
        return noteDao.getNotesLiveData()
    }

}