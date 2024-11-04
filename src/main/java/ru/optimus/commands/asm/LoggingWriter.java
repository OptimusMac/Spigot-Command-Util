package ru.optimus.commands.asm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.optimus.commands.executes.AbstractCommand;
import ru.optimus.commands.util.Color;
import ru.optimus.commands.util.LoggerCache;

public class LoggingWriter {

  protected final Collection<Class<? extends AbstractCommand>> acceptClasses = new ArrayList<>();
  protected String path, format;

  public void add(Class<? extends AbstractCommand> classes) {
    this.acceptClasses.add(classes);
  }

  public void write(CommandSender sender, String command, String[] args,
      Class<? extends AbstractCommand> request) {

    if (!acceptClasses.contains(request)) {
      throw new RuntimeException("Class " + request.getName() + " cannot be execute logwriter!");
    }

    Optional.ofNullable(path).ifPresent(value -> {

      Logger logger = LoggerCache.getLogger(command, path, request);
      logger.info(format.replace("<time>", LocalDate.now().toString())
          .replace("<sender>", sender.getName()).replace("<command>", command)
          .replace("<args>", Arrays.toString(args)));
    });
  }
}

