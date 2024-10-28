package com.example.services

import com.example.dto.AccountDTO
import com.example.utils.AccountMappers
import io.grpc.Channel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.core.InvestApi

@Service
class UserService(
    @Autowired private val channel: Channel
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
        private val mappers = AccountMappers()
    }
    private val api = InvestApi.createReadonly(channel)

    fun getAccounts(status: AccountStatus): List<AccountDTO> {
        logger.info("Method 'getAccounts': started")
        val result = api.userService.getAccounts(status)
        return result.get()
            .map { mappers.mapToAccountDTO(it) }
            .also { logger.info("Method 'getAccounts': finished") }
    }
}
