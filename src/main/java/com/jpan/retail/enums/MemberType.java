package com.jpan.retail.enums;

public enum MemberType {

    GOLD(3), SILVER(2), COPER(1);

    private final int multiplier;

    MemberType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("MemberType{")
                .append("multiplier=")
                .append(multiplier)
                .append('}')
                .toString();
    }
}
