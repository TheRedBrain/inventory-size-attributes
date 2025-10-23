# Inventory Size Attributes

This mod adds two new entity attributes, which control how many inventory and hotbar slots are active.

## Configuration

The entity attributes "generic.hotbar_slot_amount" and "generic.inventory_slot_amount" control how many slots are active.

The "natural_player_hotbar_size" and "natural_player_inventory_size" server config options control the amount of hotbar and inventory slots each player has by default.

## "What happens to items on my hotbar/in my inventory when those become smaller?"

When a hotbar or inventory slot contains an item and becomes inactive, that item is moved to an active inventory slot or dropped at the players location, when the inventory is full. This is announced to the player via a chat message.

## Mod Compatibility

Items that are placed in inactive slots (possible when a modded screen is not hiding inactive inventory slots), are moved to active slots or dropped on the ground when the inventory is full. This is announced to the player via a chat message.

### Adding support for inactive inventory slots in modded screens

Mods that show the player inventory in custom screens can depend on this mod to hide inactive inventory slots.

Examples can be found in this mods [source code](https://github.com/TheRedBrain/inventory-size-attributes/).