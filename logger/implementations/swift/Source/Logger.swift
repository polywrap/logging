import OSLog
import PolywrapClient

typealias LogFunc = (LogLevel, String) -> Void

extension Logger {
    /// Using your bundle identifier is a great way to ensure a unique identifier.
    private static var subsystem = Bundle.main.bundleIdentifier!

    /// Logs the view cycles like a view that appeared.
    static let viewCycle = Logger(subsystem: subsystem, category: "viewcycle")

    /// All logs related to tracking and analytics.
    static let statistics = Logger(subsystem: subsystem, category: "statistics")
}

class LoggerPlugin: Plugin {
    var methodsMap: [String : PluginMethod] = [:]
    
    private let config: LogFunc?

    public init(config: LogFunc?) {
        self.config = config
    }

    func log(_ args: ArgsLog, _ env: VoidCodable?, _ invoker: Invoker) -> Bool {
        if let logFunc = config {
            logFunc(args.level, args.message)
            return true
        }

        switch args.level {
        case .DEBUG:
            Logger.viewCycle.debug("\(args.message)")
        case .WARN:
            Logger.viewCycle.warning("\(args.message)")
        case .ERROR:
            Logger.viewCycle.error("\(args.message)")
        case .INFO:
            Logger.viewCycle.info("\(args.message)")
        }

        return true
    }
}

func getLoggerPlugin(logFunc: LogFunc?) -> LoggerPlugin {
    var plugin = LoggerPlugin(config: logFunc)
    plugin.addMethod(name: "log", closure: plugin.log)
    return plugin
}
