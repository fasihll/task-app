package com.otakkanan.taskapp.data.repository

import com.otakkanan.taskapp.data.model.SurveyModel
import com.otakkanan.taskapp.data.model.TreatmentModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class MainRepository {

    companion object {
        private val surveys = listOf(
            SurveyModel(name = "Membuat Dashboard UI", time = LocalDate.of(2024, 8, 2)),
            SurveyModel(name = "Slicing UI", time = LocalDate.of(2024, 8, 29)),
            SurveyModel(name = "Setup Project", time = LocalDate.of(2024, 8, 21))
        )

        private val treatments = listOf(
            TreatmentModel(name = "Mengerjakan Kuis", time = LocalDate.of(2024, 8, 2)),
            TreatmentModel(name = "Github", LocalDate.of(2024, 8, 10)),
            TreatmentModel(name = "Bitbucket", LocalDate.of(2024, 8, 13)),
            TreatmentModel(name = "Jira", LocalDate.of(2024, 8, 29))
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    fun getSurveys(): Flow<List<SurveyModel>> {
        return flow { emit(surveys) }
    }

    fun getTreatments(): Flow<List<TreatmentModel>> {
        return flow { emit(treatments) }
    }
}