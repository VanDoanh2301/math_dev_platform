package com.dev.devmath.shared.data.repository

import com.dev.devmath.core.domain.model.ExampleModel
import com.dev.devmath.core.domain.repository.ExampleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Example repository implementation.
 * Replace this with your actual repository implementations.
 */
class ExampleRepositoryImpl : ExampleRepository {
    override fun getExampleData(): Flow<ExampleModel> {
        return flowOf(
            ExampleModel(
                id = "1",
                name = "Example",
                value = 42
            )
        )
    }

    override suspend fun saveExampleData(model: ExampleModel) {
        // Implement save logic here
    }
}

