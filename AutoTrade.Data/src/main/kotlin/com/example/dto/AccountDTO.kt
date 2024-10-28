@file:UseSerializers(InstantSerializer::class)

package com.example.dto

import com.example.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.tinkoff.piapi.contract.v1.AccessLevel
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.contract.v1.AccountType
import java.time.Instant

@Serializable
data class AccountDTO(
    val id: String,
    val type: AccountType,
    val name: String,
    val status: AccountStatus,
    val openedDate: Instant,
    val closedDate: Instant,
    val accessLevel: AccessLevel,
)