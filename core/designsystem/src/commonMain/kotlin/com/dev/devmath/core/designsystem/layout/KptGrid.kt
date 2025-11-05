/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See See https://github.com/openMF/kmp-project-template/blob/main/LICENSE
 */
package com.dev.devmath.core.designsystem.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.theme.KptTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.min

@Composable
fun KptGrid(
    modifier: Modifier = Modifier,
    configuration: GridConfiguration = GridConfiguration(
        spacing = KptTheme.spacing.md,
        horizontalPadding = KptTheme.spacing.md,
    ),
    content: @Composable GridScope.() -> Unit,
) {
    val density = LocalDensity.current
    val columns = configuration.getColumnsForCurrentScreen()

    Layout(
        modifier = modifier
            .padding(horizontal = configuration.horizontalPadding)
            .testTag("KptGrid"),
        content = {
            GridScopeImpl(columns, configuration.spacing).content()
        },
    ) { measurables, constraints ->
        val spacing = with(density) { configuration.spacing.roundToPx() }
        val horizontalPadding = with(density) { configuration.horizontalPadding.roundToPx() }

        val availableWidth = constraints.maxWidth - horizontalPadding * 2
        val columnWidth = (availableWidth - spacing * (columns - 1)) / columns

        val placeables = measurables.fastMap { measurable ->
            val gridItem = measurable.parentData as? GridItemData ?: GridItemData()
            val itemColumns = min(gridItem.span, columns)
            val itemWidth = columnWidth * itemColumns + spacing * (itemColumns - 1)

            measurable.measure(
                constraints.copy(
                    minWidth = itemWidth,
                    maxWidth = itemWidth,
                ),
            )
        }

        val rows = mutableListOf<MutableList<Placeable>>()
        var currentRow = mutableListOf<Placeable>()
        var currentRowColumns = 0

        placeables.fastForEach { placeable ->
            val gridItem = placeable.parentData as? GridItemData ?: GridItemData()
            val itemColumns = min(gridItem.span, columns)

            if (currentRowColumns + itemColumns > columns) {
                if (currentRow.isNotEmpty()) {
                    rows.add(currentRow)
                    currentRow = mutableListOf()
                    currentRowColumns = 0
                }
            }

            currentRow.add(placeable)
            currentRowColumns += itemColumns
        }

        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
        }

        val rowHeights = rows.fastMap { row ->
            row.fastMaxBy { it.height }?.height ?: 0
        }

        val totalHeight = rowHeights.sum() + spacing * (rows.size - 1).coerceAtLeast(0)

        layout(constraints.maxWidth, totalHeight) {
            var yPosition = 0

            rows.forEachIndexed { rowIndex, row ->
                var xPosition = 0

                row.fastForEach { placeable ->
                    placeable.place(xPosition, yPosition)
                    val gridItem = placeable.parentData as? GridItemData ?: GridItemData()
                    val itemColumns = min(gridItem.span, columns)
                    xPosition += columnWidth * itemColumns + spacing * itemColumns
                }

                yPosition += rowHeights[rowIndex] + spacing
            }
        }
    }
}

interface GridScope {
    fun Modifier.gridItem(span: Int = 1): Modifier
}

private class GridScopeImpl(
    private val columns: Int,
    private val spacing: Dp,
) : GridScope {
    override fun Modifier.gridItem(span: Int): Modifier {
        return this.then(
            GridItemModifier(
                span = span.coerceIn(1, columns),
            ),
        )
    }
}

private data class GridItemData(
    val span: Int = 1,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = this@GridItemData
}

private data class GridItemModifier(
    val span: Int,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        return GridItemData(span)
    }
}

@Immutable
data class GridConfiguration(
    val spacing: Dp,
    val horizontalPadding: Dp,
    val columns: Int = 12,
    val breakpoints: BreakpointConfiguration = BreakpointConfiguration(),
) {
    @Composable
    fun getColumnsForCurrentScreen(): Int {
        val window = LocalWindowInfo.current
        val screenWidth = window.containerSize.width.dp

        return when {
            screenWidth >= breakpoints.xl -> breakpoints.xlColumns
            screenWidth >= breakpoints.lg -> breakpoints.lgColumns
            screenWidth >= breakpoints.md -> breakpoints.mdColumns
            screenWidth >= breakpoints.sm -> breakpoints.smColumns
            else -> breakpoints.xsColumns
        }
    }
}

@Immutable
data class BreakpointConfiguration(
    val xs: Dp = 0.dp,
    val sm: Dp = 600.dp,
    val md: Dp = 840.dp,
    val lg: Dp = 1200.dp,
    val xl: Dp = 1600.dp,
    val xsColumns: Int = 4,
    val smColumns: Int = 8,
    val mdColumns: Int = 12,
    val lgColumns: Int = 12,
    val xlColumns: Int = 12,
)

@Preview(name = "KptGrid - Light Theme")
@Composable
fun KptGridLightPreview() {
    KptMaterialTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "KptGrid Example",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            KptGrid(
                modifier = Modifier.fillMaxSize(),
                configuration = GridConfiguration(
                    spacing = KptTheme.spacing.md,
                    horizontalPadding = KptTheme.spacing.md,
                    columns = 12
                )
            ) {
                // Full width item
                Text(
                    text = "Full Width (12 columns)",
                    modifier = Modifier
                        .gridItem(span = 12)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.shapes.small
                        )
                        .padding(16.dp)
                        .height(60.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                // Half width items
                repeat(2) { index ->
                    Text(
                        text = "Half Width ${index + 1} (6 columns)",
                        modifier = Modifier
                            .gridItem(span = 6)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(16.dp)
                            .height(60.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                
                // Third width items
                repeat(3) { index ->
                    Text(
                        text = "Third ${index + 1} (4 cols)",
                        modifier = Modifier
                            .gridItem(span = 4)
                            .background(
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(16.dp)
                            .height(60.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                
                // Quarter width items
                repeat(4) { index ->
                    Text(
                        text = "Q${index + 1} (3)",
                        modifier = Modifier
                            .gridItem(span = 3)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.small
                            )
                            .padding(12.dp)
                            .height(50.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(name = "KptGrid - Dark Theme")
@Composable
fun KptGridDarkPreview() {
    KptMaterialTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "KptGrid Example",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            KptGrid(
                modifier = Modifier.fillMaxSize(),
                configuration = GridConfiguration(
                    spacing = KptTheme.spacing.md,
                    horizontalPadding = KptTheme.spacing.md,
                    columns = 12
                )
            ) {
                // Full width item
                Text(
                    text = "Full Width (12 columns)",
                    modifier = Modifier
                        .gridItem(span = 12)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.shapes.small
                        )
                        .padding(16.dp)
                        .height(60.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                // Half width items
                repeat(2) { index ->
                    Text(
                        text = "Half Width ${index + 1} (6 columns)",
                        modifier = Modifier
                            .gridItem(span = 6)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(16.dp)
                            .height(60.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                
                // Third width items
                repeat(3) { index ->
                    Text(
                        text = "Third ${index + 1} (4 cols)",
                        modifier = Modifier
                            .gridItem(span = 4)
                            .background(
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(16.dp)
                            .height(60.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                
                // Quarter width items
                repeat(4) { index ->
                    Text(
                        text = "Q${index + 1} (3)",
                        modifier = Modifier
                            .gridItem(span = 3)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.small
                            )
                            .padding(12.dp)
                            .height(50.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
