package ru.optimus.commands.executes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.optimus.commands.annotations.AsCommand;
import ru.optimus.commands.annotations.Description;
import ru.optimus.commands.annotations.HelpCommand;
import ru.optimus.commands.annotations.Messages;
import ru.optimus.commands.annotations.logging.LoggingService;
import ru.optimus.commands.asm.AsmAdapter;
import ru.optimus.commands.asm.LoggingWriter;
import ru.optimus.commands.asm.managers.ManagerAdapter;
import ru.optimus.commands.util.Argument;
import ru.optimus.commands.util.ArgumentQueue;
import ru.optimus.commands.util.Color;

public abstract class AbstractCommand extends Command {

  @LoggingService
  public LoggingWriter loggingWriter = new LoggingWriter();
  private final List<String> arguments = new ArrayList<>();
  private String[] original;
  private CommandSender sender;


  protected AbstractCommand() {
    super("");
    this.register();
  }


  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] a) {

    AsCommand command = getClass().getAnnotation(AsCommand.class);
    Messages message = getClass().getAnnotation(Messages.class);
    Predicate<Messages> predicate = Objects::nonNull;
    if (command != null) {
      int args = command.args();
      boolean onlyPlayer = command.onlyPlayer();

      boolean anMessage = predicate.test(message);

      if (a.length < args) {
        if (anMessage) {
          sender.sendMessage(Color.alternate(message.exceptionMessage()));
        } else {
          sender.sendMessage("Args too low");
        }
        return true;
      }

      if (!(sender instanceof Player) && onlyPlayer) {
        if (anMessage) {
          sender.sendMessage(Color.alternate(message.onlyPlayer()));
        } else {
          sender.sendMessage("this command executed only with player");
        }
        return true;
      }
      this.original = a;
      this.sender = sender;
      boolean execute = onCommand(sender, a);

      if (execute) {
        loggingWriter.write(sender, command.name(), a, getClass());
        return true;
      }
    }

    return false;
  }

  private void register() {
    AsCommand command = getClass().getAnnotation(AsCommand.class);
    if (command == null) {
      throw new RuntimeException(
          "Class without annotation 'AsCommand' cannot be contains this package!");
    }

    Messages messages = getClass().getAnnotation(Messages.class);

    setName(command.name());
    setPermission(command.permission());
    setAliases(List.of(command.aliases()));
    if (messages != null) {
      setPermissionMessage(messages.permission());
    }
  }

  public abstract boolean onCommand(CommandSender sender, String[] args);


  public void sendHelp(CommandSender sender, JavaPlugin request, JavaPlugin receiver) {
    HelpCommand helpCommand = getClass().getAnnotation(HelpCommand.class);
    if (helpCommand == null) {
      throw new RuntimeException("User method sendHelp can only with annotation HelpCommand");
    }

    AsCommand asCommand = getClass().getAnnotation(AsCommand.class);
    if (asCommand == null) {
      throw new RuntimeException("Annotation HelpCommand can use only with annotation AsCommand");
    }
    ManagerAdapter.getAdapter(request, receiver).ifPresent(asmAdapters -> {
      for (AsmAdapter asmAdapter : asmAdapters) {
        Set<Class<? extends AbstractCommand>> classes = asmAdapter.getClasses();
        for (Class<? extends AbstractCommand> clazz : classes) {
          Description desc = clazz.getAnnotation(Description.class);
          if (desc == null) {
            continue;
          }

          String message = Color.alternate(
              String.format(helpCommand.format(), asCommand.name(), desc.message()));
          sender.sendMessage(message);
        }
      }
    });
  }

  public void registerArgument(String... arguments) {
    for (String argument : arguments) {
      this.arguments.add(argument.toLowerCase());
    }
  }

  public int getPosition(String argument) {
    if (!arguments.contains(argument)) {
      return -1;
    }
    return arguments.indexOf(argument);
  }


  private Optional<String> getArgumentValue(String argument) {
    int position = getPosition(argument.toLowerCase());
    if (position == -1) {
      return Optional.empty();
    }
    if (original.length <= position) {
      throw new IllegalArgumentException(
          "You are using an argument that has a position greater than the size of the arguments");
    }

    return Optional.ofNullable(original[position]);
  }


  public Argument getArgument(String argument) {
    return new Argument(getArgumentValue(argument).orElseGet(() -> {
      Messages messages = getClass().getAnnotation(Messages.class);
      if(messages != null){
        sender.sendMessage(messages.exceptionMessage());
      }
      return null;
    }));
  }


  public String getRange(int start, int end, boolean space) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = start; i < Math.min(end, original.length); i++) {
      stringBuilder.append(original[i]).append(space ? " " : "");
    }
    return stringBuilder.toString();
  }

  public ArgumentQueue getArgumentQueue(String... args) {
    List<Argument> argumentQueues = this.arguments.stream()
        .filter(value -> List.of(args).contains(value))
        .map(value -> new Argument(getArgumentValue(value).orElse(null))).toList();
    return new ArgumentQueue(argumentQueues);
  }

}
