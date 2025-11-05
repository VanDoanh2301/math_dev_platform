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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.dev.devmath.core.designsystem.KptMaterialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
fun KptFlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier.testTag("KptFlowRow"),
        content = content,
    ) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        val childConstraints = Constraints(maxWidth = constraints.maxWidth)

        measurables.fastForEach { measurable ->
            val placeable = measurable.measure(childConstraints)

            if (currentSequence.isNotEmpty() &&
                (
                        currentMainAxisSize + placeable.width > constraints.maxWidth ||
                                currentSequence.size >= maxItemsInEachRow
                        )
            ) {
                sequences += currentSequence.toList()
                crossAxisSizes += currentCrossAxisSize
                mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)
                crossAxisSpace += currentCrossAxisSize

                currentSequence.clear()
                currentMainAxisSize = placeable.width
                currentCrossAxisSize = placeable.height
            } else {
                currentMainAxisSize += placeable.width
                currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
            }

            currentSequence += placeable
        }

        if (currentSequence.isNotEmpty()) {
            sequences += currentSequence
            crossAxisSizes += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)
            crossAxisSpace += currentCrossAxisSize
        }

        // Handle empty case
        if (sequences.isEmpty()) {
            return@Layout layout(0, 0) {}
        }

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)
        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        var crossAxisPosition = 0
        crossAxisSizes.fastForEach { size ->
            crossAxisPositions += crossAxisPosition
            crossAxisPosition += size
        }

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            sequences.forEachIndexed { sequenceIndex, placeables ->
                val childCrossAxisPosition = if (sequenceIndex < crossAxisPositions.size) {
                    crossAxisPositions[sequenceIndex]
                } else {
                    0
                }
                var childMainAxisPosition = 0

                placeables.fastForEach { placeable ->
                    placeable.place(
                        x = childMainAxisPosition,
                        y = childCrossAxisPosition,
                    )
                    childMainAxisPosition += placeable.width
                }
            }
        }
    }
}

@Preview(name = "KptFlowRow - Light Theme")
@Composable
fun KptFlowRowLightPreview() {
    KptMaterialTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "KptFlowRow Example",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            KptFlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
                maxItemsInEachRow = 4
            ) {
                repeat(12) { index ->
                    Text(
                        text = "Item ${index + 1}",
                        modifier = Modifier
                            .width(80.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Preview(name = "KptFlowRow - Dark Theme")
@Composable
fun KptFlowRowDarkPreview() {
    KptMaterialTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "KptFlowRow Example",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            KptFlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
                maxItemsInEachRow = 4
            ) {
                repeat(12) { index ->
                    Text(
                        text = "Item ${index + 1}",
                        modifier = Modifier
                            .width(80.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.shapes.small
                            )
                            .padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}
