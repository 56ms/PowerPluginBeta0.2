# PowerPluginBeta 0.1
Version: Spigot Java Minecraft 1.18.2

Permissions:
Will be added soon! 

General Features:
 - Sidebar
 - Economy
 - Mana Working
 - Actionbar Stats
 - Player menu (shows the armor of player clicked on when NOT shifting)
 - Right Click Item abilities working!
 - Entity health bars
   
# Commands:
 - /setintel
   - Description: (No arguments required)
   - Usage: Sets the intelligence (intel) of an item.

 - /ppspawn <mob>
   - Description: Spawns the specified mob.
   - Arguments:
     - <mob>: The type of mob to spawn (e.g., zombie, skeleton, crypt_ghoul).

 - /trade <player> [ DISCONTINUED ]
   - Description: Initiates a trade with another player.
   - Arguments:
     - <player>: The player to trade with.

 - /amount <number>
   - Description: Set the item in your hand's amount.
   - Arguments:
     - <number>: The amount to set for the item in your hand.

 - /armorcolor <color> | /armorcolor clear
   - Description: Set or clear the color of leather armor.
   - Arguments:
     - <color>: The color to apply to the leather armor (e.g., red, blue).
     - clear: Clears the color from the leather armor.

 - /balance
   - Description: Check your current balance.
   - Arguments: None

 - /broadcast <message>
   - Description: Broadcast a message to the entire server.
   - Arguments:
     - <message>: The message to broadcast.

 - /ci | /ci <itemId>
   - Description: Opens a custom menu for custom items.
   - Arguments: 
     - <itemId>: (Optional) Specifies the custom item ID to view.

 - /day
   - Description: Sets the time of day to noon.
   - Arguments: None

 - /die
   - Description: Kills you.
   - Arguments: None

 - /dupe [amount]
   - Description: Duplicates the item in your hand.
   - Arguments:
     - [amount]: Optional amount of items to duplicate (e.g., 64). Defaults to duplicating the entire stack if not provided.

 - /enderchest
   - Description: Opens your ender chest.
   - Arguments: None

 - /gmc
   - Description: Sets your gamemode to creative.
   - Arguments: None

 - /god
   - Description: Becomes invincible.
   - Arguments: None

 - /hat
   - Description: Equip the item in your hand as your helmet.
   - Arguments: None

 - /help
   - Description: Displays help message!
   - Arguments: None

 - /invsee <player>
   - Description: Opens another player's inventory.
   - Arguments:
     - <player>: The player whose inventory you want to view.

 - /lore <add|remove|insert|clear|list> <line> [text]
   - Description: Modifies the lore of the item in your hand.
   - Arguments:
     - <add|remove|insert|clear|list>: Action to perform (add or remove).
     - <line>: The line number to modify (if applicable).
     - [text]: The text to add or remove (if applicable).

 - /meow <player>
   - Description: Sends a meow message to another player.
   - Arguments:
     - <player>: The player to send the message to.

 - /money <player> <add|remove|set> [amount]
   - Description: Manage player money.
   - Arguments:
     - <player>: Player Target
     - <add|remove|set>: Action wanted!
     - [amount]: Amount of money for action!

 - /mypermissions [search]
   - Description: Get your in-game permissions.
   - Arguments: 
     - [search]: OPTIONAL: search within your permissions for a specific one!

 - /nbt <subcommand> [args]
   - Description: Manage NBT data.
   - Arguments:
     - <subcommand>: Subcommands like get, set, remove.
     - [args]: Arguments for the subcommands (e.g., key and value).

 - /nick <nickname> | /nick (clears nickname)
   - Description: Set your nickname.
   - Arguments:
     - <nickname>: The new nickname to set.

 - /night
   - Description: Sets the time of day to midnight.
   - Arguments: None

 - /pay <player> <amount>
   - Description: Pay another player a specified amount of money.
   - Arguments:
     - <player>: The player to pay.
     - <amount>: The amount of money to pay.

 - /rarity <set|change|clear> <rarity> [type]
   - Description: Set, change, or clear the rarity of an item.
   - Arguments:
     - <set|change|clear>: Action to perform.
     - <rarity>: The rarity level (e.g., COMMON, RARE).
     - [type]: Optional type of item (e.g., sword).

 - /rename <newName>
   - Description: Rename the item you're holding.
   - Arguments:
     - <newName>: The new name for the item.

 - /repeat <message>
   - Description: Repeat your message back to you.
   - Arguments:
     - <message>: The message to repeat.

 - /repeatcommand <amount> <command to run>
   - Description: Executes a command multiple times.
   - Arguments:
     - <command>: The command to execute.
     - <amount>: The number of times to execute the command.

 - /servermessage <message>
   - Description: Broadcast a server-wide message.
   - Arguments:
     - <message>: The message to broadcast.

 - /setitem <minecraftItemID>
   - Description: Set the item you're holding to a specified item ID.
   - Arguments:
     - <minecraftItemID>: The item ID to set (e.g., diamond_sword).

 - /setrank <player> <rank>
   - Description: Set a player's rank.
   - Arguments:
     - <player>: The player to set the rank for.
     - <rank>: The rank to assign.

 - /trash
   - Description: Opens a trash bin.
   - Arguments: None

 - /warpmenu
   - Description: Currently a debug menu.
   - Arguments: None

 - /world <subcommand> [args]
   - Description: World management commands.
   - Arguments:
     - <subcommand>: Subcommands like create, delete, tp, etc.
     - [args]: Arguments for the subcommands (e.g., world name).

