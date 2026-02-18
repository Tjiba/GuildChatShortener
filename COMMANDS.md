# GuildChat Shortener - Commands Reference

## 🌍 Language / Langue

### Change Language / Changer la langue
```
/bridgelanguage <language>
```
- English: `/bridgelanguage English` or `/bridgelanguage en`
- Français: `/bridgelanguage Français` or `/bridgelanguage fr`

---

## 🎮 Main Commands / Commandes principales

### Bridge Setup / Configuration du bridge
```
/bridgesetup <mcName> <alias>
```
**English**: Define the Discord bot's Minecraft name and display alias
**Français**: Définir le nom Minecraft du bot Discord et l'alias d'affichage

**Example / Exemple**:
```
/bridgesetup DiscordBot DC
```

---

### Bridge Status / Statut du bridge
```
/bridge status
```
**English**: Display current configuration (bot name, alias, colors, mode)
**Français**: Affiche la configuration actuelle (nom du bot, alias, couleurs, mode)

---

### Bridge Reset / Réinitialisation du bridge
```
/bridge reset
```
**English**: Reset all settings to default (automatic detection, default colors)
**Français**: Réinitialise tous les paramètres par défaut (détection auto, couleurs par défaut)

---

### Bridge Help / Aide du bridge
```
/bridge help
```
**English**: Display quick help for all commands
**Français**: Affiche l'aide rapide pour toutes les commandes

---

## 🏷️ Alias Management / Gestion de l'alias

### Change Alias / Changer l'alias
```
/bridgename <alias>
```
**English**: Change the bridge display name
**Français**: Changer le nom d'affichage du bridge

**Example / Exemple**:
```
/bridgename Discord
/bridgename DC
/bridgename Bridge
```

---

### Reset Alias / Réinitialiser l'alias
```
/bridgename reset
```
**English**: Reset alias to "Bridge"
**Français**: Réinitialiser l'alias à "Bridge"

---

## 🎨 Color Customization / Personnalisation des couleurs

### Bridge Alias Color / Couleur de l'alias du bridge
```
/bridgecolor <color>
/bc <color>             (short alias / alias court)
```
**English**: Change the bridge alias color
**Français**: Changer la couleur de l'alias du bridge

**Examples / Exemples**:
```
/bc yellow
/bc jaune
/bc &e
/bridgecolor cyan
```

---

### Bridge Alias Color Reset / Réinitialiser la couleur de l'alias
```
/bridgecolor reset
/bc reset
```
**English**: Reset alias color to cyan (&b)
**Français**: Réinitialiser la couleur de l'alias à cyan (&b)

---

### Player Name Color / Couleur du pseudo joueur
```
/bridgeplayercolor <color>
/bpc <color>            (short alias / alias court)
```
**English**: Change the Discord player name color
**Français**: Changer la couleur du pseudo Discord

**Examples / Exemples**:
```
/bpc green
/bpc vert
/bpc &a
/bridgeplayercolor red
```

---

### Player Name Color Reset / Réinitialiser la couleur du pseudo
```
/bridgeplayercolor reset
/bpc reset
```
**English**: Reset player name color to dark cyan (&3)
**Français**: Réinitialiser la couleur du pseudo à cyan foncé (&3)

---

## 🌐 Global Mode / Mode global

### Enable Global Formatting / Activer le formatage global
```
/bridgeactivateall
```
**English**: Format ALL guild messages (not just bridge messages)
**Français**: Formater TOUS les messages de guilde (pas seulement le bridge)

---

### Disable Global Formatting / Désactiver le formatage global
```
/bridgeactivateall off
```
**English**: Format only bridge messages (default)
**Français**: Formater uniquement les messages bridge (par défaut)

---

## 🎲 Random Colors / Couleurs aléatoires

### Enable Random Colors / Activer les couleurs aléatoires
```
/bridge random
/bridge random on
```
**English**: Enable random colors for Discord player names
**Français**: Activer les couleurs aléatoires pour les pseudos Discord

---

### Disable Random Colors / Désactiver les couleurs aléatoires
```
/bridge random off
```
**English**: Disable random colors and use configured color
**Français**: Désactiver les couleurs aléatoires et utiliser la couleur configurée

---

## 🔄 Update Check / Vérification de mise à jour

### Check for Updates / Vérifier les mises à jour
```
/bridge update
```
**English**: Manually check for mod updates from GitHub
**Français**: Vérifier manuellement les mises à jour du mod depuis GitHub

**Automatic check**: The mod automatically checks for updates when you join a server for the first time.
**Vérification automatique**: Le mod vérifie automatiquement les mises à jour lors de votre première connexion à un serveur.

---

## 🎨 Available Colors / Couleurs disponibles

### Color Names / Noms de couleurs

| English | Français | Code | Hex |
|---------|----------|------|-----|
| black | noir | &0 | #000000 |
| dark blue | bleu foncé | &1 | #0000AA |
| dark green | vert foncé | &2 | #00AA00 |
| dark cyan | cyan foncé | &3 | #00AAAA |
| dark red | rouge foncé | &4 | #AA0000 |
| dark purple | violet foncé | &5 | #AA00AA |
| gold | or | &6 | #FFAA00 |
| gray | gris | &7 | #AAAAAA |
| dark gray | gris foncé | &8 | #555555 |
| blue | bleu | &9 | #5555FF |
| green | vert | &a | #55FF55 |
| cyan | cyan | &b | #55FFFF |
| red | rouge | &c | #FF5555 |
| light purple | rose clair | &d | #FF55FF |
| yellow | jaune | &e | #FFFF55 |
| white | blanc | &f | #FFFFFF |

### Accepted Formats / Formats acceptés

1. **English name**: `black`, `dark_blue`, `green`, etc.
2. **French name**: `noir`, `bleu_foncé`, `vert`, etc.
3. **Minecraft color code**: `&0`, `&1`, `&a`, etc.
4. **Section symbol**: `§0`, `§1`, `§a`, etc.

---

## 💡 Tips / Astuces

### English
- Commands support **both English and French** color names regardless of your language setting
- Use `/bc` instead of `/bridgecolor` for faster color changes
- Use `/bpc` instead of `/bridgeplayercolor` for faster player color changes
- The mod automatically detects bridge messages - you only need `/bridgesetup` if detection fails
- All settings are saved automatically and persist between game sessions

### Français
- Les commandes supportent les noms de couleurs **en anglais et en français** quelle que soit votre langue
- Utilisez `/bc` au lieu de `/bridgecolor` pour changer les couleurs plus rapidement
- Utilisez `/bpc` au lieu de `/bridgeplayercolor` pour changer la couleur du pseudo plus rapidement
- Le mod détecte automatiquement les messages bridge - vous n'avez besoin de `/bridgesetup` que si la détection échoue
- Tous les paramètres sont sauvegardés automatiquement et persistent entre les sessions de jeu

---

## 📄 Configuration File / Fichier de configuration

**Location / Emplacement**: `.minecraft/config/guildchat-formatter.json`

**Example / Exemple**:
```json
{
  "botMCName": null,
  "botAlias": "Bridge",
  "botAliasColor": "b",
  "discordNameColor": "3",
  "formatAllGuild": false,
  "language": "french"
}
```

---

**Made with ❤️ by [Tjiba](https://github.com/Tjiba) (MeteoFrance in-game)**
