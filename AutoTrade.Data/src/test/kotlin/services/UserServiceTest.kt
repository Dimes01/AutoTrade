package services

import configurations.ExchangeServicesConfiguration
import org.junit.jupiter.api.Test
import org.mockito.Mock

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.tinkoff.piapi.contract.v1.AccountStatus

@SpringBootTest(classes = [UserService::class, ExchangeServicesConfiguration::class])
class UserServiceTest(
    @Mock private val service: UserService
) {

    @Test
    fun getAccounts() {
        val result = service.getAccounts(AccountStatus.ACCOUNT_STATUS_OPEN)
        println(result)
    }
}