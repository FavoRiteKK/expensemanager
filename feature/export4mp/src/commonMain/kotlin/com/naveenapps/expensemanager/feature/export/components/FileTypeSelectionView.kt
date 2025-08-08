package com.naveenapps.expensemanager.feature.export.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppFilterChip
import com.naveenapps.expensemanager.core.model.ExportFileType
import expensemanager.feature.export4mp.generated.resources.Res
import expensemanager.feature.export4mp.generated.resources.csv
import expensemanager.feature.export4mp.generated.resources.pdf
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExportFileTypeSelectionView(
    selectedExportFileType: ExportFileType,
    onExportFileTypeChange: ((ExportFileType) -> Unit),
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AppFilterChip(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            centerAlign = true,
            filterName = stringResource(resource = Res.string.csv),
            isSelected = selectedExportFileType == ExportFileType.CSV,
            onClick = {
                onExportFileTypeChange.invoke(ExportFileType.CSV)
            },
        )
        AppFilterChip(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            centerAlign = true,
            filterName = stringResource(resource = Res.string.pdf),
            isSelected = selectedExportFileType == ExportFileType.PDF,
            onClick = {
                onExportFileTypeChange.invoke(ExportFileType.PDF)
            },
        )
    }
}

//@Preview
//@Composable
//private fun ExportFileTypeSelectionViewPreview() {
//    ExpenseManagerTheme {
//        Column {
//            ExportFileTypeSelectionView(
//                modifier = Modifier
//                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//                    .fillMaxWidth(),
//                selectedExportFileType = ExportFileType.CSV,
//                onExportFileTypeChange = {},
//            )
//            ExportFileTypeSelectionView(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                selectedExportFileType = ExportFileType.PDF,
//                onExportFileTypeChange = {},
//            )
//        }
//    }
//}
