import {
  Module,
  Args_log,
  LogLevel,
  LogLevelEnum,
  manifest,
} from "./wrap";

import { PluginFactory, PluginPackage } from "@polywrap/plugin-js";

export type LogFunc = (level: LogLevel, message: string) => void;

export interface LoggerPluginConfig {
  logFunc?: LogFunc;
}

export class LoggerPlugin extends Module<LoggerPluginConfig> {
  public log(args: Args_log): boolean {
    if (this.config.logFunc) {
      this.config.logFunc(args.level, args.message);
      return true;
    }

    switch (args.level) {
      case "DEBUG":
      case LogLevelEnum.DEBUG:
        console.debug(args.message);
        break;
      case "WARN":
      case LogLevelEnum.WARN:
        console.warn(args.message);
        break;
      case "ERROR":
      case LogLevelEnum.ERROR:
        console.error(args.message);
        break;
      case "INFO":
      case LogLevelEnum.INFO:
        console.log(args.message);
        break;
      default:
        console.log(args.message);
    }

    return true;
  }
}

export const loggerPlugin: PluginFactory<LoggerPluginConfig> = (
  config: LoggerPluginConfig
) => new PluginPackage(new LoggerPlugin(config), manifest);

export const plugin = loggerPlugin;
