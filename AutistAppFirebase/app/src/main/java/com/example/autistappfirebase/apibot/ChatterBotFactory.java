package com.example.autistappfirebase.apibot;

public class ChatterBotFactory {

    public ChatterBot create(ChatterBotType type) throws Exception {
        return create(type, null);
    }

    public ChatterBot create(ChatterBotType type, Object arg) throws Exception {
        switch (type) {
            case CLEVERBOT:
                return new Cleverbot("http://www.cleverbot.com", "http://www.cleverbot.com/webservicemin?uc=777&botapi=chatterbotapi", 35);
            case JABBERWACKY:
                return new Cleverbot("http://jabberwacky.com", "http://jabberwacky.com/webservicemin?botapi=chatterbotapi", 29);
            case PANDORABOTS:
                if (arg == null) {
                    throw new Exception("PANDORABOTS needs a botid arg");
                }
                return new Pandorabots(arg.toString());
        }
        return null;
    }
}
