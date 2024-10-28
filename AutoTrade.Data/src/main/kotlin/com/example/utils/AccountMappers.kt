package com.example.utils

import com.example.dto.AccountDTO
import ru.tinkoff.piapi.contract.v1.Account
import java.time.Instant

class AccountMappers {
    fun mapToAccountDTO(account: Account): AccountDTO =
        AccountDTO(
            account.id,
            account.type,
            account.name,
            account.status,
            Instant.ofEpochSecond(account.openedDate.seconds),
            Instant.ofEpochSecond(account.closedDate.seconds),
            account.accessLevel
        )
}