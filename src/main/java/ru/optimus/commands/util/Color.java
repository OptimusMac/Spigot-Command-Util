package ru.optimus.commands.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

@UtilityClass
public class Color {


  public static String alternate(@NonNull String text) {
    TextColor textColor = TextColor.fromCSSHexString(text);
    return textColor == null ? ChatColor.translateAlternateColorCodes('&', text) : textColor.asHexString();

  }

}
