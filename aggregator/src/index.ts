import {
  Logger,
  Logger_Module,
  Logger_LogLevel,
  Args_debug,
  Args_info,
  Args_warn,
  Args_error,
  Args_loggers,
  ModuleBase
} from "./wrap";
import { Args_log } from "./wrap/imported/Logger_Module/serialization";

export class Module extends ModuleBase {
  log(args: Args_log): bool {
    const uris = this.loggers({});
    for (let i = 0; i < uris.length; i++) {
      new Logger_Module(uris[i]).log({
        message: args.message,
        level: args.level,
      }).unwrap();
    }
    return true;
  }

  debug(args: Args_debug): bool {
    return this.log({
      message: args.message,
      level: Logger_LogLevel.DEBUG,
    });
  }

  info(args: Args_info): bool {
    return this.log({
      message: args.message,
      level: Logger_LogLevel.INFO,
    });
  }

  warn(args: Args_warn): bool {
    return this.log({
      message: args.message,
      level: Logger_LogLevel.WARN,
    });
  }

  error(args: Args_error): bool {
    return this.log({
      message: args.message,
      level: Logger_LogLevel.ERROR,
    });
  }

  loggers(_: Args_loggers): string[] {
    return Logger.getImplementations();
  }
}