# Items: Format: "[NAME]: [ID]"
 - Aspect of the End: ASPECT_OF_THE_END
   - Material: Diamond Sword
   - Display Name: Aspect of the End
   - Description: High damage and strength with instant transmission ability.

 - Aspect of the Void: ASPECT_OF_THE_VOID
   - Material: Diamond Shovel
   - Display Name: Aspect of the Void
   - Description: Increased damage and strength with instant transmission ability.

 - Qbert Helmet: QBERT_HELMET
   - Material: Chainmail Helmet
   - Display Name: Qbert Helmet
   - Description: High magic find, health, defense, and intelligence.

 - Qbert Chestplate: QBERT_CHESTPLATE
   - Material: Chainmail Chestplate
   - Display Name: Qbert Chestplate
   - Description: High magic find, health, defense, and intelligence.

 - Qbert Leggings: QBERT_LEGGINGS
   - Material: Chainmail Leggings
   - Display Name: Qbert Leggings
   - Description: High magic find, health, defense, and intelligence.

 - Qbert Boots: QBERT_BOOTS
   - Material: Chainmail Boots
   - Display Name: Qbert Boots
   - Description: High magic find, health, defense, and intelligence.

 - Dctr's Space Helmet: DCTR_SPACE_HELMET
   - Material: Red Stained Glass
   - Display Name: Dctr's Space Helmet
   - Description: Rare space helmet from moon glass shards.

 - Gambling Coin: GAMBLE_COIN
   - Material: Sunflower
   - Display Name: Gambling Coin
   - Description: Chooses a random number between double your balance and 0.

 - Cyanide Pill: CYANIDE_PILL
   - Material: Polished Blackstone Button
   - Display Name: Cyanide Pill
   - Description: Instant death.

 - Pill Remnant: PILL_REMNANT
   - Material: Glass Bottle
   - Display Name: Cyanide Pill Remnant
   - Description: Shows that you lived a rough life.

 - Move Stick: MOVE_STICK
   - Material: Stick
   - Display Name: Move Stick
   - Description: Move quickly in the direction you are facing.

 - Rancher's Boots: RANCHER_BOOTS
   - Material: Leather Boots
   - Display Name: Rancher's Boots
   - Description: Prevents crop destruction while jumping.
 
- 1m coin item
  - Material: Green Wool
  - Display Name;  Millions for free!
  - Description: Gives you 1 million coins when clicked on!





