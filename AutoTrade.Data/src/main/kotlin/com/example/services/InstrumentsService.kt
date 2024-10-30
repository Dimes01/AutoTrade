package com.example.services

import io.grpc.Channel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.Share
import ru.tinkoff.piapi.core.InvestApi

@Service
class InstrumentsService(
    private val channel: Channel
) {
    companion object {
        private val logger = LoggerFactory.getLogger(InstrumentsService::class.java)
    }
    private val api = InvestApi.createReadonly(channel)

    fun getShares(): List<Share> {
        logger.info("Method 'getShares': start")
        return api.instrumentsService.allSharesSync
            .also { logger.info("Method 'getShares': finish") }
    }
}