# Logger Plugin
The Logger plugin implements the `logger-interface` @ [wrapscan.io/polywrap/logger@1.0.0](../../interface/) (see [./polywrap.graphql](./polywrap.graphql)). The plugin makes it possible to log messages from a Wasm Wrap.

## Installation

Kotlin
```kotlin
dependencies {
    implementation("io.polywrap.plugins:logger-plugin:0.10.0")
}
```

Groovy
```groovy
dependencies {
    implementation "io.polywrap.plugins:logger-plugin:0.10.0"
}
```

The Logger plugin depends on [SLF4J](https://www.slf4j.org/) and requires an SLF4J provider implementation. For example, you can use logback-android by adding the following to your `build.gradle` file:

```kotlin
dependencies {
    implementation("com.github.tony19:logback-android:3.0.0")
}
```

Each SLF4J provider implementation is configured differently. See the provider documentation for more information.

## Usage

### 1. Configure Client
When creating your Polywrap JS client, add the logger plugin:
```kotlin
import io.polywrap.configBuilder.polywrapClient
import io.polywrap.core.resolution.Uri
import io.polywrap.plugins.logger.wrap.*

val client = polywrapClient {
    // 1. Add the plugin package @ an arbitrary URI
    setPackage("plugin/logger" to loggerPlugin(null))
    // 2. Register this plugin as an implementation of the interface
    addInterfaceImplementation(
        "wrapscan.io/polywrap/logger@1.0",
        "plugin/logger"
    )
    // 3. Redirect invocations @ the interface to the plugin (default impl)
    setRedirect("wrapscan.io/polywrap/logger@1.0" to "plugin/logger")
}
```

### 2. Invoke The Logger
Invocations to the logger plugin can be made via the interface URI (which will get redirected), or the plugin's URI directly:
```kotlin
client.invoke(
    uri = uri,
    method = "log",
    args = ArgsLog(LogLevel.DEBUG, "Hello, World!")
)
```
