package com.edurda77.blocknote2021.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edurda77.workmaterial.model.ModelNote

@Database(
    entities = [ModelNote:: class],
    version = 1
)
abstract class NoteRoomDb : RoomDatabase() {
    abstract fun noteDao():NoteDao
}