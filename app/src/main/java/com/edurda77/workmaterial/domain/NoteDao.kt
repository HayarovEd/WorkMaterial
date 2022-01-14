package com.edurda77.workmaterial.domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.edurda77.workmaterial.model.ModelNote
import com.edurda77.workmaterial.model.NOTE_ID
import com.edurda77.workmaterial.model.NOTE_TABLE
import com.edurda77.workmaterial.model.NOTE_TITLE
import com.edurda77.workmaterial.model.NOTE_CONTENT

@Dao
interface NoteDao {
    @Insert
    fun add(note: ModelNote)

    @Query("DELETE FROM $NOTE_TABLE WHERE $NOTE_ID=:id")
    fun delete (id: Int)

    @Query("UPDATE $NOTE_TABLE SET $NOTE_TITLE=:title,$NOTE_CONTENT=:content  WHERE $NOTE_ID=:id")
    fun update (id: Int, title: String, content: String)

    @Query("SELECT * FROM $NOTE_TABLE")
    fun getNotesLiveData(): LiveData<List<ModelNote>>
}