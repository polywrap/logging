import io.polywrap.configBuilder.polywrapClient
import io.polywrap.core.InvokeResult
import io.polywrap.core.resolution.Uri
import io.polywrap.plugins.logger.loggerPlugin
import io.polywrap.plugins.logger.wrap.ArgsLog
import io.polywrap.plugins.logger.wrap.LogLevel
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HelloWorld {

    val interfaceUri = "wrapscan.io/polywrap/logger@1.0"
    val client = polywrapClient {
        addDefaults()
        setPackage("plugin/logger" to loggerPlugin(null))
        addInterfaceImplementation(interfaceUri, "plugin/logger")
        setRedirect(interfaceUri to "plugin/logger")
    }

    @Test
    fun printHelloWorld() {
        println("Invoking Method: log")

        val result: InvokeResult<Boolean> = client.invoke(
            uri = Uri(interfaceUri),
            method = "log",
            args = ArgsLog(LogLevel.INFO, "Hello, World!")
        )

        assertNull(result.exceptionOrNull())
        assertTrue(result.getOrThrow())
    }
}
