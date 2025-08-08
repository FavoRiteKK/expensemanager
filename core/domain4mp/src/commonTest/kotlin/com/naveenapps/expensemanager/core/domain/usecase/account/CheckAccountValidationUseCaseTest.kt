package com.naveenapps.expensemanager.core.domain.usecase.account

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_ACCOUNT
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CheckAccountValidationUseCaseTest : BaseCoroutineTest() {

    private var checkAccountValidationUseCase = CheckAccountValidationUseCase()

    @Test
    fun whenProperAccountShouldReturnSuccess() {
        val response = checkAccountValidationUseCase.invoke(FAKE_ACCOUNT)

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        val success = (response as Resource.Success)
        LWTruth_assertThat(success).isNotNull()
        LWTruth_assertThat(success.data).isTrue()
    }

    @Test
    fun whenAccountIdIsNotAvailableShouldResultError() {
        val response = checkAccountValidationUseCase.invoke(
            FAKE_ACCOUNT.copy(id = ""),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconNameNotAvailableShouldResultError() {
        val response = checkAccountValidationUseCase.invoke(
            FAKE_ACCOUNT.copy(
                storedIcon = StoredIcon(
                    name = "",
                    backgroundColor = "#ffff",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconBGColorNotAvailableShouldResultError() {
        val response = checkAccountValidationUseCase.invoke(
            FAKE_ACCOUNT.copy(
                storedIcon = StoredIcon(
                    name = "Sample",
                    backgroundColor = "",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenStoredIconBGColorNotStartsWithHashShouldResultError() {
        val response = checkAccountValidationUseCase.invoke(
            FAKE_ACCOUNT.copy(
                storedIcon = StoredIcon(
                    name = "Sample",
                    backgroundColor = "sample",
                ),
            ),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun whenAccountNameIsNotAvailableShouldResultError() {
        val response = checkAccountValidationUseCase.invoke(
            FAKE_ACCOUNT.copy(name = ""),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}
