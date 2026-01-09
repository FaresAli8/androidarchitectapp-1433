package com.generated.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.generated.app.data.FitnessDatabase
import com.generated.app.data.Workout
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FitnessViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = FitnessDatabase.getDatabase(application).workoutDao()

    val workouts: StateFlow<List<Workout>> = dao.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addWorkout(type: String, duration: Int, calories: Int) {
        viewModelScope.launch {
            val workout = Workout(
                type = type,
                durationMinutes = duration,
                caloriesBurned = calories,
                dateTimestamp = System.currentTimeMillis()
            )
            dao.insertWorkout(workout)
        }
    }
}