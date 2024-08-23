package com.example.test_task.feature.home.internal.screen.home_screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.product.api.ProductRepository
import com.example.product.api.model.Product
import com.example.test_task.R
import com.example.test_task.feature.home.internal.components.dialog.adding_dislog.AddingProductDialogData
import com.example.test_task.feature.home.internal.components.dialog.redaction_dialog.RedactionDialogData
import com.example.test_task.feature.home.internal.components.dialog.selection_dialog.SelectionDialogData
import com.example.test_task.feature.home.until.MviViewModel
import com.example.test_task.feature.home.internal.mapper.toUiModel
import com.example.test_task.feature.home.until.DialogState
import com.example.test_task.feature.home.until.ResourceProvider
import com.example.test_task.ui.theme.BluePrimary
import com.example.test_task.ui.theme.RedPrimary
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class HomeViewModel(
    private val repository: ProductRepository,
    private val resourceProvider: ResourceProvider,
) : MviViewModel<HomeUiState, HomeAction, HomeEffect>(
    HomeUiState.Loading
) {

    init {
        getListProduct()
        changeState { HomeUiState.Success() }
        viewModelScope.launch {
            repository.products.collect { products ->
                val state = uiState.value
                if (state is HomeUiState.Success) {
                    changeState {
                        state.copy(
                            listProduct = products.map { it.toUiModel() }.toPersistentList()
                        )
                    }
                }
            }
        }

    }

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.UpdateSearchText -> updateSearchText(action.searchText)
            is HomeAction.ShowRedactionDialog -> showRedactionDialog(action.id)
            is HomeAction.DeleteProductsItem -> showSelectedDialog(action.id)
            is HomeAction.UpdateAmount -> updateAmount(action.amount)
            is HomeAction.UpdateName -> updateName(action.name)
            is HomeAction.UpdateTags -> updateTags(action.tags)
            is HomeAction.AcceptRedactionDialog -> {
                action.id?.let {
                    updateItem(
                        id = it,
                        amount = action.amount
                    )
                }
                hideRedactionDialog()
            }

            is HomeAction.AcceptSelectionDialog -> {
                action.id?.let {
                    deleteItem(it)
                }
                hideSelectedDialog()
            }

            HomeAction.RemoveText -> removeTextAndSnowDefaultList()
            HomeAction.HideRedactionDialog -> hideRedactionDialog()
            HomeAction.HideSelectionDialog -> hideSelectedDialog()
            HomeAction.AcceptAddingDialog -> addingProduct()
            HomeAction.HideAddingDialog -> hideAddingDialog()
            HomeAction.ShowAddingDialog -> showAddingDialog()
        }
    }

    private fun updateAmount(amount: String) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    amount = amount
                )
            }
        }
    }

    private fun updateName(name: String) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    name = name
                )
            }
        }
    }

    private fun updateTags(tags: String) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    tags = tags
                )
            }
        }
    }

    private fun addingProduct() {
        viewModelScope.launch {
            val state = uiState.value
            if (state is HomeUiState.Success) {
                repository.insertProduct(
                    product = Product(
                        id = 0,
                        name = state.name.orEmpty(),
                        amount = state.amount?.toIntOrNull() ?: 0,
                        time = getTodayAsString(),
                        tags = state.tags?.convertStringToList().orEmpty()
                    )
                )
            }
        }.invokeOnCompletion {
            hideAddingDialog()
            showNotification(resourceProvider.getString(R.string.product_successfully_added))
        }
    }

    private fun showAddingDialog() {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    addingDialogState = DialogState.Visible(
                        AddingProductDialogData(
                            title = resourceProvider.getString(R.string.adding_product),
                        )
                    )
                )
            }
        }
    }

    private fun hideAddingDialog() {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    addingDialogState = DialogState.Hidden,
                    name = null,
                    amount = null,
                    tags = null
                )
            }
        }
    }

    private fun showSelectedDialog(id: Int) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    selectionDialogState = DialogState.Visible(
                        SelectionDialogData(
                            id = id,
                            primaryText = resourceProvider.getString(R.string.remove_product),
                            secondaryText = resourceProvider.getString(R.string.are_you_sure_to_delete_product),
                        )
                    )
                )
            }
        }
    }

    private fun hideSelectedDialog() {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    selectionDialogState = DialogState.Hidden,
                )
            }
        }
    }

    private fun showRedactionDialog(id: Int?) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    redactionDialogState = DialogState.Visible(
                        RedactionDialogData(
                            id = id,
                            primaryText = resourceProvider.getString(R.string.quantity_product),
                            quantity = state.listProduct.first { it.id == id }.amount?.toInt() ?: 0,
                        )
                    )
                )
            }
        }
    }

    private fun hideRedactionDialog() {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    quantity = 0,
                    redactionDialogState = DialogState.Hidden
                )
            }
        }
    }

    private fun updateSearchText(searchText: String) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    searchText = searchText,
                )
            }
        }
        if (searchText.isNotBlank())
            searchItems(searchText)
        else
            getListProduct()
    }

    private fun removeTextAndSnowDefaultList() {
        getListProduct()
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    searchText = "",
                )
            }
        }
    }


    private fun getListProduct() = viewModelScope.launch {
        repository.getAllProduct()
    }

    private fun searchItems(query: String) = viewModelScope.launch {
        repository.searchProduct(query)
    }

    private fun updateItem(amount: Int, id: Int) = viewModelScope.launch {
        repository.updateProductById(amount = amount, id = id)
    }
        .invokeOnCompletion { showNotification(resourceProvider.getString(R.string.product_successfully_update)) }


    private fun deleteItem(id: Int) = viewModelScope.launch {
        repository.deleteProductById(id)
    }
        .invokeOnCompletion { showNotification(resourceProvider.getString(R.string.product_successfully_delete)) }


    private fun showNotification(message: String?, color: Color = BluePrimary) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    colorContainerSnackBar = color
                )
            }
        }

        sendEffect(HomeEffect.ShowNotification(message = message.toString()))
    }

    private fun showErrorOnSnackBar(message: String?, color: Color = RedPrimary) {
        val state = uiState.value
        if (state is HomeUiState.Success) {
            changeState {
                state.copy(
                    colorContainerSnackBar = color
                )
            }
        }

        sendEffect(HomeEffect.ShowErrorOnSnackBar(message = message.toString()))
    }

    private fun getTodayAsString(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val today = Date()
        return dateFormat.format(today)
    }

    private fun String.convertStringToList(): List<String> {
        return this.split(",")
            .map { it.trim() }
    }

}