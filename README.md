# 🌟 **CommandUtil** - Мощное API для команд на Spigot! 🚀

Добро пожаловать в **CommandUtil** — API для создания и управления командами на Spigot с использованием аннотаций! 🎮✨

С этим API вы сможете легко создавать команды для вашего плагина, обрабатывать аргументы и даже логировать каждое использование команд. 🧙‍♂️

## 🛠️ **Как это работает** 🛠️

### 🎯 **1. Аннотация @AsCommand — Магия команды!** 🎯
Эта аннотация является основой вашего класса команды и позволяет вам настраивать его параметры:
- `name`: имя команды.
- `args`: количество аргументов (по умолчанию -1, что означает любое количество).
- `permission`: права, необходимые для выполнения команды.
- `onlyPlayer`: команда будет доступна только игрокам.
- `aliases`: альтернативные имена для команды (например, сокращения).

**Пример**:
```java
@AsCommand(name = "teleport", permission = "teleport:use", aliases = {"tp", "teleportPlayer"}, args = 4)
```

### 📜 **2. Аннотация @Messages — Ваши магические сообщения!** 📜
Эта аннотация нужна для управления сообщениями, которые появляются при:

- **onlyPlayer**: проверка, чтобы команда выполнялась только игроком.
- **exceptionMessage**: сообщение о неправильном количестве аргументов.
- **permission**: сообщение, если у игрока нет прав на выполнение команды.

**Пример**:
```java
@Messages(exceptionMessage = "/teleport <player> <x> <y> <z>", onlyPlayer = "Только для игроков!")
```

### 📚 **3. Аннотация @HelpCommand — Помощь, когда ты в беде!** 📚
Вы можете добавить команду `/mycmd help`, которая будет автоматически собирать все зарегистрированные команды с аннотацией **@Description** и выводить их справку.

- **Метод `sendHelp()`**: внутри вашего класса команды вы должны использовать метод `sendHelp()` в дефолтной реализации `onCommand`. Этот метод будет выводить справку по всем доступным командам с аннотацией **HelpCommand**.
- Если на классе команды нет аннотации **HelpCommand**, будет выброшено исключение: `Use method sendHelp can only with annotation HelpCommand`.
- **Важно!** Класс с аннотацией **HelpCommand** должен иметь аннотацию **@AsCommand**, иначе будет выброшено исключение: `Annotation HelpCommand can use only with annotation AsCommand`.

**Пример**:
```java
@AsCommand(name = "help", permission = "help:use", args = 0)
@HelpCommand
public class HelpCommandClass extends AbstractCommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sendHelp();  // Метод, который покажет справку по всем доступным командам
        return true;
    }
}
```

### 📝 **4. Аннотация @Logging — Логирование магических событий!** 📝
Для логирования использования команд добавьте аннотацию **@Logging**. Логи будут автоматически записываться в файл, указанный в пути.

- **format**: строка формата для записи лога (по умолчанию: `"[<time>] player <sender> send command <command> with args <args>"`).
- **path**: путь, куда будет записываться лог (по умолчанию: `"commandLogs/logging.txt"`).

**Пример**:
```java
@AsCommand(name = "teleport", permission = "teleport:use", args = 4)
@Logging(format = "[<time>] Player <sender> used /teleport with args <args>", path = "logs/teleport_log.txt")
public class TeleportCommand extends AbstractCommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // Ваш код команды
        return true;
    }
}
```
### ⚙️ **5. Методы в AbstractCommand — Ваши верные помощники!** ⚙️
Методы, которые делают ваш код проще и удобнее:

- **`registerArgument(String[] args)`** — Регистрация аргументов в конструкторе команды.
- **`getArgument(String name)`** — Получение значения аргумента по его имени.
- **`getRange(int start, int end, boolean space)`** — Получение диапазона аргументов. Параметр `space` указывает, будет ли использован пробел между аргументами.
- **`getArgumentQueue`** — Управление очередью аргументов для последовательного их извлечения.

**Пример**:
```java
@AsCommand(name = "teleport", args = 4)
@Messages(exceptionMessage = "/teleport <player> <x> <y> <z>")
public class TeleportCommand extends AbstractCommand {

    public TeleportCommand() {
        registerArgument("player", "x", "y", "z"); // Регистрация аргументов
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // Получение значений аргументов
        String player = getArgument("player").getUnsafe(); // Без Optional
        int x = getArgument("x").getArgument(Integer.class);
        int y = getArgument("y").getArgument(Integer.class);
        int z = getArgument("z").getArgument(Integer.class);

        // Пример использования диапазона аргументов
        String coordinates = getRange(1, 3, true); // Вернет "x y z"

        // Управление аргументами через очередь
        ArgumentQueue argumentQueue = getArgumentQueue("player", "x", "y", "z");
        argumentQueue.execute((queue) -> {
            String playerName = queue.poll();
            int posX = queue.poll(Integer.class);
            int posY = queue.poll(Integer.class);
            int posZ = queue.poll(Integer.class);
            // Ваш код с использованием аргументов
        });

        return true;
    }
}
```

### 🔧 **Как это подключить в своем плагине** 🔧

Чтобы начать использовать **CommandUtil** в вашем плагине, просто зарегистрируйте адаптер в методе `onEnable`:

```java
@Override
public void onEnable() {
    new ClassFinder("ru.optimus.commands.commands", this).register();
}
```

### 📌 **Заключение** 📌

**CommandUtil** — это не просто инструмент для работы с командами, это целая магия, которая делает ваш код проще, понятнее и красивее! 🚀✨  

Если у вас есть вопросы или предложения, не стесняйтесь задавать их. Мы всегда рады помочь! 💬  

Спасибо за использование **CommandUtil**! 💖  
