# 🧪 Testing Guide - Multilingual System

## Quick Test Commands

### Test Language Switching

1. **Start in English (default)**
   ```
   /bridge help
   ```
   Expected: Messages in English
   
2. **Switch to French**
   ```
   /bridgelanguage Français
   ```
   Expected: "§aLangue définie sur: §eFrançais"
   
3. **Test French messages**
   ```
   /bridge help
   ```
   Expected: Messages in French
   
4. **Switch back to English**
   ```
   /bridgelanguage en
   ```
   Expected: "§aLanguage set to: §eEnglish"

---

## Test All Commands in Both Languages

### English Mode (`/bridgelanguage English`)

```
/bridge status
→ Expected: "§7Bot: §eauto §7| Alias: §bBridge §7| Colors: §bcyan §7/ §3dark cyan §7| Mode: §ebridge"

/bridge reset
→ Expected: "§aBridge bot reset. Automatic detection enabled."

/bridgesetup TestBot DC
→ Expected: "§aBridge bot defined: §eTestBot §7→ §bDC"

/bridgename Discord
→ Expected: "§aBridge name: §bDiscord"

/bridgename reset
→ Expected: "§aBridge name reset: §bBridge"

/bridgecolor yellow
→ Expected: "§aBridge color: §eyellow §7(&e)"

/bridgecolor reset
→ Expected: "§aBridge color reset: §bcyan §7(&b)"

/bridgeplayercolor green
→ Expected: "§aPlayer color: §agreen §7(&a)"

/bridgeplayercolor reset
→ Expected: "§aPlayer color reset: §3dark cyan §7(&3)"

/bridgeactivateall
→ Expected: "§aGuild formatting enabled for all messages."

/bridgeactivateall off
→ Expected: "§cGuild formatting disabled (bridge only)."
```

### French Mode (`/bridgelanguage Français`)

```
/bridge status
→ Expected: "§7Bot: §eauto §7| Alias: §bBridge §7| Couleurs: §bcyan §7/ §3cyan foncé §7| Mode: §ebridge"

/bridge reset
→ Expected: "§aBot bridge réinitialisé. Détection automatique activée."

/bridgesetup TestBot DC
→ Expected: "§aBot bridge défini : §eTestBot §7→ §bDC"

/bridgename Discord
→ Expected: "§aNom du bridge: §bDiscord"

/bridgename reset
→ Expected: "§aNom du bridge réinitialisé: §bBridge"

/bridgecolor jaune
→ Expected: "§aCouleur du bridge: §ejaune §7(&e)"

/bridgecolor reset
→ Expected: "§aCouleur du bridge réinitialisée: §bcyan §7(&b)"

/bridgeplayercolor vert
→ Expected: "§aCouleur du pseudo: §avert §7(&a)"

/bridgeplayercolor reset
→ Expected: "§aCouleur du pseudo réinitialisée: §3cyan foncé §7(&3)"

/bridgeactivateall
→ Expected: "§aFormatage guilde activé pour tous les messages."

/bridgeactivateall off
→ Expected: "§cFormatage guilde désactivé (bridge uniquement)."
```

---

## Test Color Names (Both Languages)

### English Mode
```
/bc black → "§aBridge color: §0black §7(&0)"
/bc dark_blue → "§aBridge color: §1dark blue §7(&1)"
/bc dark_green → "§aBridge color: §2dark green §7(&2)"
/bc dark_aqua → "§aBridge color: §3dark cyan §7(&3)"
/bc gold → "§aBridge color: §6gold §7(&6)"
/bc yellow → "§aBridge color: §eyellow §7(&e)"
/bc white → "§aBridge color: §fwhite §7(&f)"
```

### French Mode
```
/bc noir → "§aCouleur du bridge: §0noir §7(&0)"
/bc bleu_fonce → "§aCouleur du bridge: §1bleu foncé §7(&1)"
/bc vert_fonce → "§aCouleur du bridge: §2vert foncé §7(&2)"
/bc cyan_fonce → "§aCouleur du bridge: §3cyan foncé §7(&3)"
/bc or → "§aCouleur du bridge: §6or §7(&6)"
/bc jaune → "§aCouleur du bridge: §ejaune §7(&e)"
/bc blanc → "§aCouleur du bridge: §fblanc §7(&f)"
```

