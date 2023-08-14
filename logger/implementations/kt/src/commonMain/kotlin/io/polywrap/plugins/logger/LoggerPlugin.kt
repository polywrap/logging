package io.polywrap.plugins.logger

import io.polywrap.core.Invoker
import io.polywrap.plugin.PluginFactory
import io.polywrap.plugin.PluginPackage
import io.polywrap.plugins.logger.wrap.*
import org.slf4j.LoggerFactory

/**
 * A plugin for interacting with the platform's file system
 *
 * @property config An optional configuration object for the plugin.
 */
class LoggerPlugin(config: Config? = null) : Module<LoggerPlugin.Config?>(config) {

    /**
     * Configuration class for LoggerPlugin.
     */
    class Config()

    private val logger = LoggerFactory.getLogger(LoggerPlugin::class.java)

    override suspend fun log(args: ArgsLog, invoker: Invoker): Boolean {
        when (args.level) {
            LogLevel.INFO -> logger.info(args.message)
            LogLevel.DEBUG -> logger.debug(args.message)
            LogLevel.WARN -> logger.warn(args.message)
            LogLevel.ERROR -> logger.error(args.message)
        }
        return true
    }
}

val loggerPlugin: PluginFactory<LoggerPlugin.Config?> = { config: LoggerPlugin.Config? ->
    PluginPackage(
        pluginModule = LoggerPlugin(config),
        manifest = manifest
    )
}
