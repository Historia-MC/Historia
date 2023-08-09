# Historia v1.0.0 Requirement Checklist

```
To be considered as a release, the following requirements must be met:
```

## [Proficiency]

### [Stats]
- [x] Apply modified health.
- [x] Apply modified speed.
- [x] Apply modified experience.
- [x] Apply chance for attack evasion.
- [x] Apply weapon proficiency check for attack.
- [x] Apply chance for unsuccessful harvest.
- [x] Apply chance for double harvest.
- [x] Apply chance for instant growth.
- [x] Apply chance to behead player on death.
- [x] Apply sword proficiency damage bonus/debuff.
- [ ] Apply bow proficiency damage bonus/debuff.
- [ ] Apply crossbow proficiency damage bonus/debuff.
- [x] Display proficiency level as player experience level.
- [x] Restrict food intake to maximum for proficiency.

### [Skills]

- [ ] (Cant use without NMS) Prevent player from displaying nametag.
- [x] Apply feather falling to boots for builders.
- [x] Apply quick charge to crossbow for hunters.
- [x] Apply efficiency pickaxe to miners.
- [x] Apply efficiency shovel to miners.
- [x] Apply efficiency axe to woodcutters.
- [x] Apply chance for double ore drop for miners.
- [x] Apply chance for double wood drop for woodcutters.
- [x] Apply chance for double feather drop for hunters.
- [ ] Apply chance for no anvil damage for blacksmiths.
- [x] Apply chance to not consume the block for builders.
- [ ] Apply custom fishing loot for fishermen.
- [x] Check if player can ignite oil on bow.
- [x] Check if player can harvest oil from sources.
- [ ] Check if player can harvest grass?
- [x] Check if player can craft saddles.
- [x] Check if player can tame animals.
- [ ] Check if player can use sweeping edge?
- [x] Check if player can extract bones from sources.
- [x] Check if player can break beehives.
- [x] Check if player can craft gunpowder.
- [ ] Check if player can apply unbreaking.
- [x] Check if player can make high tier armor.
- [ ] Check if player can generate a knowledge book.
- [ ] Increase breath underwater for fishermen (enchant of respiration)
- [ ] Allow building of tridents for fishermen and farmers.
- [ ] Allow holding bonemeal for farmers.
- [ ] Allow for increased yields for farmers based on level.
- [ ] Allow for greater leather drops for hunters.
- [x] Allow for architects to place ladders and scaffolding in other towns.
- [x] Allow for apothecaries to apply poison to arrows.

## [HistoriaPlayer]

- [x] Get experience increase for successful proficiency action.
- [x] Check level up condition and apply level up.
- [x] Calculate dealth pentalty to experience and level down where necessary.
- [ ] Calculate ambient temperature.
- [ ] Calculate armor weight and temperature.
- [ ] Check exhaustion condition and apply debuffs.

## [Crafting]

### [Weapons]

- [x] Apply crafting table pattern match for weapon recipes.
- [ ] Apply damage to player on hit after armor and proficiency modifier.
- [ ] Bypass modifiers if other entity.
- [x] Apply randomized damage value based on range and level.
- [x] Apply randomized durability value based on range and level.
- [x] Apply randomized attack speed value based on range and level.
- [x] Apply randomized knockback value based on range and level.
- [ ] Apply randomized critical chance value based on range and level (?).
- [x] Apply randomized sweeping edge damage value based on range and level.
- [ ] Add trident recipe and weights in the weapons yml.

### [Armor]

- [x] Apply crafting table pattern match for armor recipes.
- [x] Apply armor defence value based on range and level.
- [x] Apply durability value based on range and level.

### [Other]

- [x] Apply crafting table pattern match for gunpowder recipe.
- [x] Apply crafting table pattern match for oil-tipped arrow recipe.

## [MySQL]

- [x] Create connection on init.
- [x] Create table on init.
- [x] Validation of user existance.
- [x] Save HistoriaPlayer to table.
- [x] Load HistoriaPlayer from table.
- [x] Getter/Setter for username.
- [x] Getter/Setter for proficiency name.
- [x] Getter/Setter for proficiency level.
- [x] Getter/Setter for proficiency experience.
- [x] Getter/Setter for login time.
- [x] Getter/Setter for logout time.

## [Configuration]

- [x] Validate existence of files against resource folder.
- [x] Create missing files on disk if not found.
- [x] Validate resource version files against files on disk.
- [ ] Apply or overwrite missing values in files on disk.
- [x] Create a configuration factory.

## [Items]

### [Weapons]

- [x] Create object for each weapon.
- [x] Preload weapon objects on init into hashmap.
- [x] Getter for weapon object.

### [Armor]

- [x] Create object for each armor.
- [x] Preload armor objects on init into hashmap.
- [x] Getter for armor object.

### [Tools]

- [ ] Create tools that match custom resource types.
- [ ] Create object for each tool.
- [ ] Preload tool objects on init into hashmap.
- [ ] Getter for tool object.

### [Other]

- [x] Create object for each item.
- [x] Validate if they are limited to a specific pattern.
- [x] Preload item objects on init into hashmap.
- [x] Getter for item object.

## [Experience]

- [x] Player without custom weapon deals base item damage. 
- [ ] Arrow landing has chance of igniting block if flamable.
- [x] Apply slowness debuff on damage.
- [ ] Can eat large fish / meat multiple times (?)
- [ ] Toggle PvP on/off for settlements that have been raided or are attacking after being raided.
- [x] Right click stonecutter to increase sharpness.
- [x] Can shear chickens for feathers.
- [x] Limited use for sharpness.
- [ ] Allow dulling of weapons (decrease base stats).
