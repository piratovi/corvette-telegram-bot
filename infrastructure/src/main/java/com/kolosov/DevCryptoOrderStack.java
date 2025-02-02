package com.kolosov;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class DevCryptoOrderStack extends CryptoOrderStack {
    public DevCryptoOrderStack(final Construct scope, final String id) {
        super(scope, id, null, "dev");
    }
} 