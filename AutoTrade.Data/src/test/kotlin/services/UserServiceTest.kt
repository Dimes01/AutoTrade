package services

import com.example.configurations.ExchangeServicesConfiguration
import com.example.services.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mock

import org.springframework.boot.test.context.SpringBootTest
import ru.tinkoff.piapi.contract.v1.AccountStatus

@SpringBootTest(classes = [UserService::class, ExchangeServicesConfiguration::class])
class UserServiceTest {
    @Mock private lateinit var service: UserService

    // TODO: написать нормальные тесты
    @Test
    fun getAccounts() {
        val result = service.getAccounts(AccountStatus.ACCOUNT_STATUS_OPEN)
        println(result)
    }
}