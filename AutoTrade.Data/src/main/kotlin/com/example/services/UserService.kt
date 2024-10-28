package com.example.services

import io.grpc.Channel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.Account
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.core.InvestApi

@Service
class UserService(
    @Autowired private val channel: Channel
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }
    private val api = InvestApi.createReadonly(channel)

    fun getAccounts(status: AccountStatus): List<Account> {
        logger.info("Method 'getAccounts': started")
        val result = api.userService.getAccounts(status)
        return result.get()
    }
}