---

## Test Color Input Flexibility

Both English and French color names should work in both language modes:

### In English Mode
```
/bc rouge → Should work (French color name)
/bc red → Should work (English color name)
/bc &c → Should work (color code)
```

### In French Mode
```
/bc red → Should work (English color name)
/bc rouge → Should work (French color name)
/bc &c → Should work (color code)
```

---

## Test Error Messages

### Invalid Color
```
English Mode:
/bc invalid_color
→ Expected: "§cUnknown color: §finvalid_color §7(values: black (&0), dark blue (&1), ...)"

French Mode:
/bc couleur_invalide
→ Expected: "§cCouleur inconnue: §fcouleur_invalide §7(valeurs: noir (&0), bleu foncé (&1), ...)"
```

### Invalid Language
```
/bridgelanguage spanish
→ Expected (if in French): "§cLangue inconnue: §fspanish §7(disponibles: English, Français)"
→ Expected (if in English): "§cUnknown language: §fspanish §7(available: English, Français)"
```

---

## Test Configuration Persistence

1. Set language to French
   ```
   /bridgelanguage Français
   ```

2. Exit Minecraft

3. Check config file: `.minecraft/config/guildchat-formatter.json`
   ```json
   {
     ...
     "language": "french"
   }
   ```

4. Restart Minecraft

5. Test command
   ```
   /bridge help
   ```
   Expected: Messages still in French

---

## Test Help Command

### English
```
/bridge help
```
Expected output:
```
§e/bridgesetup <mcName> <alias> §7- define bot and alias
§e/bridge status §7- show configuration
§e/bridge reset §7- reset everything
§e/bridgename <alias> §7- change alias
§e/bridgecolor <color> §7- bridge color (alias: /bc)
§e/bridgeplayercolor <color> §7- player name color (alias: /bpc)
§e/bridgeactivateall [off] §7- format all guild messages
§e/bridgelanguage <language> §7- change language (English/Français)
```

### French
```
/bridge help
```
Expected output:
```
§e/bridgesetup <nomMC> <alias> §7- définir le bot et l'alias
§e/bridge status §7- afficher la config
§e/bridge reset §7- réinitialiser tout
§e/bridgename <alias> §7- changer l'alias
§e/bridgecolor <couleur> §7- couleur du bridge (alias: /bc)
§e/bridgeplayercolor <couleur> §7- couleur du pseudo (alias: /bpc)
§e/bridgeactivateall [off] §7- activer le formatage pour toute la guilde
§e/bridgelanguage <langue> §7- changer la langue (English/Français)
```

---

## Test Language Input Variations

All these should work:
```
/bridgelanguage English
/bridgelanguage english
/bridgelanguage ENGLISH
/bridgelanguage en
/bridgelanguage EN

/bridgelanguage Français
/bridgelanguage français
/bridgelanguage francais
/bridgelanguage FRANCAIS
/bridgelanguage fr
/bridgelanguage FR
```

---

## Expected Log Output

When starting the mod:

**English Mode (default):**
```
[INFO] Guild Chat Formatter loaded!
```

**French Mode:**
```
[INFO] Guild Chat Formatter chargé !
```

---

## ✅ Full Test Checklist

- [ ] Language switches correctly between English and French
- [ ] All commands display messages in current language
- [ ] Color names appear in current language
- [ ] Color input works with both English and French names
- [ ] Help command shows all commands in current language
- [ ] Error messages appear in current language
- [ ] Language preference saves to config file
- [ ] Language persists after restart
- [ ] Status command shows mode in current language
- [ ] All variations of language input work
- [ ] Mod loads with correct language message in log

---

## 🐛 Known Issues to Watch For

1. **Config file corruption**: If JSON is malformed, language should default to French
2. **Case sensitivity**: All language inputs should be case-insensitive
3. **Special characters**: French accents (é, è, à) should display correctly
4. **Color code parsing**: Both & and § symbols should work

---

## 📊 Performance Notes

- Language switching is instant (no reload required)
- Message lookup is O(1) using HashMap
- No performance impact on chat rendering
- Config file updates are async

---

**Test Environment:**
- Minecraft: 1.21.10
- Fabric Loader: 0.18.4
- Fabric API: 0.138.4+1.21.10
- Java: 21
