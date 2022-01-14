package com.edurda77.workmaterial.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edurda77.workmaterial.domain.NoteDao

@Database(
    entities = [ModelNote:: class],
    version = 1
)
abstract class NoteRoomDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}