# @polywrap/logger-plugin-js
The Logger plugin implements the `logger-interface` @ [ens/wraps.eth:logger@1.0.0](https://app.ens.domains/name/wraps.eth/details) (see [./src/schema.graphql](./src/schema.graphql)). By default, it logs all events using the Javascript `console` global object. You can circumvent this functionality by setting the `logFunc` property on the plugin's config (examples below).

## Usage
### 1. Configure Client
When creating your Polywrap JS client, add the logger plugin:
```typescript
import { PolywrapClient, ClientConfigBuilder } from "@polywrap/client-js";
import { loggerPlugin } from "@polywrap/logger-plugin-js";

const config = new ClientConfigBuilder()
  // 1. Add the plugin package @ an arbitrary URI
  .addPackage(
    "plugin/logger",
    loggerPlugin({ })
  )
  // 2. Register this plugin as an implementation of the interface
  .addInterfaceImplementation(
    "ens/wraps.eth:logger@1.0.0",
    "plugin/logger"
  )
  // 3. Redirect invocations @ the interface to the plugin (default impl)
  .addRedirect(
    "ens/wraps.eth:logger@1.0.0",
    "plugin/logger"
  )
  .build();

const client = new PolywrapClient(config));
```

### 2. Invoke The Logger
Invocations to the logger plugin can be made via the interface URI (which will get redirected), or the plugin's URI directly:
```typescript
await client.invoke({
  uri: "ens/wraps.eth:logger@1.0.0" | "plugin/logger",
  method: "log",
  args: {
    level: "INFO",
    message: "foo bar baz"
  }
});
```

### 3. Customize The Logger
When adding the logger to your client, you can add your own custom log function:
```typescript
const config = new ClientConfigBuilder()
  .addPackage(
    "plugin/logger",
    loggerPlugin({
      logFunc: (level: string, message: string): void => {
        // add your own logic here...
      }
    })
  )
  .build();

const client = new PolywrapClient(config);
```

### 4. Add Multiple Loggers
Multiple logger implementations can be added to the client:
```typescript
const config = new ClientConfigBuilder()
  .addPackage(
    "plugin/logger",
    loggerPlugin({ })
  )
  .addPackage(
    "plugin/custom-logger",
    loggerPlugin({ logFunc: ... })
  )
  .addInterfaceImplementations(
    "ens/wraps.eth:logger@1.0.0",
    ["plugin/logger", "plugin/custom-logger"]
  )
  .addRedirect(
    "ens/wraps.eth:logger@1.0.0",
    "plugin/logger"
  )
  .build();

const client = new PolywrapClient(config);
```

### 5. Invoke All Logger Implementations
When you'd like to log something to more than one logger, you can invoke all implementations of the logger interface:
```typescript
const result = await client.getImplementations(
  "ens/wraps.eth:logger@1.0.0"
);

const implementations: string[] = result.ok ? result.value : [];

for (const impl of implementations) {
  await client.invoke({
    uri: impl,
    method: "log",
    args: {
      level: "INFO",
      message: "message"
    }
  });
}
```
