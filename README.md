# 🌟 **CommandUtil** - Специальное API для команд на Spigot! 🚀

Привет, волшебники кода! 🔮 Встречайте **CommandUtil** — API, которое сделает ваши команды на Spigot **быстрыми**, **красивыми** и **удобными**. 🔥

**CommandUtil** позволяет вам легко и удобно управлять командами, благодаря мощным аннотациям и автоматической настройке. Готовы к магии? Тогда продолжим! ✨

---

## ✨ **Основные особенности** ✨

### 1️⃣ **@AsCommand** — Для каждой команды, как истинный маг! 🧙‍♂️
Аннотация для базовой настройки команды:
- `name`: название команды.
- `args`: количество аргументов (по умолчанию -1, значит любое количество).
- `permission`: право для выполнения команды.
- `onlyPlayer`: команда выполняется только для игроков. ⚔️
- `aliases`: дополнительные алиасы команды.

---

### 2️⃣ **@Messages** — Ваши магические сообщения! ✉️
Для проверки на:
- **onlyPlayer** — "Только для игрока!" 🎮
- **Аргументы** — Правильное количество аргументов.
- **Права** — "У вас нет прав!" 🚫

---

### 3️⃣ **@HelpCommand** — Мудрость в помощь! 📜
Создайте команду, например `/mycmd help`, и она отобразит все команды вашего плагина, которые имеют аннотацию **Description**. С помощью этого, пользователи смогут легко получить справку по всем доступным командам!

---

### 4️⃣ **@Logging** — Логирование магических событий! 📚
- Включите логирование команд с помощью аннотации **@Logging**.
- Формат записи логов: `"[<time>] player <sender> send command <command> with args <args>"`.
- Логи будут сохраняться по умолчанию в `commandLogs/logging.txt`.

---

### 5️⃣ **Методы в AbstractCommand** — Ваши верные помощники! 🧑‍💻
- `registerArgument(String[] args)` — Регистрация аргументов команды.
- `getArgument(String name)` — Получите значение аргумента.
- `getRange(int start, int end, boolean space)` — Сбор аргументов в диапазоне.
- `getArgumentQueue` — Работайте с очередью аргументов.

---

## ⚡ **Пример использования** ⚡

```java
@AsCommand(name = "teleport", permission = "teleport:use", aliases = {"tp", "teleportPlayer"}, args = 4)
@Logging
@Messages(exceptionMessage = "/teleport <player> <x> <y> <z>", onlyPlayer = "Only for player!")
public class Command extends AbstractCommand {

  public Command() {
    registerArgument("player", "x", "y", "z");
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    Player player = Bukkit.getPlayerExact(getArgument("player").getUnsafe());
    int x = getArgument("x").getArgument(Integer.class);
    int y = getArgument("y").getArgument(Integer.class);
    int z = getArgument("z").getArgument(Integer.class);
    player.teleport(new Location(player.getWorld(), x, y, z));
    return true;
  }

  @Override
  public List<String> tabCompleter(@NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if(args.length == 1){
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }

    return List.of();
  }
}
```
