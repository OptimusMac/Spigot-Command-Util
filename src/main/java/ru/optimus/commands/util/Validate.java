package ru.optimus.commands.util;


import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class Validate {

  private final Predicate<JavaPlugin> predicate = plugin ->
      Bukkit.getPluginManager().getPlugin(plugin.getName()) != null;


  public static Supplier<Boolean> hasPlugin(JavaPlugin plugin) {
    return () -> predicate.test(plugin);
  }

  public static Supplier<Boolean> isOwner(JavaPlugin request, JavaPlugin receiver) {
    return () -> Objects.equals(request, receiver) && predicate.and(plugin -> receiver != null).test(request);
  }

}
