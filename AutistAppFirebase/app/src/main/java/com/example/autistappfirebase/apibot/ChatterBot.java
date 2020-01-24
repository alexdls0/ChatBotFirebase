package com.example.autistappfirebase.apibot;

import java.util.Locale;

public interface ChatterBot {
    ChatterBotSession createSession(Locale... locales);
}