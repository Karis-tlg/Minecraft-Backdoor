

# DOOM Backdoor

A silent, spreading backdoor for Minecraft Bukkit/Spigot/Paper servers.
Using the injector is recommended, should you choose to manually backdoor a plugin, you're on your own if you run into problems.

For educational purposes only. Do not use on a server you do not own.

## Requirements:
* Java 8 runtime.
* Desired target plugin jar file.

## Usage instructions:
* Run doom-(version).jar.
* Select desired plugin file.
* Input your Minecraft UUID.
* Input chat command prefix. (Default: xQc>)

## Commands
Default command prefix is ``xQc>``,  this can be changed.
* xQc>op - Give player operator status
* xQc>deop - Remove player's operator status
* xQc>ban -  Ban player
* xQc>banip - IP ban player
* xQc>gamemode - Change gamemode
* xQc>give - Give items
* xQc>exec - Execute a command as the server console. **[Visible]**
* xQc>chaos - Deop and Ban all ops currently online. Give admin to everyone else. **[Visible]**
* xQc>seed - Find world seed
* xQc>coords - Find player coordinates
* xQc>tp - Teleport to coordinates **[Visible, See below.]**
* #help - List all available commands, with syntax and description.

Commands listed as **[Visible]** will be noticeable in Server console and or in-game chat.

Warning:
Teleporting may cause a '[player name] moved to quickly!' warning in server console. It may also cause anti-cheat to kick you.
Other strange behavior may occur when teleporting extreme distances. (such as to the world border)

## License
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
