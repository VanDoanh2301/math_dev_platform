package com.dev.devmath.shared.data.datasource

import com.dev.devmath.core.domain.model.ExampleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Example data source.
 * Replace this with your actual data sources (local database, remote API, etc.)
 */
interface ExampleDataSource {
    fun getExampleData(): Flow<ExampleModel>
    suspend fun saveExampleData(model: ExampleModel)
}

/**
 * Example local data source implementation.
 */
class ExampleLocalDataSource : ExampleDataSource {
    override fun getExampleData(): Flow<ExampleModel> {
        return flowOf(
            ExampleModel(
                id = "1",
                name = "Local Example",
                value = 42
            )
        )
    }

    override suspend fun saveExampleData(model: ExampleModel) {
        // Implement local save logic here (e.g., Room database)
    }
}

