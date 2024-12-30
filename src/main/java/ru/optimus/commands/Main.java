package ru.optimus.commands;

import org.bukkit.plugin.java.JavaPlugin;
import ru.optimus.commands.asm.ClassFinder;

public class Main extends JavaPlugin {


  @Override
  public void onEnable() {
    new ClassFinder("ru.optimus.commands.commands", this).register();
  }
}
