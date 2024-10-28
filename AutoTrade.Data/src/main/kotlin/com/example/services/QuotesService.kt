package com.example.services

import io.grpc.Channel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.CandleInterval
import ru.tinkoff.piapi.contract.v1.HistoricCandle
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.core.InvestApi
import java.time.Instant

@Service
class QuotesService(
    @Autowired private val channel: Channel,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(QuotesService::class.java)
    }
    private val api = InvestApi.createReadonly(channel)

    fun getLastPrice(idsInstrument: Iterable<String>): List<LastPrice> {
        logger.info("Method 'getLastPrice': started")
        return api.marketDataService.getLastPrices(idsInstrument).get()
            .also { logger.info("Method 'getLastPrice': finished") }
    }

    fun getCandles(idInstrument: String, from: Instant, to: Instant, interval: CandleInterval): List<HistoricCandle> {
        logger.info("Method 'getCandles': started")
        return api.marketDataService.getCandles(idInstrument, from, to, interval).get()
            .also { logger.info("Method 'getCandles': finished") }
    }
}