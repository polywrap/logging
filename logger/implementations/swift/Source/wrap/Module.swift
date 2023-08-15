// NOTE: This is an auto-generated file.
//       All modifications will be overwritten.

import PolywrapClient
import Foundation

public struct ArgsLog: Codable {
    var level: LogLevel
    var message: String
}


public protocol Plugin: PluginModule {
    func log(_ args: ArgsLog, _ env: VoidCodable?, _ invoker: Invoker) throws -> Bool
}
