package ru.optimus.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HelpCommand {

  /**
   * Формат для вывода всех {@link Description} из классов команд
   * Пример: "/%s - %s"
   */

  String format() default "/%s - %s";
}
