package com.naveenapps.expensemanager.core.data.repository

import androidx.room.Room
import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.lwInMemoryDatabaseBuilder
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import com.naveenapps.expensemanager.core.database.dao.CategoryDao
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.CategoryRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_CATEGORY
import com.naveenapps.expensemanager.core.testing.FAKE_FAVORITE_CATEGORY
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var categoryDao: CategoryDao

    private lateinit var database: ExpenseManagerDatabase

    private lateinit var categoryRepository: CategoryRepository

    @BeforeTest
    override fun onCreate() {
        super.onCreate()

        database = Room.lwInMemoryDatabaseBuilder().build()

        categoryDao = database.categoryDao()

        categoryRepository = CategoryRepositoryImpl(
            categoryDao,
            AppCoroutineDispatchers(
                testCoroutineDispatcher,
                testCoroutineDispatcher,
                testCoroutineDispatcher,
            ),
        )
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    @Test
    fun checkDatabaseObject() {
        LWTruth_assertThat(database).isNotNull()
        LWTruth_assertThat(categoryDao).isNotNull()
    }

    @Test
    fun checkInsertSuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_CATEGORY)
    }

    @Test
    fun checkDeleteSuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_CATEGORY)
        deleteCategoryAndAssert(FAKE_CATEGORY)
    }

    @Test
    fun checkFindByIdSuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_CATEGORY)
        findCategoryAndAssert(FAKE_CATEGORY.id)
    }

    @Test
    fun checkGetAllCategoriesSuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_CATEGORY)
        findCategoryAndAssert(FAKE_CATEGORY.id)
    }

    @Test
    fun checkFavoriteCategoriesSuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_FAVORITE_CATEGORY)
        findCategoryAndAssert(FAKE_FAVORITE_CATEGORY.id)
    }

    @Test
    fun checkFindCategoryErrorCase() = runTest {
        val newResult = categoryRepository.findCategory("Unknown id")
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Error::class)
        val foundData = (newResult as Resource.Error).exception
        LWTruth_assertThat(foundData).isNotNull()
    }

    @Test
    fun updateCategorySuccessCase() = runTest {
        insertCategoryAndAssert(FAKE_FAVORITE_CATEGORY)
        val category = FAKE_FAVORITE_CATEGORY.copy(name = "Sample Notes")
        updateCategoryAndAssert(category)
        findCategoryAndAssert(category.id)
    }

    @Test
    fun getAllCategoryCase() = runTest {
        insertCategoryAndAssert(FAKE_FAVORITE_CATEGORY)
        val result = categoryRepository.getAllCategory()
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((result as Resource.Success).data).hasSize(1)
    }

    @Test
    fun checkCategoryChangeFlow() = runTest {
        insertCategoryAndAssert(FAKE_FAVORITE_CATEGORY)

        categoryRepository.findCategoryFlow(FAKE_FAVORITE_CATEGORY.id).test {
            val firstItem = awaitItem()
            LWTruth_assertThat(firstItem).isNotNull()
            LWTruth_assertThat(firstItem?.id).isEqualTo(FAKE_FAVORITE_CATEGORY.id)

            val updated = FAKE_FAVORITE_CATEGORY.copy(name = "Updated")
            categoryRepository.updateCategory(updated)

            val updatedItem = awaitItem()
            LWTruth_assertThat(updatedItem).isNotNull()
            LWTruth_assertThat(updatedItem?.id).isEqualTo(updated.id)
            LWTruth_assertThat(updatedItem?.name).isEqualTo(updated.name)
        }
    }

    @Test
    fun checkGetAllCategoryFlowAfterInsertCase() = runTest {
        categoryRepository.getCategories().test {
            val data = awaitItem()
            LWTruth_assertThat(data).isEmpty()

            insertCategoryAndAssert(FAKE_CATEGORY)

            val secondItem = awaitItem()
            LWTruth_assertThat(secondItem).isNotEmpty()
            val firstItem = secondItem.first()
            LWTruth_assertThat(firstItem).isNotNull()
            LWTruth_assertThat(firstItem.id).isEqualTo(FAKE_CATEGORY.id)

            deleteCategoryAndAssert(FAKE_CATEGORY)
            val newData = awaitItem()
            LWTruth_assertThat(newData).isEmpty()
        }
    }

    private suspend fun findCategoryAndAssert(categoryId: String) {
        val newResult = categoryRepository.findCategory(categoryId)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isNotNull()
        LWTruth_assertThat(foundData.id).isEqualTo(categoryId)
    }

    private suspend fun insertCategoryAndAssert(category: Category) {
        val result = categoryRepository.addCategory(category)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun updateCategoryAndAssert(category: Category) {
        val result = categoryRepository.updateCategory(category)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun deleteCategoryAndAssert(category: Category) {
        val result = categoryRepository.deleteCategory(category)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }
}
