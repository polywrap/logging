/// NOTE: This is an auto-generated file.
///       All modifications will be overwritten.

package io.polywrap.plugins.logger.wrap

import io.polywrap.core.Invoker
import io.polywrap.core.InvokeResult
import io.polywrap.core.resolution.Uri
import io.polywrap.core.msgpack.GenericMapExtensionSerializer
import kotlinx.serialization.Serializable

typealias GenericMap<K, V> = @Serializable(with = GenericMapExtensionSerializer::class) io.polywrap.core.msgpack.GenericMap<K, V>

typealias BigInt = String
typealias BigNumber = String
typealias Json = String

/// Env START ///
/// Env END ///

/// Objects START ///
/// Objects END ///

/// Enums START ///
@Serializable
enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
}

/// Enums END ///

/// Imported Objects START ///
/// Imported Objects END ///

/// Imported Modules START ///
/// Imported Modules END ///
