package com.example.test_task.feature.home.internal.screen.home_screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.test_task.feature.home.internal.components.dialog.adding_dislog.AddingProductDialogData
import com.example.test_task.feature.home.internal.components.dialog.redaction_dialog.RedactionDialogData
import com.example.test_task.feature.home.internal.components.dialog.selection_dialog.SelectionDialogData
import com.example.test_task.feature.home.until.MviAction
import com.example.test_task.feature.home.until.MviEffect
import com.example.test_task.feature.home.until.MviState
import com.example.test_task.feature.home.internal.model.ProductUi
import com.example.test_task.feature.home.until.DialogState
import com.example.test_task.ui.theme.RedPrimary
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal sealed interface HomeUiState : MviState {

    data object Loading : HomeUiState

    data class Error(val message: String) : HomeUiState

    @Immutable
    data class Success(
        val listProduct: PersistentList<ProductUi> = persistentListOf(),
        val searchText: String? = null,
        val quantity: Int = 0,
        val name: String? = null,
        val amount: String? = null,
        val tags: String? = null,
        val colorContainerSnackBar: Color = RedPrimary,
        val redactionDialogState: DialogState<RedactionDialogData> = DialogState.Hidden,
        val selectionDialogState: DialogState<SelectionDialogData> = DialogState.Hidden,
        val addingDialogState: DialogState<AddingProductDialogData> = DialogState.Hidden,
    ) : HomeUiState
}

internal sealed interface HomeAction : MviAction {

    @JvmInline
    value class UpdateSearchText(val searchText: String) : HomeAction

    @JvmInline
    value class DeleteProductsItem(val id: Int) : HomeAction

    @JvmInline
    value class AcceptSelectionDialog(val id: Int?) : HomeAction

    @JvmInline
    value class ShowRedactionDialog(val id: Int?) : HomeAction

    @JvmInline
    value class UpdateAmount(val amount: String) : HomeAction

    @JvmInline
    value class UpdateName(val name: String) : HomeAction

    @JvmInline
    value class UpdateTags(val tags: String) : HomeAction

    class AcceptRedactionDialog(
        val id: Int?,
        val amount: Int,
    ) : HomeAction

    data object RemoveText : HomeAction

    data object HideRedactionDialog : HomeAction

    data object HideSelectionDialog : HomeAction

    data object ShowAddingDialog : HomeAction

    data object AcceptAddingDialog : HomeAction

    data object HideAddingDialog : HomeAction

}

internal sealed interface HomeEffect : MviEffect {

    @JvmInline
    value class ShowErrorOnSnackBar(val message: String) : HomeEffect

    @JvmInline
    value class ShowNotification(val message: String) : HomeEffect
}