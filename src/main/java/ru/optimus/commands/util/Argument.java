package ru.optimus.commands.util;

import java.util.Optional;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class Argument {


  @Getter
  private String name;

  public Argument(String name) {
    this.name = name;
  }

  @NotNull
  public <T> T getArgument(Class<T> type) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Argument value cannot be null or empty.");
    }

    return Util.castElement(type, name);
  }

  public String getUnsafe(){
    return name;
  }
}
