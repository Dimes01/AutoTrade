package services

import io.grpc.Channel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.Account
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.core.InvestApi

@Service
class UserService(
    @Autowired private val channel: Channel
) {
    fun getAccounts(status: AccountStatus): MutableList<Account> {
        val api = InvestApi.createReadonly(channel)
        val result = api.userService.getAccounts(status)
        return result.get()
    }
}