package com.ssafy.darly.dao

import androidx.room.*
import com.google.gson.Gson
import com.ssafy.darly.model.RecordRequestDto
import com.ssafy.darly.model.Section

@Dao
interface RecordDao {
    @Query("SELECT * FROM RecordRequestDto")
    fun getAll():List<RecordRequestDto>

    @Insert
    fun insertRecord(record : RecordRequestDto)

    @Query("DELETE FROM RecordRequestDto")
    fun deleteAll()
}

@Database(entities = [RecordRequestDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao() : RecordDao
}

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromSectionList(value: List<Section>?) = Gson().toJson(value)

    @TypeConverter
    fun toSectionList(value: String) = Gson().fromJson(value, Array<Section>::class.java).toList()
}