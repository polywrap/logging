package io.polywrap.plugins.logger

import io.polywrap.configBuilder.polywrapClient
import io.polywrap.core.InvokeResult
import io.polywrap.core.resolution.Uri
import io.polywrap.plugins.logger.wrap.*
import kotlin.test.Test
import kotlin.test.assertTrue

class LoggerPluginIntegrationTest {

    @Test
    fun invokeByClient() {
        val uri = Uri("plugin/logger@1.0")
        val plugin = loggerPlugin(null)

        val client = polywrapClient {
            setPackage(uri.toString() to plugin)
        }

        val result: InvokeResult<Boolean> = client.invoke(
            uri = uri,
            method = "log",
            args = ArgsLog(LogLevel.DEBUG, "Hello, World!")
        )
        if (result.isFailure) throw result.exceptionOrNull()!!

        assertTrue(result.getOrThrow())
    }
}
