package com.dev.devmath.shared.data.mapper

import com.dev.devmath.core.domain.model.ExampleModel

/**
 * Example mapper.
 * Replace this with your actual mappers that convert between data models and domain models.
 */

/**
 * Example data model (typically from API or database)
 */
data class ExampleDataModel(
    val id: String,
    val name: String,
    val value: Int
)

/**
 * Maps data model to domain model
 */
fun ExampleDataModel.toDomainModel(): ExampleModel {
    return ExampleModel(
        id = id,
        name = name,
        value = value
    )
}

/**
 * Maps domain model to data model
 */
fun ExampleModel.toDataModel(): ExampleDataModel {
    return ExampleDataModel(
        id = id,
        name = name,
        value = value
    )
}

