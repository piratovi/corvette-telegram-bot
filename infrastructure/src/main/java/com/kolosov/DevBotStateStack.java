package com.kolosov;

import software.constructs.Construct;

public class DevBotStateStack extends BotStateStack {
    public DevBotStateStack(final Construct scope, final String id) {
        super(scope, id, null, "dev");
    }
} 