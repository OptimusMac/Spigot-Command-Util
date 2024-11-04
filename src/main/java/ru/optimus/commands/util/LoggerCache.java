package ru.optimus.commands.util;

import java.util.HashMap;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import lombok.experimental.UtilityClass;
import ru.optimus.commands.executes.AbstractCommand;

@UtilityClass
public class LoggerCache {

  private static final HashMap<Class<? extends AbstractCommand>, Logger> loggers = new HashMap<>();


  public static Logger registerLogger(String command, String path,
      Class<? extends AbstractCommand> clazz) {
    Logger logger = LoggerCommand.getLogger(command, path);
    if (loggers.containsValue(logger)) {
      throw new IllegalArgumentException("This logger has already been registered before!");
    }
    loggers.put(clazz, logger);
    return logger;
  }

  public static Logger getLogger(String command, String path,
      Class<? extends AbstractCommand> clazz) {
    return Optional.ofNullable(loggers.get(clazz))
        .orElseGet(() -> registerLogger(command, path, clazz));

  }



}
