package ru.optimus.commands.asm;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
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
        String commandName = asCommand.name();
        if (isCommandNotRegistered(commandName)) {
          registerCommand(clazz, asCommand);
        }
      }
    }
  }

  private boolean isCommandNotRegistered(String commandName) {
    CommandMap commandMap = getPlugin().getServer().getCommandMap();
    return commandMap.getCommand(commandName) == null;
  }

  private void registerCommand(Class<? extends AbstractCommand> clazz, AsCommand asCommand)
      throws Exception {
    AbstractCommand command = clazz.getDeclaredConstructor().newInstance();

    setupLogging(command, clazz);

    updatePluginCommands(asCommand.name());

    PluginCommand pluginCommand = createPluginCommand(asCommand.name());
    pluginCommand.setExecutor(
        (sender, cmd, label, args) -> {
          command.execute(sender, label, args);
          return true;
        });
    pluginCommand.setTabCompleter(command::tabCompleter);
    pluginCommand.setAliases(Arrays.asList(asCommand.aliases()));

    getPlugin().getServer().getCommandMap().register(asCommand.name(), pluginCommand);
  }

  private void setupLogging(AbstractCommand command, Class<? extends AbstractCommand> clazz)
      throws Exception {
    Logging logging = command.getClass().getAnnotation(Logging.class);
    if (logging != null) {
      Field loggingWriterField = AbstractCommand.class.getDeclaredField("loggingWriter");
      loggingWriterField.setAccessible(true);
      LoggingWriter loggingWriter = (LoggingWriter) loggingWriterField.get(command);
      loggingWriter.path = logging.path();
      loggingWriter.format = logging.format();
      loggingWriter.add(clazz);
    }
  }

  @SuppressWarnings("unchecked")
  private void updatePluginCommands(String commandName) throws Exception {
    Object pluginDescription = getPlugin().getDescription();
    Field commandsField = pluginDescription.getClass().getDeclaredField("commands");
    commandsField.setAccessible(true);

    ImmutableMap<String, Map<String, Object>> currentCommands = (ImmutableMap<String, Map<String, Object>>) commandsField.get(
        pluginDescription);
    Map<String, Map<String, Object>> modifiableCommands = new HashMap<>(currentCommands);
    modifiableCommands.put(commandName, new HashMap<>());
    commandsField.set(pluginDescription, ImmutableMap.copyOf(modifiableCommands));
  }

  private PluginCommand createPluginCommand(String commandName) throws Exception {
    Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(
        String.class, Plugin.class);
    constructor.setAccessible(true);
    return constructor.newInstance(commandName, getPlugin());
  }

}
