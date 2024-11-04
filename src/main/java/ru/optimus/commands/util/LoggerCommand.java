package ru.optimus.commands.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerCommand {


  public static Logger getLogger(String command, String path) {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    Configuration config = context.getConfiguration();

    PatternLayout layout = PatternLayout.newBuilder()
        .withPattern("%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n")
        .build();

    FileAppender fileAppender = FileAppender.newBuilder()
        .withName("command" + command)
        .withFileName(path)
        .withAppend(true)
        .withLayout(layout)
        .build();

    fileAppender.start();
    config.addAppender(fileAppender);

    LoggerConfig loggerConfig = new LoggerConfig(command, org.apache.logging.log4j.Level.INFO,
        false);
    loggerConfig.addAppender(fileAppender, org.apache.logging.log4j.Level.INFO, null);
    config.addLogger(command, loggerConfig);

    context.updateLoggers();

    return LogManager.getLogger(command);
  }

}
