"""This package contains the Logger plugin."""
import logging
from dataclasses import dataclass, field

from polywrap_plugin import PluginPackage

from .wrap import (
    ArgsLog,
    InvokerClient,
    LogLevel,
    Module,
    UriPackageOrWrapper,
    manifest,
)


@dataclass
class LoggerConfig:
    """Defines the configuration for the Logger plugin. """
    logger: logging.Logger = field(default_factory=logging.getLogger)
    level: LogLevel = LogLevel.INFO


class LoggerModule(Module[LoggerConfig]):
    """Defines the Logger module."""

    def __init__(self, config: LoggerConfig):
        """Initializes the Logger module."""
        self.config = config
        self.logger = self.config.logger
        self.logger.setLevel(self.config.level.name)

    async def log(
        self,
        args: ArgsLog,
        client: InvokerClient[UriPackageOrWrapper],
        env: None
    ) -> bool:
        """Logs a message."""
        level = (args['level'].value + 1) * 10
        self.logger.log(level, args['message'])
        return True


def logger_plugin(config: LoggerConfig = LoggerConfig()) -> PluginPackage[LoggerConfig]:
    """Returns the Logger plugin package."""
    return PluginPackage(module=LoggerModule(config), manifest=manifest)
