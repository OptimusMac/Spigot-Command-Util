package ru.optimus.commands.annotations.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ru.optimus.commands.executes.AbstractCommand;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {

  /**
   * Used only absolute path!<br>
   * &lt;sender&gt; - sender command.<br>
   * &lt;command&gt; - name command executed player.<br>
   * &lt;args&gt; - args command.<br>
   * &lt;time&gt; - time since the command was sent.<br>
   * The command will be logged to a file only if the {@link #onCommand()} method
   * from the {@link AbstractCommand} class returns true.<br>
   * <br>
   * <p>
   * Example path: <b>plugins/test/log.txt</b>
   * </p>
   */

  String format() default "[<time>] player <sender> send command <command> with args <args>";
  String path() default "commandLogs/logging.txt";
}
