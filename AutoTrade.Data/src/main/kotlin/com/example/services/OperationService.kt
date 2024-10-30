package com.example.services

import io.grpc.Channel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.core.InvestApi
import ru.tinkoff.piapi.core.models.Positions

@Service
class OperationService(
    private val channel: Channel
) {
    companion object {
        private val logger = LoggerFactory.getLogger(OperationService::class.java)

    }
    private val api = InvestApi.createReadonly(channel)

    fun getPositions(idAccount: String): Positions {
        logger.info("Method 'getPortfolioInfo': started")
        val result = api.operationsService.getPositions(idAccount)
        logger.info("Method 'getPortfolioInfo': finished")
        return result.get()
    }
}