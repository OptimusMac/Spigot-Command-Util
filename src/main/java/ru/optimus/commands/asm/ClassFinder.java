package ru.optimus.commands.asm;

import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import ru.optimus.commands.annotations.AsCommand;
import ru.optimus.commands.annotations.logging.Logging;
import ru.optimus.commands.executes.AbstractCommand;

public class ClassFinder extends AsmAdapter {

  public ClassFinder(String path, JavaPlugin plugin) {
    super(path, plugin);
  }


  @Override
  @SneakyThrows
  public void register() {
    for (Class<? extends AbstractCommand> clazz : getClasses()) {
      AsCommand asCommand = clazz.getAnnotation(AsCommand.class);
      if (asCommand != null) {
        String name = asCommand.name();
        CommandMap commandMap = getPlugin().getServer().getCommandMap();
        if (commandMap.getCommand(name) == null) {
          AbstractCommand command = clazz.getDeclaredConstructor().newInstance();
          Logging logging = command.getClass().getAnnotation(Logging.class);
          if (logging != null) {
            Field loggingWriterField = AbstractCommand.class.getDeclaredField("loggingWriter");
            loggingWriterField.setAccessible(true);
            LoggingWriter loggingWriter = (LoggingWriter) loggingWriterField.get(command);
            loggingWriter.path = logging.path();
            loggingWriter.format = logging.format();
            loggingWriter.add(clazz);
          }

          commandMap.register(asCommand.name(), command);

        }
      }
    }
  }
}
