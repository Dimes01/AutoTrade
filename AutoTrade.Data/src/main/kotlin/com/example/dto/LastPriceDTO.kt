@file:UseSerializers(InstantSerializer::class)

package com.example.dto

import com.example.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.tinkoff.piapi.contract.v1.LastPrice
import java.time.Instant

@Serializable
data class LastPriceDTO(
    val figi: String,
    val priceUnits: Long,
    val priceNano: Int,
    val time: Instant,
    val instrumentUid: String,
    val lastPriceType: Int,
)

fun LastPrice.mapToDTO(): LastPriceDTO =
    LastPriceDTO(figi, price.units, price.nano, Instant.ofEpochSecond(time.seconds), instrumentUid, lastPriceType.number)