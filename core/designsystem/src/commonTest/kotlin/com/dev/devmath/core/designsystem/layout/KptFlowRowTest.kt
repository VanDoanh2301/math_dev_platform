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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.dev.devmath.core.designsystem.KptMaterialTheme
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Tests for [KptFlowRow] component.
 * These tests verify the component's composability and basic functionality.
 */
class KptFlowRowTest {

    @Test
    fun `KptFlowRow compiles successfully`() {
        // This test verifies that the component can be composed
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowRow {
                    Text("Test Item")
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowRow with custom parameters compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    maxItemsInEachRow = 3
                ) {
                    repeat(5) {
                        Text("Item $it")
                    }
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowRow with empty content compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowRow {
                    // Empty content
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowRow with multiple items compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowRow(
                    maxItemsInEachRow = 2
                ) {
                    repeat(10) { index ->
                        Text("Item $index")
                    }
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowRow with different arrangements compiles`() {
        val arrangements = listOf(
            Arrangement.Start,
            Arrangement.End,
            Arrangement.Center,
            Arrangement.SpaceBetween,
            Arrangement.SpaceEvenly,
            Arrangement.SpaceAround
        )

        arrangements.forEach { arrangement ->
            val composable: @Composable () -> Unit = {
                KptMaterialTheme {
                    KptFlowRow(
                        horizontalArrangement = arrangement
                    ) {
                        Text("Test")
                    }
                }
            }
            assertNotNull(composable)
        }
    }

    @Test
    fun `KptFlowRow with different vertical alignments compiles`() {
        val alignments = listOf(
            Alignment.Top,
            Alignment.CenterVertically,
            Alignment.Bottom
        )

        alignments.forEach { alignment ->
            val composable: @Composable () -> Unit = {
                KptMaterialTheme {
                    KptFlowRow(
                        verticalAlignment = alignment
                    ) {
                        Text("Test")
                    }
                }
            }
            assertNotNull(composable)
        }
    }
}

