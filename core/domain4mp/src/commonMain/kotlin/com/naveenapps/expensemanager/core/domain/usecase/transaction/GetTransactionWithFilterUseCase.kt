package com.naveenapps.expensemanager.core.domain.usecase.transaction

import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.Transaction
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.repository.AccountRepository
import com.naveenapps.expensemanager.core.repository.CategoryRepository
import com.naveenapps.expensemanager.core.repository.SettingsRepository
import com.naveenapps.expensemanager.core.repository.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

class GetTransactionWithFilterUseCase(
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val settingsRepository: SettingsRepository,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val transactionRepository: TransactionRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(accId: String = ""): Flow<List<Transaction>?> {
        val flows = if ("" != accId) {
            getDateRangeUseCase.invoke()
                .mapLatest { dateRangeModel ->

                    val transactionTypes: List<Int> = TransactionType.entries.map { it.ordinal }

                    val accounts: List<String> = listOf(accId)

                    val categories: List<String> =
                        categoryRepository.getCategories().firstOrNull()?.map { it.id }
                            ?: emptyList()

                    FilterValue(
                        dateRangeModel.type,
                        dateRangeModel.dateRanges,
                        accounts,
                        categories,
                        transactionTypes,
                    )
                }
        } else {
            combine(
                settingsRepository.getTransactionTypes(),
                settingsRepository.getCategories(),
                settingsRepository.getSelectedAccounts(),
                getDateRangeUseCase.invoke(),
            ) { selectedTransactionTypes, selectedCategories, selectedAccounts, dateRangeModel ->

                val transactionTypes: List<Int> = if (selectedTransactionTypes.isNullOrEmpty()) {
                    TransactionType.entries.map { it.ordinal }
                } else {
                    selectedTransactionTypes.map { it.ordinal }
                }

                val accounts: List<String> = if (selectedAccounts.isNullOrEmpty()) {
                    accountRepository.getAccounts().firstOrNull()?.map { it.id } ?: emptyList()
                } else {
                    selectedAccounts
                }

                val categories: List<String> = if (selectedCategories.isNullOrEmpty()) {
                    categoryRepository.getCategories().firstOrNull()?.map { it.id } ?: emptyList()
                } else {
                    selectedCategories
                }

                FilterValue(
                    dateRangeModel.type,
                    dateRangeModel.dateRanges,
                    accounts,
                    categories,
                    transactionTypes,
                )
            }
        }

        return flows.flatMapLatest {
            return@flatMapLatest if (it.dateRangeType == DateRangeType.ALL) {
                transactionRepository.getAllFilteredTransaction(
                    it.accounts,
                    it.categories,
                    it.transactionTypes,
                )
            } else {
                transactionRepository.getFilteredTransaction(
                    it.accounts,
                    it.categories,
                    it.transactionTypes,
                    it.filterRange[0],
                    it.filterRange[1],
                )
            }
        }
    }
}

data class FilterValue(
    val dateRangeType: DateRangeType,
    val filterRange: List<Long>,
    val accounts: List<String>,
    val categories: List<String>,
    val transactionTypes: List<Int>,
)
