package configurations

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import utils.AuthInterceptor

@Configuration
@PropertySource(value = ["classpath:datamodule.properties", "classpath:local.properties"])
class ExchangeServicesConfiguration(

    @Value("\${ru.tinkoff.piapi.core.api.target}")
    private val target: String,

    @Value("\${ru.tinkoff.piapi.core.api.token}")
    private val token: String

) {
    private val authInterceptor: AuthInterceptor = AuthInterceptor(token)
    private val channel: Channel = ManagedChannelBuilder
        .forTarget(target)
        .useTransportSecurity()
        .intercept(authInterceptor)
        .build()

    @Bean
    fun getChannel(): Channel = channel
}