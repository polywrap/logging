# @polywrap/datetime-plugin-js
The Datetime plugin implements the `datetime-interface` @ [wrapscan.io/polywrap/datetime@1.0](../../interface/) (see [./polywrap.graphql](./polywrap.graphql)). It provides access to the current unix timestamp of the host operating system.

## Usage
### 1. Configure Client
When creating your Polywrap JS client, add the datetime plugin:
```typescript
import { ClientConfigBuilder } from "@polywrap/client-config-builder-js";
import { PolywrapClient } from "@polywrap/client-js";
import { datetimePlugin } from "@polywrap/datetime-plugin-js";

const config = new ClientConfigBuilder()
  // 1. Add the plugin package @ an arbitrary URI
  .addPackage(
    "plugin/datetime",
    datetimePlugin({ })
  )
  // 2. Register this plugin as an implementation of the interface
  .addInterfaceImplementation(
    "wrapscan.io/polywrap/datetime@1.0",
    "plugin/datetime"
  )
  // 3. Redirect invocations @ the interface to the plugin (default impl)
  .addRedirect(
    "wrapscan.io/polywrap/datetime@1.0",
    "plugin/datetime"
  )
  .build();

const client = new PolywrapClient(config);
```

### 2. Invoke the Datetime Plugin
Invocations to the datetime plugin can be made via the interface URI (which will get redirected), or the plugin's URI directly:
```typescript
await client.invoke({
  uri: "wrapscan.io/polywrap/datetime@1.0" | "plugin/datetime",
  method: "currentTimestamp"
});
```
