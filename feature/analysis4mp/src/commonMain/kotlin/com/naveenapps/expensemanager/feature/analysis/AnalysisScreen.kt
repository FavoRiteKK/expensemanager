package com.naveenapps.expensemanager.feature.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviews
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.feature.filter.FilterView
import expensemanager.feature.analysis4mp.generated.resources.Res
import expensemanager.feature.analysis4mp.generated.resources.analysis
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnalysisScreen() {
    AnalysisScreenScaffoldView()
}

@Composable
private fun AnalysisScreenScaffoldView() {
    Scaffold(
        topBar = {
            TopNavigationBar(
                onClick = {},
                title = stringResource(Res.string.analysis),
                disableBackIcon = true,
            )
        },
    ) { innerPadding ->
        AnalysisScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        )
    }
}

@Composable
private fun AnalysisScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        FilterView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 6.dp),
        )
        AnalysisGraphScreen()
    }
}

@AppPreviews
@Composable
fun AnalysisScreenPreview() {
    ExpenseManagerTheme {
        AnalysisScreen()
    }
}
