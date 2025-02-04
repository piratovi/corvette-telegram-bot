package com.kolosov;

import software.constructs.Construct;

public class DevCryptoOrderStack extends CryptoOrderStack {
    public DevCryptoOrderStack(final Construct scope, final String id) {
        super(scope, id, null, "dev");
    }
} 