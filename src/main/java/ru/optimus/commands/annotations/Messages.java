package ru.optimus.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Messages {


  String onlyPlayer();
  String exceptionMessage();
  String permission() default "You don't have permission!";

}
