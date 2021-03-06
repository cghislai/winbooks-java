package be.valuya.jbooks.model;

/**
 *
 * @author Yannick Majoros <yannick@valuya.be>
 */
public enum WbClientSupplierType implements WbValue {

    CLIENT("1"),
    SUPPLIER("2");
    private String value;

    private WbClientSupplierType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
