package ru.optimus.commands.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.optimus.commands.annotations.AsCommand;
import ru.optimus.commands.annotations.Messages;
import ru.optimus.commands.annotations.logging.Logging;
import ru.optimus.commands.executes.AbstractCommand;

@AsCommand(name = "teleport", permission = "teleport:use", aliases = {"tp",
    "teleportPlayer"}, args = 4)
@Logging
@Messages(exceptionMessage = "/teleport <player> <x> <y> <z>", onlyPlayer = "Only for player!")
public class Command extends AbstractCommand {

  public Command() {
    registerArgument("player", "x", "y", "z");
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {

    getArgumentQueue("player", "x", "y", "z").execute(queue -> {
      Player p = Bukkit.getPlayerExact(queue.poll(String.class));
      int xPos = queue.poll(Integer.class);
      int yPos = queue.poll(Integer.class);
      int zPos = queue.poll(Integer.class);
      p.teleport(new Location(p.getWorld(), xPos, yPos, zPos));


    });

    return true;
  }

  @Override
  public List<String> tabCompleter(@NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (args.length == 1) {
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    return new ArrayList<>();
  }
}
