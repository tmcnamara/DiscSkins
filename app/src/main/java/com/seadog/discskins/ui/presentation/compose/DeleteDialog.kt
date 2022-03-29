package com.seadog.discskins.ui.presentation.compose

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.preview.RoundProvider
import com.seadog.discskins.ui.theme.DiscSkinsTheme
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun DeleteDialog(
    round: Round,
    viewModel: SkinsViewModel?,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit?
) {
    DiscSkinsTheme {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    setShowDialog(false)
                },
                title = {
                    Text("Delete Round", fontWeight = FontWeight.Bold,
                        color =  MaterialTheme.colors.primary)
                },
                confirmButton = {
                    Card(elevation = 24.dp) {
                        Button(
                            content = { Text("Delete") },
                            onClick = {
                                viewModel?.delete(round)
                                setShowDialog(false)
                            }
                        )
                    }
                },
                dismissButton = {
                    Card(elevation = 24.dp) {
                        Button(
                            content = { Text("Cancel") },
                            onClick = {
                                setShowDialog(false)
                            }
                        )
                    }
                },
                text = {
                    Text("Would you like to delete this ?")
                }
            )
        }
    }
}

@Preview(name="Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name="Dark Mode", showBackground = true)
@Composable
fun RoundSummaryCardPreview(
    @PreviewParameter(RoundProvider::class) roundWithScorecards: RoundWithScorecards
) {
    val (showDialog, setShowDialog) =  remember { mutableStateOf(true) }

    DiscSkinsTheme() {
        DeleteDialog(
            round = roundWithScorecards.round,
            viewModel = null,
            showDialog = true,
            setShowDialog = setShowDialog
        )
    }
}