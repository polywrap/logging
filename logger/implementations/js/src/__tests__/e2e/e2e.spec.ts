import { PolywrapClient, ClientConfigBuilder } from "@polywrap/client-js";

import { loggerPlugin, LogFunc } from "../..";
import { LogLevel } from "../../wrap";

const console_log = jest.spyOn(console, "log");
const console_debug = jest.spyOn(console, "debug");
const console_warn = jest.spyOn(console, "warn");
const console_error = jest.spyOn(console, "error");

describe("loggerPlugin", () => {

  const pluginUri = "plugin/logger";
  const interfaceUri = "ens/wraps.eth:logger@1.0.0";

  function createClient(logFunc?: LogFunc): PolywrapClient {
    const config = new ClientConfigBuilder()
      .addPackage(
        pluginUri,
        loggerPlugin({ logFunc })
      )
      .addRedirect(
        interfaceUri,
        pluginUri
      )
      .addInterfaceImplementation(
        interfaceUri,
        pluginUri
      )
      .build();

    return new PolywrapClient(config);
  }

  it("logs to console appropriate level", async () => {
    const message = "Test message";
    const client = createClient();

    async function testLevel(
      level: LogLevel,
      mock: jest.SpyInstance
    ): Promise<void> {
      const response = await client.invoke<boolean>({
        uri: interfaceUri,
        method: "log",
        args: {
          level,
          message,
        },
      });
  
      if (!response.ok) fail(response.error);
      expect(response.value).toBe(true);
      expect(mock).toBeCalledWith(message)
    }

    await testLevel("INFO", console_log);
    await testLevel("DEBUG", console_debug);
    await testLevel("WARN", console_warn);
    await testLevel("ERROR", console_error);
  });

  it("supports setting a custom log function", async () => {
    const customLog = jest.fn();
    const client = createClient(customLog);

    const response = await client.invoke<boolean>({
      uri: interfaceUri,
      method: "log",
      args: {
        level: "DEBUG",
        message: "foo bar"
      }
    });

    if (!response.ok) fail(response.error);
    expect(response.value).toBe(true);
    expect(customLog).toBeCalledWith("DEBUG", "foo bar");
  });

  it("supports setting multiple implementations", async () => {
    const customLog1 = jest.fn();
    const customLog2 = jest.fn();

    const config = new ClientConfigBuilder()
      .addPackages({
        "plugin/logger-1": loggerPlugin({ logFunc: customLog1 }),
        "plugin/logger-2": loggerPlugin({ logFunc: customLog2 })
      })
      .addRedirect(
        interfaceUri,
        "plugin/logger-1"
      )
      .addInterfaceImplementations(
        interfaceUri,
        ["plugin/logger-1", "plugin/logger-2"]
      )
      .build();

    const client = new PolywrapClient(config);

    const implementations = await client.getImplementations(interfaceUri);

    if (!implementations.ok) fail(implementations.error);

    for (const implementation of implementations.value) {
      await client.invoke<boolean>({
        uri: implementation.uri,
        method: "log",
        args: {
          level: "INFO",
          message: "foo bar"
        }
      });
    }

    expect(customLog1).toBeCalledWith("INFO", "foo bar");
    expect(customLog2).toBeCalledWith("INFO", "foo bar");
  });
});
