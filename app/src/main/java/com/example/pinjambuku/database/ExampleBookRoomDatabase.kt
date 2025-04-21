package com.example.pinjambuku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ExampleBorrowedBookEntity::class], version = 1,exportSchema = false)

abstract class ExampleBookRoomDatabase : RoomDatabase() {

    abstract fun exampleBorrowedBookDao(): ExampleBorrowedBookDao

    companion object {
        @Volatile
        private var INSTANCE: ExampleBookRoomDatabase? = null



        fun getDatabase(context: Context): ExampleBookRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExampleBookRoomDatabase::class.java,
                    "book_room_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        }


    }

}