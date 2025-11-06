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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.devmath.core.designsystem.KptMaterialTheme
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Tests for [KptFlowColumn] component.
 * These tests verify the component's composability and basic functionality.
 */
class KptFlowColumnTest {

    @Test
    fun `KptFlowColumn compiles successfully`() {
        // This test verifies that the component can be composed
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowColumn {
                    Text("Test Item")
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowColumn with custom parameters compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    maxItemsInEachColumn = 5
                ) {
                    repeat(10) {
                        Text("Item $it")
                    }
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowColumn with empty content compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowColumn {
                    // Empty content
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowColumn with multiple items compiles`() {
        val composable: @Composable () -> Unit = {
            KptMaterialTheme {
                KptFlowColumn(
                    maxItemsInEachColumn = 3
                ) {
                    repeat(15) { index ->
                        Text("Item $index")
                    }
                }
            }
        }
        assertNotNull(composable)
    }

    @Test
    fun `KptFlowColumn with different arrangements compiles`() {
        val arrangements = listOf(
            Arrangement.Top,
            Arrangement.Bottom,
            Arrangement.Center,
            Arrangement.SpaceBetween,
            Arrangement.SpaceEvenly,
            Arrangement.SpaceAround
        )

        arrangements.forEach { arrangement ->
            val composable: @Composable () -> Unit = {
                KptMaterialTheme {
                    KptFlowColumn(
                        verticalArrangement = arrangement
                    ) {
                        Text("Test")
                    }
                }
            }
            assertNotNull(composable)
        }
    }

    @Test
    fun `KptFlowColumn with different horizontal alignments compiles`() {
        val alignments = listOf(
            Alignment.Start,
            Alignment.CenterHorizontally,
            Alignment.End
        )

        alignments.forEach { alignment ->
            val composable: @Composable () -> Unit = {
                KptMaterialTheme {
                    KptFlowColumn(
                        horizontalAlignment = alignment
                    ) {
                        Text("Test")
                    }
                }
            }
            assertNotNull(composable)
        }
    }
}

