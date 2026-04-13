# 1.4.0

- added client config option to hide inactive hotbar slots in the HUD
- added client config option to center the hotbar HUD element (no effect if all nine slots are active)

## Changes

- replaced the new game rules with server config settings again, I decided that I don't like game rules that much
- inactive inventory slots are now hidden by default (can be changed in the client config)

# 1.3.0

- replaced the "default_hotbar_slot_amount" server config option with "naturalHotbarSize" game rule and adjusted the "generic.hotbar_slot_amount" entity attribute
- replaced the "default_inventory_slot_amount" server config option with "naturalInventorySize" game rule and adjusted the "generic.inventory_slot_amount" entity attribute

# 1.2.1

- added support for latest Slot Customization API version

# 1.2.0

- inactive hotbar slots are no longer selectable in the ingame HUD
- added client config option to hide inactive inventory slots in all vanilla screens
- fixed crafter and shulker box screens not disabling inactive slots

# 1.1.0

- removed dependency on cloth config
- added dependency on fzzy config
- added missing localization keys
- added API methods for getting active hotbar/inventory size of a player to main class
- inventory size is now also checked when opening the normal inventory

# 1.0.1

- internal refactoring to improve compatibility

# 1.0.0

Initial release.

#