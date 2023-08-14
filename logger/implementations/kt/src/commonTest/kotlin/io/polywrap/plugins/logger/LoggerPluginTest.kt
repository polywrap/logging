package io.polywrap.plugins.logger

import emptyMockInvoker
import io.polywrap.plugins.logger.wrap.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class LoggerPluginTest {

    private val plugin = LoggerPlugin()
    private val invoker = emptyMockInvoker

    @Test
    fun logInfo() = runTest {
        val args = ArgsLog(LogLevel.INFO, "Hello, World!")
        val result = plugin.log(args, invoker)
        assertTrue(result)
    }

    @Test
    fun logDebug() = runTest {
        val args = ArgsLog(LogLevel.DEBUG, "Hello, World!")
        val result = plugin.log(args, invoker)
        assertTrue(result)
    }

    @Test
    fun logWarn() = runTest {
        val args = ArgsLog(LogLevel.WARN, "Hello, World!")
        val result = plugin.log(args, invoker)
        assertTrue(result)
    }

    @Test
    fun logError() = runTest {
        val args = ArgsLog(LogLevel.ERROR, "Hello, World!")
        val result = plugin.log(args, invoker)
        assertTrue(result)
    }
}
