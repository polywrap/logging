import XCTest
@testable import LoggerPlugin
@testable import PolywrapClient


func getClient() throws -> (PolywrapClient, Uri) {
    let uri = try Uri("plugin/logger")
    let loggerPlugin = getLoggerPlugin(nil)
    let package = PluginPackage(loggerPlugin)
    let builder = BuilderConfig().addPackage(uri, package)
    return (builder.build(), uri)
}

final class LoggerPluginTests: XCTestCase {
    func testLog() throws {
        let (client, uri) = try getClient()
        let args = ArgsLog(level: LogLevel.INFO, message: "Hallo!")
        
        let response: Bool = try client.invoke(uri: uri, method: "log", args: args)
        XCTAssert(response)
    }
}
