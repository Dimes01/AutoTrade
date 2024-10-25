package utils

import io.grpc.*
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall


internal class AuthInterceptor(token: String) : ClientInterceptor {
    private companion object {
        const val AUTH_HEADER_NAME: String = "Authorization"
        val AUTH_HEADER: Metadata.Key<String> = Metadata.Key.of(AUTH_HEADER_NAME, Metadata.ASCII_STRING_MARSHALLER)
    }
    private var authHeaderValue: String = "Bearer $token"

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        methodDescriptor: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        channel: Channel?
    ): ClientCall<ReqT, RespT> {
        val call = channel?.newCall(methodDescriptor, callOptions)
        return object : SimpleForwardingClientCall<ReqT, RespT>(call) {
            override fun start(responseListener: Listener<RespT>?, headers: Metadata) {
                headers.put(AUTH_HEADER, authHeaderValue)
                super.start(responseListener, headers)
            }
        }
    }
}