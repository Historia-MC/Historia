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
- [ ] Apply chance for unsuccessful harvest.
- [ ] Apply chance for double harvest.
- [ ] Apply chance for instant growth.
- [ ] Apply chance to behead player on death.
- [ ] Apply sword proficiency damage bonus/debuff.
- [ ] Apply bow proficiency damage bonus/debuff.
- [ ] Apply crossbow proficiency damage bonus/debuff.
- [x] Display proficiency level as player experience level.
- [ ] Restrict food intake to maximum for proficiency.

### [Skills]

- [ ] (Cant use without NMS) Prevent player from displaying nametag.
- [ ] Apply feather falling to boots for builders.
- [ ] Apply quick charge to crossbow for hunters.
- [ ] Apply efficiency pickaxe to miners.
- [ ] Apply efficiency shovel to miners.
- [ ] Apply efficiency axe to woodcutters.
- [ ] Apply chance for double ore drop for miners.
- [ ] Apply chance for double wood drop for woodcutters.
- [ ] Apply chance for double feather drop for hunters.
- [ ] Apply chance for no anvil damage for blacksmiths.
- [ ] Apply chance to not consume the block for builders.
- [ ] Check if player can ignite oil on bow.
- [ ] Check if player can harvest oil from sources.
- [ ] Check if player can harvest grass?
- [ ] Chekc if player can craft saddles.
- [ ] Check if player can tame animals.
- [ ] Check if player can use sweeping edge?
- [ ] Check if player can extract bones from sources.
- [ ] Check if player can break beehives.
- [ ] Check if player can craft gunpowder.
- [ ] Check if player can apply unbreaking.
- [ ] Check if player can make high tier armor.
- [ ] Check if player can generate a knowledge book.

## [HistoriaPlayer]

- [ ] Get experience increase for successful proficiency action.
- [ ] Check level up condition and apply level up.
- [ ] Calculate dealth pentalty to experience and level down where necessary.
- [ ] Calculate ambient temperature.
- [ ] Calculate armor weight and temperature.
- [ ] Check exhaustion condition and apply debuffs.

## [Expiry]

- [ ] Apply standard date format to food items.
- [ ] Check if food item has expired and apply debuff if eaten.
- [ ] Salted foods do not expire.

## [Crafting]

### [Weapons]

- [x] Apply crafting table pattern match for weapon recipes.
- [ ] Apply damage to player on hit after armor and proficiency modifier.
- [ ] Bypass modifiers if other entity.
- [ ] Apply randomized damage value based on range and level.
- [ ] Apply randomized durability value based on range and level.
- [ ] Apply randomized attack speed value based on range and level.
- [ ] Apply randomized knockback value based on range and level.
- [ ] Apply randomized critical chance value based on range and level (?).
- [ ] Apply randomized sweeping edge damage value based on range and level.

### [Armor]

- [x] Apply crafting table pattern match for armor recipes.
- [ ] Apply armor defence value based on range and level.
- [ ] Apply durability value based on range and level.

### [Other]

- [ ] Apply crafting table pattern match for gunpowder recipe.
- [ ] Apply crafting table pattern match for oil-tipped arrow recipe.
- [ ] Apply crafting table pattern match for salted foods.

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

## [Items]



## [Experience]

- [ ] Player without custom weapon deals base item damage. 
- [ ] Arrow landing has chance of igniting block if flamable.
- [ ] Apply slowness debuff on damage.
- [ ] Can eat large fish / meat multiple times (?)
- [ ] Boil water in cauldron to get salt.
- Possible to use bottles gathered in an ocean biome on a campfire.
- [ ] Toggle PvP on/off for settlements that have been raided or are attacking after being raided. 