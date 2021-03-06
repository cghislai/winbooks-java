package be.valuya.jbooks.model;

import java.util.stream.Stream;

/**
 *
 * @author Yannick Majoros <yannick@valuya.be>
 */
public enum WbDocOrderType implements WbValue {

    NUMBER(null),
    VAT("VAT"),
    DEBIT_CENTRAL("998"),
    CREDIT_CENTRAL("999");
    //
    private String value;

    private WbDocOrderType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static WbDocOrderType fromString(String docOrderStr) {
        return Stream.of(WbDocOrderType.values())
                .filter(wbDocOrderType -> wbDocOrderType.value != null && wbDocOrderType.value.equals(docOrderStr))
                .findAny()
                .orElseGet(() -> getDocOrderTypeNumber(docOrderStr));
    }

    private static WbDocOrderType getDocOrderTypeNumber(String docOrderStr) {
        if (docOrderStr.matches("^\\d+$")) {
            return NUMBER;
        }
        throw new IllegalArgumentException();
    }

}
