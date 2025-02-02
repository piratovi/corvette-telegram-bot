package com.kolosov;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.constructs.Construct;

public class CryptoOrderStack extends Stack {
    public CryptoOrderStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CryptoOrderStack(final Construct scope, final String id, final StackProps props) {
        this(scope, id, props, null);
    }

    public CryptoOrderStack(final Construct scope, final String id, final StackProps props, String stage) {
        super(scope, id, props);

        String tableName = "dev".equals(stage) ? "dev-crypto-orders" : "crypto-orders";
        
        Table.Builder.create(this, "CryptoOrderTable")
            .tableName(tableName)
            .partitionKey(Attribute.builder()
                .name("id")
                .type(AttributeType.STRING)
                .build())
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build();
    }
} 