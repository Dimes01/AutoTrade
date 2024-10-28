package com.example.controllers

import com.example.services.OperationService
import com.example.services.UserService
import jakarta.validation.constraints.NotBlank
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.piapi.contract.v1.Account
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.core.models.Positions

@RestController
@RequestMapping("/portfolio")
class PortfolioController(
    private val userService: UserService,
    private val operationService: OperationService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(PortfolioController::class.java)
    }

    //TODO: вместо контрактов написать DTO

    @GetMapping("/accounts/open")
    fun getAccounts(): ResponseEntity<List<Account>> {
        logger.info("Endpoint '/portfolio/accounts/open' starts work")
        return ResponseEntity.ok(userService.getAccounts(AccountStatus.ACCOUNT_STATUS_OPEN))
            .also { logger.info("Endpoint '/portfolio/accounts/open' finishes work") }
    }

    @GetMapping("/positions/{idAccount}")
    fun getShores(@PathVariable @NotBlank idAccount: String): ResponseEntity<Positions> {
        logger.info("Endpoint '/portfolio/positions' starts work")
        return ResponseEntity.ok(operationService.getPositions(idAccount))
            .also { logger.info("Endpoint '/portfolio/positions' finishes work") }
    }
}