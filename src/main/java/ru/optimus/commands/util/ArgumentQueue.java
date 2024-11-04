package ru.optimus.commands.util;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class ArgumentQueue {

  private final Queue<Argument> arguments = new ArrayDeque<>();


  public ArgumentQueue(List<Argument> argumentList) {
    this.arguments.addAll(argumentList);
  }


  public void execute(Consumer<Queue<String>> consumer) {
    Queue<String> values = new ArrayDeque<>();
    while (!arguments.isEmpty()) {
      values.add(arguments.poll().name());
    }
    consumer.accept(values);
  }
}
