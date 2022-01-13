package com.edurda77.workmaterial.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.edurda77.blocknote2021.domain.NoteDao

private const val DB_PATH = "notes.db"

class RoomService(context: Context) : NoteDao {
    private val noteDao: NoteDao = Room.databaseBuilder(
        context,
        NoteRoomDb::class.java,
        DB_PATH
    ).build().noteDao()

    override fun add(note: ModelNote) {
        noteDao.add(note)
    }

    override fun getNotes(): List<ModelNote> {
        return noteDao.getNotes()
    }

    override fun delete(id: Int) {
        noteDao.delete(id)
    }

    override fun update(id: Int, title: String, content: String) {
        noteDao.update(id, title, content)
    }

    override fun getNotesLiveData(): LiveData<List<ModelNote>> {
        return noteDao.getNotesLiveData()
    }

    fun getNoteDao() {
        //TODO Реализовать и удалить наследование
    }
}