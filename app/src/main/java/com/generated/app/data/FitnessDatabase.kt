package com.generated.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val dateTimestamp: Long
)

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY dateTimestamp DESC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Insert
    suspend fun insertWorkout(workout: Workout)

    @Query("DELETE FROM workouts")
    suspend fun deleteAll()
}

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class FitnessDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var Instance: FitnessDatabase? = null

        fun getDatabase(context: Context): FitnessDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FitnessDatabase::class.java, "fitness_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}