package ru.optimus.commands.util;

import java.util.Optional;

public record Argument(String name) {

  public <T> Optional<T> getArgument(Class<T> type) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Argument value cannot be null or blank.");
    }

    try {
      if (type == String.class) {
        return Optional.of(type.cast(name));
      } else if (type == Boolean.class) {
        String lowerCaseArg = name.toLowerCase();
        if (lowerCaseArg.equals("true") || lowerCaseArg.equals("1")) {
          return Optional.of(type.cast(true));
        } else if (lowerCaseArg.equals("false") || lowerCaseArg.equals("0")) {
          return Optional.of(type.cast(false));
        } else {
          return Optional.empty();
        }
      } else if (Number.class.isAssignableFrom(type)) {
        if (name.contains(".")) {
          if (type == Float.class) {
            return Optional.of(type.cast(Float.parseFloat(name)));
          } else if (type == Double.class) {
            return Optional.of(type.cast(Double.parseDouble(name)));
          } else {

            return Optional.empty();
          }
        } else {
          if (type == Integer.class) {
            return Optional.of(type.cast(Integer.parseInt(name)));
          } else if (type == Long.class) {
            return Optional.of(type.cast(Long.parseLong(name)));
          } else if (type == Short.class) {
            return Optional.of(type.cast(Short.parseShort(name)));
          } else if (type == Byte.class) {
            return Optional.of(type.cast(Byte.parseByte(name)));
          }  else {
            return Optional.empty();
          }
        }
      } else {
        return Optional.empty();
      }
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  public String getUnsafe(){
    return name;
  }
}
