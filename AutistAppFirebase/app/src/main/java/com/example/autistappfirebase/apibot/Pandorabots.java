package com.example.autistappfirebase.apibot;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

class Pandorabots implements ChatterBot {
    private final String botid;

    public Pandorabots(String botid) {
        this.botid = botid;
    }

    @Override
    public ChatterBotSession createSession(Locale... locales) {
        return new Session();
    }

    private class Session implements ChatterBotSession {
        private final Map<String, String> vars;

        public Session() {
            vars = new LinkedHashMap<String, String>();
            vars.put("botid", botid);
            vars.put("custid", UUID.randomUUID().toString());
        }

        public ChatterBotThought think(ChatterBotThought thought) throws Exception {
            vars.put("input", thought.getText());
            String response = Utils.request("https://www.pandorabots.com/pandora/talk-xml", null, null, vars);
            ChatterBotThought responseThought = new ChatterBotThought();
            responseThought.setText(Utils.xPathSearch(response, "//result/that/text()"));
            return responseThought;
        }

        public String think(String text) throws Exception {
            ChatterBotThought thought = new ChatterBotThought();
            thought.setText(text);
            return think(thought).getText();
        }
    }
}