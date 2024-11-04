package ru.optimus.commands.asm;

import java.util.Set;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import ru.optimus.commands.executes.AbstractCommand;

@Getter
public abstract class AsmAdapter {

  private final JavaPlugin plugin;
  private final String path;


  public AsmAdapter(String path, JavaPlugin plugin) {
    this.path = path;
    this.plugin = plugin;
  }

  public Set<Class<? extends AbstractCommand>> getClasses() {
    Reflections reflections = new Reflections(getPath());
    Set<Class<? extends AbstractCommand>> result = null;
    try {
      result = reflections.getSubTypesOf(AbstractCommand.class);
    } catch (ReflectionsException e) {
      System.err.println("Perhaps you specified the path in the adapter incorrectly");
    }
    return result;
  }

  public abstract void register();
}
