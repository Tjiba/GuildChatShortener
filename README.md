<div align="center">

# 🎮 GuildChat Shortener

**Fabric mod to shorten and customize Discord bridge and Normal messages in Hypixel guilds**

![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+) **Proudly made in France** ![France](https://img.shields.io/badge/Made%20in-France-blue?style=flat&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8cmVjdCB3aWR0aD0iOTAwIiBoZWlnaHQ9IjYwMCIgZmlsbD0iI0VEMjkzOSIvPgogIDxyZWN0IHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIiBmaWxsPSIjRkZGIi8+CiAgPHJlY3Qgd2lkdGg9IjMwMCIgaGVpZ2h0PSI2MDAiIGZpbGw9IiMwMDIzOTUiLz4KPC9zdmc+)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21-brightgreen.svg)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Fabric-0.18.4-orange.svg)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)

**English** | [Français](README.fr.md)

<img width="265" height="61" alt="image" src="https://github.com/user-attachments/assets/39af7b73-32f2-4ae7-962c-0e8b86914a77" />

</div>

---

## 📖 Description

**GuildChat Shortener** is a Fabric client-side mod that transforms long Discord bridge messages and regular guild messages in your Hypixel guild into short and elegant messages. No more cluttered chat - customize the display with colors and aliases to your liking!

### ✨ Key Features

- 🤖 **Automatic Detection** - The mod automatically detects Discord bridge messages
- 🌍 **Multilingual Support** - Full support for English and French languages
- 🎨 **Color Customization** - Change the colors of the bridge alias and player names
- 🏷️ **Custom Aliases** - Replace the bot name with a short alias (e.g., "Discord", "Bridge", "DC")
- ⚙️ **Flexible Configuration** - Intuitive in-game commands to adjust settings
- 🔄 **Smart Detection** - Automatically recognizes Discord bridge messages
- 💾 **Automatic Saving** - Your configuration persists between sessions
- 🌐 **Global Mode** - Enable formatting for all guild messages

---

## 📥 Installation

**Requirements**:
- Minecraft 1.21+
- Fabric Loader 0.18.0+
- Fabric API (required)
- ModMenu (optional - for graphical config)
- Cloth Config (optional - for graphical config)

**Setup**:
1. Download from [Releases](https://github.com/Tjiba/GuildChatShortener/releases)
2. Place in your `mods/` folder
3. Launch Minecraft with Fabric profile

---

## 🎯 Usage

**The mod automatically detects Discord bridge messages!** Configuration is optional but can be customized with commands or ModMenu.

### Essential Commands

```
/bridge help              # Show help
/bridgename <alias>       # Change alias (ex: /bridgename DC)
/bridgecolor <color>      # Change alias color (ex: /bridgecolor red)
/bridgeplayercolor <color> # Change player name color
/bridgelanguage <lang>    # Change language (english/french)
```

**Examples**:
```
/bridgename Bridge        # Set alias to "Bridge"
/bridgecolor yellow       # Make alias yellow
/bridgelanguage french    # Switch to French
```

---

## 🎨 Available Colors

Use color names in commands: `black`, `dark_blue`, `dark_green`, `dark_aqua`, `dark_red`, `dark_purple`, `gold`, `gray`, `dark_gray`, `blue`, `green`, `aqua`, `red`, `light_purple`, `yellow`, `white`

**Examples**: `/bc red`, `/bpc green`, `/bridgecolor aqua`

---

## 🛠️ Development

### Build the project

```cmd
gradlew.bat build
```

The compiled JAR file can be found in `build/libs/GuildChatShortener-1.0.0.jar`.

### Project Structure

```
src/main/
├── java/com/guildchat/formatter/
│   ├── GuildChatMod.java          # Main entry point
│   ├── BridgeConfig.java          # Configuration management
│   └── mixin/
│       └── ChatHudMixin.java      # Injection to format messages
└── resources/
    ├── fabric.mod.json            # Mod metadata
    └── guildchat-formatter.mixins.json
```

---

## 📝 Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history.

---

## 🤝 Contributing

Contributions are welcome! Feel free to:
- 🐛 Report bugs
- 💡 Suggest new features
- 🔧 Submit pull requests

---

## 📜 License

This project is licensed under the **MIT** License. See the [LICENSE](./LICENSE) file for details.

---

## 👤 Author

Developed with ❤️ by **[Tjiba](https://github.com/Tjiba)** (MeteoFrance in-game)

---

<div align="center">

### ⭐ If this mod is useful to you, feel free to give it a star!

[Download](https://github.com/Tjiba/GuildChatShortener/releases) • [Report a Bug](https://github.com/Tjiba/GuildChatShortener/issues) • [Request a Feature](https://github.com/Tjiba/GuildChatShortener/issues)

</div>
