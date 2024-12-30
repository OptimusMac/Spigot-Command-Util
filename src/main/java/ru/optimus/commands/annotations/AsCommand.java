package ru.optimus.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;


@Qualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface AsCommand {

  String name();
  int args() default -1;
  String permission();
  boolean onlyPlayer() default true;
  String[] aliases();



}
