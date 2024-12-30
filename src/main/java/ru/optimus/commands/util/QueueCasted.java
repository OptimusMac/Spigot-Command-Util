package ru.optimus.commands.util;

import java.util.ArrayDeque;

public class QueueCasted<E> extends ArrayDeque<E> {


  public <T> T poll(Class<T> type) {
    E element = poll();

    if (element == null) {
      throw new IllegalStateException("No elements to poll");
    }

    String value = element.toString();

    if (type == String.class) {
      return type.cast(value);
    } else if (type == Integer.class) {
      return type.cast(Integer.parseInt(value));
    } else if (type == Double.class) {
      return type.cast(Double.parseDouble(value));
    } else if (type == Float.class) {
      return type.cast(Float.parseFloat(value));
    } else if (type == Long.class) {
      return type.cast(Long.parseLong(value));
    } else if (type == Short.class) {
      return type.cast(Short.parseShort(value));
    } else if (type == Byte.class) {
      return type.cast(Byte.parseByte(value));
    } else if (type == Boolean.class) {
      return type.cast(Boolean.parseBoolean(value));
    } else {
      throw new UnsupportedOperationException("Type " + type.getName() + " is not supported");
    }
  }

}
