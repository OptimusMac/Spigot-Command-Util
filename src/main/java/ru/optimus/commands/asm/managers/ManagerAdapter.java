package ru.optimus.commands.asm.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import ru.optimus.commands.asm.AsmAdapter;
import ru.optimus.commands.util.Validate;

@UtilityClass
public class ManagerAdapter {

  @Getter
  private static final HashMap<JavaPlugin, List<AsmAdapter>> classes = new HashMap<>();

  public static AsmAdapter addAdapter(JavaPlugin plugin, AsmAdapter adapter) {
    classes.compute(plugin, (key, asmAdapters) -> {
      if (asmAdapters == null) {
        asmAdapters = new ArrayList<>();
      }
      asmAdapters.add(adapter);
      return asmAdapters;
    });
    return adapter;
  }

  public static Optional<List<AsmAdapter>> getAdapter(JavaPlugin request, JavaPlugin receiver) {
    if (!Validate.hasPlugin(request).get()) {
      throw new NullPointerException("Request plugin cannot be null!");
    }

    if (!Validate.isOwner(request, receiver).get()) {
      throw new RuntimeException("Receiver plugin cannot send foreign plugin adapter");
    }
    return Optional.ofNullable(classes.get(request));
  }

}
