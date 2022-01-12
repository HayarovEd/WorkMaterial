package com.edurda77.workmaterial.model

import android.content.Context
import androidx.room.Room
import com.edurda77.blocknote2021.domain.NoteDao
import com.edurda77.blocknote2021.domain.NoteRoomDb

private const val DB_PATH="notes.db"
class RoomService (context: Context): NoteDao {
    private val noteDao:NoteDao
            = Room.databaseBuilder(
        context,
        NoteRoomDb::class.java,
        DB_PATH
    ).build().noteDao()
    override fun add(note: ModelNote)  {
        noteDao.add(note)

    }

    override fun getNots(): List<ModelNote> {
        return noteDao.getNots()
    }

    override fun delete(id: Int) {
        noteDao.delete(id)
    }

    override fun update(id: Int, title: String, content: String) {
        noteDao.update(id,title,content)
    }
}