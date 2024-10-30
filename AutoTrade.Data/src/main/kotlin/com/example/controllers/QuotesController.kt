package com.example.controllers

import com.example.dto.LastPriceDTO
import com.example.services.QuotesService
import jakarta.validation.constraints.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.piapi.contract.v1.CandleInterval
import ru.tinkoff.piapi.contract.v1.HistoricCandle
import java.time.Instant

@RestController
@RequestMapping("/quotes")
class QuotesController(
    @Autowired private val quotesService: QuotesService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(QuotesController::class.java)
    }

    @GetMapping("/last-price")
    fun getLastPrice(
        @RequestParam(name = "idsInstrument", required = true) @Size(min = 1, max = 50) idsInstrument: Iterable<String>
    ): ResponseEntity<List<LastPriceDTO>> {
        logger.info("Endpoint /quotes/last-price/$idsInstrument starts work")
        return ResponseEntity.ok(quotesService.getLastPrice(idsInstrument))
            .also { logger.info("Endpoint /quotes/last-price/$idsInstrument finishes work") }
    }

    @GetMapping("/historic-candles")
    fun getCandles(
        @RequestParam(name = "idInstrument", required = true) @NotBlank idInstrument: String,
        @RequestParam(name = "from", required = true) @NotNull from: Instant,
        @RequestParam(name = "to", required = true) @NotNull to: Instant,
        @RequestParam(name = "interval", required = true) @Min(0) @Max(13) interval: Int
    ): ResponseEntity<List<HistoricCandle>> {
        logger.info("Endpoint /quotes/historic-candles/$idInstrument starts work")
        return ResponseEntity.ok(quotesService.getCandles(idInstrument, from, to, CandleInterval.forNumber(interval)))
            .also { logger.info("Endpoint /quotes/historic-candles/$idInstrument finishes work") }
    }
}