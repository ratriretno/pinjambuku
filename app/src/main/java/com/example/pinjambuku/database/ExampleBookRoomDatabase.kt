package com.example.pinjambuku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ExampleBorrowedBookEntity::class,FavoriteBookEntity::class], version = 2,exportSchema = false)

abstract class ExampleBookRoomDatabase : RoomDatabase() {

    abstract fun exampleBorrowedBookDao(): ExampleBorrowedBookDao
    abstract fun favoriteBookDao(): FavoriteBookDao

    companion object {
        @Volatile
        private var INSTANCE: ExampleBookRoomDatabase? = null
        private var instance: ExampleBookRoomDatabase? = null
        fun getInstance(context: Context): ExampleBookRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    ExampleBookRoomDatabase::class.java, "book_room_database"
                ).build()
            }


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