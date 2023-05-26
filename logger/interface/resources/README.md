# Logging Wrapper Interface

| Version | URI | WRAP Version |
|-|-|-|
| 1.0.0 | [`wrap://ens/wraps.eth:logging@1.0.0`](https://wrappers.io/v/ens/wraps.eth:logging@1.0.0) | 0.1 |

## Interface
```graphql
type Module {
    # Log a message
    log(level: LogLevel!, message: String!, context: String): LogEntry!

    trace(message: String!, context: String): LogEntry!
    debug(message: String!, context: String): LogEntry!
    info(message: String!, context: String): LogEntry!
    warn(message: String!, context: String): LogEntry!
    error(message: String!, context: String): LogEntry!
    fatal(message: String!, context: String): LogEntry!

    # Change the minimum log level at runtime
    setLogLevel(level: LogLevel!): Boolean!

    # Get the current log level
    getLogLevel: LogLevel!

    # Get a specific log entry by ID
    getLog(id: Int!): LogEntry

    # Get a list of logs, optionally by log level
    getLogs(level: LogLevel): [LogEntry!]!
}

enum LogLevel {
    TRACE
    DEBUG
    INFO
    WARN
    ERROR
    FATAL
}

type LogEntry {
    id: Int!
    timestamp: String! # ISO 8601 format
    level: LogLevel!
    message: String!
    context: String
}
```

## Usage
```graphql
#import * from "ens/wraps.eth:logging@1.0.0"
```

And implement the interface methods within your programming language of choice.

## Source Code
[Link](https://github.com/polywrap/std/logging)

## Known Implementations
[Link](https://github.com/polywrap/logging/tree/master/implementations)