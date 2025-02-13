package com.kolosov;

import software.amazon.awscdk.App;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        App app = new App();

        new CryptoOrderStack(app, "CryptoOrderStack");
        new DevCryptoOrderStack(app, "DevCryptoOrderStack");
        new BotStateStack(app, "BotStateStack");
        new DevBotStateStack(app, "DevBotStateStack");
        
        app.synth();
    }
}