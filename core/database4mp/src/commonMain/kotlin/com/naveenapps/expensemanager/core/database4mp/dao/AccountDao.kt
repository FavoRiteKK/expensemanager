package com.naveenapps.expensemanager.core.database4mp.dao

import androidx.room.Dao
import androidx.room.Query
import com.naveenapps.expensemanager.core.database4mp.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : BaseDao<AccountEntity> {

    @Query("SELECT * FROM account ORDER BY sequence ASC")
    fun getAccounts(): Flow<List<AccountEntity>?>

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun findById(id: String): AccountEntity?
}
