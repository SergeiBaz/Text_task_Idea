package com.example.test_task.feature.home.internal.screen.home_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.test_task.R
import com.example.test_task.feature.home.internal.components.textField.SearchTextField
import com.example.test_task.feature.home.internal.components.snackbar.SnackBarHost
import com.example.test_task.feature.home.internal.components.card.ProductCard
import com.example.test_task.feature.home.internal.components.dialog.adding_dislog.AddingProductDialog
import com.example.test_task.feature.home.internal.components.dialog.redaction_dialog.RedactionDialog
import com.example.test_task.feature.home.internal.components.dialog.selection_dialog.SelectionDialog
import com.example.test_task.feature.home.internal.components.swipe.SwipeableLazyColumn
import com.example.test_task.feature.home.internal.model.ProductUi
import com.example.test_task.feature.home.until.DialogState
import com.example.test_task.feature.home.until.observeWithLifecycle
import com.example.test_task.ui.theme.BlackThree
import com.example.test_task.ui.theme.BluePrimary
import com.example.test_task.ui.theme.Medium20
import com.example.test_task.ui.theme.SemiBold18
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    viewModel.effect.observeWithLifecycle { effect ->
        when (effect) {

            is HomeEffect.ShowErrorOnSnackBar -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

            is HomeEffect.ShowNotification -> scope.launch {
                snackBarHostState.showSnackbar(effect.message)
            }

        }
    }

    when (val state = uiState) {
        is HomeUiState.Loading -> {}
        is HomeUiState.Error -> {}
        is HomeUiState.Success -> {
            HomeScreen(
                state = state,
                snackbar = {
                    SnackBarHost(
                        hostState = snackBarHostState,
                        containerColor = state.colorContainerSnackBar
                    )
                },
                updateSearchState = remember {
                    { viewModel.onAction(HomeAction.UpdateSearchText(it)) }
                },
                removeText = remember {
                    { viewModel.onAction(HomeAction.RemoveText) }
                },
                deleteProductsItem = remember {
                    { viewModel.onAction(HomeAction.DeleteProductsItem(it)) }
                },
                onHideRedactionDialog = remember {
                    { viewModel.onAction(HomeAction.HideRedactionDialog) }
                },
                onHideSelectionDialog = remember {
                    { viewModel.onAction(HomeAction.HideSelectionDialog) }
                },
                onAcceptRedactionDialog = remember {
                    { id, amount ->
                        viewModel.onAction(
                            HomeAction.AcceptRedactionDialog(
                                id = id, amount = amount
                            )
                        )
                    }
                },
                onAcceptSelectionDialog = remember {
                    { viewModel.onAction(HomeAction.AcceptSelectionDialog(it)) }
                },
                editProductsItem = remember {
                    { viewModel.onAction(HomeAction.ShowRedactionDialog(it)) }
                },
                onAddProductButtonClick = remember {
                    { viewModel.onAction(HomeAction.ShowAddingDialog) }
                },
                onAcceptAddingDialog = remember {
                    { viewModel.onAction(HomeAction.AcceptAddingDialog) }
                },
                onHideAddingDialog = remember {
                    { viewModel.onAction(HomeAction.HideAddingDialog) }
                },
                updateAmount = remember {
                    { viewModel.onAction(HomeAction.UpdateAmount(it)) }
                },
                updateName = remember {
                    { viewModel.onAction(HomeAction.UpdateName(it)) }
                },
                updateTags = remember {
                    { viewModel.onAction(HomeAction.UpdateTags(it)) }
                },
            )
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeUiState.Success,
    modifier: Modifier = Modifier,
    snackbar: @Composable () -> Unit = {},
    removeText: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    updateSearchState: (sarchText: String) -> Unit = {},
    deleteProductsItem: (Int) -> Unit = {},
    editProductsItem: (Int) -> Unit = {},
    onAcceptSelectionDialog: (Int?) -> Unit = {},
    onAcceptRedactionDialog: (id: Int?, amount: Int) -> Unit = { _, _ -> },
    onHideRedactionDialog: () -> Unit = {},
    onHideSelectionDialog: () -> Unit = {},
    onAddProductButtonClick: () -> Unit = {},
    onAcceptAddingDialog: () -> Unit = {},
    onHideAddingDialog: () -> Unit = {},
    updateAmount: (String) -> Unit = {},
    updateTags: (String) -> Unit = {},
    updateName: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    BackHandler {
        onBackButtonClick()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.list_product),
                        style = Medium20,
                        textAlign = TextAlign.Center
                    )
                },
            )
        },
    ) { contentPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            ) {

                SearchTextField(
                    text = state.searchText.orEmpty(),
                    placeholder = stringResource(R.string.search_product),
                    onDone = remember {
                        {
                            focusManager.moveFocus(FocusDirection.Exit)
                            keyboardController?.hide()
                            focusRequester.requestFocus()
                        }
                    },
                    onTextFieldValueChange = { searchText ->
                        updateSearchState(searchText)
                    },
                    onCrossIconButtonClick = removeText,
                    modifier = Modifier
                        .focusRequester(focusRequester),
                )

                Spacer(modifier = Modifier.height(24.dp))

                SwipeableLazyColumn(
                    listItems = state.listProduct,
                    contentPadding = PaddingValues(bottom = 70.dp),
                    swipeToStartThreshold = 0.2f,
                    swipeToEndThreshold = 0.2f,
                    enableSwipeToStart = true,
                    enableSwipeToEnd = true,
                    onSwipeEndToStart = { product ->
                        deleteProductsItem(product.id)
                    },
                    onSwipeStartToEnd = { product ->
                        editProductsItem(product.id)
                    },
                    modifier = Modifier.fillMaxHeight(),
                ) { productUi, _ ->
                    ProductCard(
                        modifier = Modifier.padding(bottom = 12.dp),
                        productUi = productUi,
                    )
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 16.dp)
            ) {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary.copy(alpha = 0.7f)
                    ),
                    border = BorderStroke(1.dp, BlackThree),
                    shape = RoundedCornerShape(8.dp),
                    onClick = onAddProductButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.add_product),
                        style = SemiBold18,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

    when {
        state.redactionDialogState is DialogState.Visible -> {
            RedactionDialog(
                state = state.redactionDialogState,
                onCancel = onHideRedactionDialog,
                onAccept = onAcceptRedactionDialog,
                onDismiss = onHideRedactionDialog
            )
        }

        state.selectionDialogState is DialogState.Visible -> {
            SelectionDialog(
                state = state.selectionDialogState,
                onCancel = onHideSelectionDialog,
                onAccept = onAcceptSelectionDialog,
                onDismiss = onHideSelectionDialog,
            )
        }

        state.addingDialogState is DialogState.Visible -> {
            AddingProductDialog(
                name = state.name.orEmpty(),
                amount = state.amount.orEmpty(),
                tags = state.tags.orEmpty(),
                state = state.addingDialogState,
                onCancel = onHideAddingDialog,
                onAccept = onAcceptAddingDialog,
                onDismiss = onHideAddingDialog,
                onValueChangeAmount = updateAmount,
                onValueChangeTags = updateTags,
                onValueChangeName = updateName,
            )
        }
    }

    snackbar()

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    Surface {
        HomeScreen(
            state = HomeUiState.Success(
                listProduct = persistentListOf(
                    ProductUi(
                        name = "iPhone",
                        time = "02.10.2021",
                        amount = "15",
                        tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
                    ),
                    ProductUi(
                        name = "iPhone",
                        time = "02.10.2021",
                        amount = "15",
                        tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
                    ),
                    ProductUi(
                        name = "iPhone",
                        time = "02.10.2021",
                        amount = "15",
                        tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
                    ),
                    ProductUi(
                        name = "iPhone",
                        time = "02.10.2021",
                        amount = "15",
                        tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
                    ),
                    ProductUi(
                        name = "iPhone",
                        time = "02.10.2021",
                        amount = "15",
                        tags = persistentListOf("Игровая приставка", "iPhone 13", "iPhone 13")
                    ),
                )
            )
        )
    }
}