package com.guildchat.formatter;

public enum Language {
    ENGLISH("English", "en"),
    FRENCH("Français", "fr");

    private final String displayName;
    private final String code;

    Language(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }

    public static Language fromString(String input) {
        if (input == null) return ENGLISH; // Default
        String normalized = input.toLowerCase().trim();
        
        for (Language lang : values()) {
            if (lang.name().toLowerCase().equals(normalized) 
                || lang.code.equals(normalized)
                || lang.displayName.toLowerCase().equals(normalized)) {
                return lang;
            }
        }
        
        // Partial match
        if (normalized.startsWith("en") || normalized.startsWith("ang")) {
            return ENGLISH;
        }
        if (normalized.startsWith("fr")) {
            return FRENCH;
        }
        
        return null;
    }
}
