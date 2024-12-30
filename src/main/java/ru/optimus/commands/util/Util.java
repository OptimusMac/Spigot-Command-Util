package ru.optimus.commands.util;

public class Util {


  public static <T> T  castElement(Class<T> type, String name){
    try {
      if (type == String.class) {
        return type.cast(name);
      } else if (type == Boolean.class) {
        String lowerCaseArg = name.toLowerCase();
        if (lowerCaseArg.equals("true") || lowerCaseArg.equals("1")) {
          return type.cast(true);
        } else if (lowerCaseArg.equals("false") || lowerCaseArg.equals("0")) {
          return type.cast(false);
        } else {
          throw new RuntimeException("argument cannot cast for " + type.getName());
        }
      } else if (Number.class.isAssignableFrom(type)) {
        if (name.contains(".")) {
          if (type == Float.class) {
            return type.cast(Float.parseFloat(name));
          } else if (type == Double.class) {
            return type.cast(Double.parseDouble(name));
          } else {
            throw new RuntimeException("argument cannot cast for " + type.getName());
          }
        } else {
          if (type == Integer.class) {
            return type.cast(Integer.parseInt(name));
          } else if (type == Long.class) {
            return type.cast(Long.parseLong(name));
          } else if (type == Short.class) {
            return type.cast(Short.parseShort(name));
          } else if (type == Byte.class) {
            return type.cast(Byte.parseByte(name));
          }  else {
            throw new RuntimeException("argument cannot cast for " + type.getName());
          }
        }
      } else {
        throw new RuntimeException("argument cannot cast for " + type.getName());
      }
    } catch (NumberFormatException e) {
      throw new RuntimeException("argument cannot cast for " + type.getName());
    }
  }

}
