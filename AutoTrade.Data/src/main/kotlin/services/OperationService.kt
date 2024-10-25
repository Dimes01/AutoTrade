package services

import io.grpc.Channel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.PortfolioRequest
import ru.tinkoff.piapi.core.InvestApi
import ru.tinkoff.piapi.core.models.Portfolio

@Service
class OperationService(
    @Autowired private val channel: Channel
) {
    fun getPortfolioInfo(portfolioRequest: PortfolioRequest): Portfolio {
        val api = InvestApi.createReadonly(channel)
        val resultFuture = api.operationsService.getPortfolio(portfolioRequest.accountId)
        return resultFuture.resultNow()
    }
}