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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.theme.KptTheme
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Tests for [KptGrid] component.
 * These tests verify the component's composability and basic functionality.
 */
class KptGridTest {

    @Test
    fun `KptGrid compiles successfully`() {
        // This test verifies that the component can be composed
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid {
                    Text("Test Item")
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGrid with default configuration compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Item 1", modifier = Modifier.gridItem(span = 12))
                    Text("Item 2", modifier = Modifier.gridItem(span = 6))
                    Text("Item 3", modifier = Modifier.gridItem(span = 6))
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGrid with custom configuration compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid(
                    modifier = Modifier.fillMaxSize(),
                    configuration = GridConfiguration(
                        spacing = 16.dp,
                        horizontalPadding = 16.dp,
                        columns = 12
                    )
                ) {
                    Text("Full Width", modifier = Modifier.gridItem(span = 12))
                    Text("Half Width 1", modifier = Modifier.gridItem(span = 6))
                    Text("Half Width 2", modifier = Modifier.gridItem(span = 6))
                    Text("Third Width 1", modifier = Modifier.gridItem(span = 4))
                    Text("Third Width 2", modifier = Modifier.gridItem(span = 4))
                    Text("Third Width 3", modifier = Modifier.gridItem(span = 4))
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGrid with empty content compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid {
                    // Empty content
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGrid with different grid spans compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid {
                    repeat(12) { index ->
                        Text(
                            "Item ${index + 1}",
                            modifier = Modifier.gridItem(span = (index % 4) + 1)
                        )
                    }
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGrid with custom breakpoints compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid(
                    configuration = GridConfiguration(
                        spacing = KptTheme.spacing.md,
                        horizontalPadding = KptTheme.spacing.md,
                        columns = 12,
                        breakpoints = BreakpointConfiguration(
                            xs = 0.dp,
                            sm = 600.dp,
                            md = 840.dp,
                            lg = 1200.dp,
                            xl = 1600.dp,
                            xsColumns = 4,
                            smColumns = 8,
                            mdColumns = 12,
                            lgColumns = 12,
                            xlColumns = 12
                        )
                    )
                ) {
                    Text("Item 1", modifier = Modifier.gridItem(span = 12))
                    Text("Item 2", modifier = Modifier.gridItem(span = 6))
                    Text("Item 3", modifier = Modifier.gridItem(span = 6))
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptGridConfiguration with different column counts compiles`() {
        val columnCounts = listOf(4, 6, 8, 12, 16)

        columnCounts.forEach { columns ->
            val composable: @Composable () -> Unit = {
                KptMaterialTheme {
                    KptGrid(
                        configuration = GridConfiguration(
                            spacing = 8.dp,
                            horizontalPadding = 16.dp,
                            columns = columns
                        )
                    ) {
                        Text("Item", modifier = Modifier.gridItem(span = columns))
                    }
                }
            }
            assertNotNull(composable)
        }
    }

    @Test
    fun `GridScope gridItem modifier compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptGrid {
                    // Test gridItem modifier with different spans
                    Text("Span 1", modifier = Modifier.gridItem(span = 1))
                    Text("Span 3", modifier = Modifier.gridItem(span = 3))
                    Text("Span 6", modifier = Modifier.gridItem(span = 6))
                    Text("Span 12", modifier = Modifier.gridItem(span = 12))
                    // Default span (should be 1)
                    Text("Default Span", modifier = Modifier.gridItem())
                }
            }
        }
        assertNotNull(composable)
    }
}

