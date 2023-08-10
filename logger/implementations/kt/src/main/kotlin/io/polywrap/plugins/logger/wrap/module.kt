/// NOTE: This is an auto-generated file.
///       All modifications will be overwritten.

package io.polywrap.plugins.logger.wrap

import io.polywrap.core.Invoker
import io.polywrap.core.msgpack.msgPackDecode
import io.polywrap.core.msgpack.msgPackEncode
import io.polywrap.plugin.PluginMethod
import io.polywrap.plugin.PluginModule
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
data class ArgsLog(
    val level: LogLevel,
    val message: String,
)

@Suppress("UNUSED_PARAMETER", "FunctionName")
abstract class Module<TConfig>(config: TConfig) : PluginModule<TConfig>(config) {

  final override val methods: Map<String, PluginMethod> = mapOf(
      "log" to ::__log,
  )

  abstract suspend fun log(
      args: ArgsLog,
      invoker: Invoker
  ): Boolean

  private suspend fun __log(
      encodedArgs: ByteArray?,
      encodedEnv: ByteArray?,
      invoker: Invoker
    ): ByteArray {
        val args: ArgsLog = encodedArgs?.let {
            msgPackDecode(ArgsLog.serializer(), it).getOrNull()
                ?: throw Exception("Failed to decode args in invocation to plugin method 'log'")
        } ?: throw Exception("Missing args in invocation to plugin method 'log'")
        val response = log(args, invoker)
        return msgPackEncode(serializer(), response)
  }
}
