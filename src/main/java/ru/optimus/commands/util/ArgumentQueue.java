package ru.optimus.commands.util;

import java.util.List;
import java.util.function.Consumer;

public class ArgumentQueue {

  private final QueueCasted<Argument> arguments = new QueueCasted<>();


  public ArgumentQueue(List<Argument> argumentList) {
    this.arguments.addAll(argumentList);
  }


  public void execute(Consumer<QueueCasted<String>> consumer) {
    QueueCasted<String> values = new QueueCasted<>();
    while (!arguments.isEmpty()) {
      values.add(arguments.poll().getName());
    }
    consumer.accept(values);
  }
}
