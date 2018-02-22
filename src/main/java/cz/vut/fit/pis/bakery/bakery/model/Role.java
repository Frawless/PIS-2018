package cz.vut.fit.pis.bakery.bakery.model;

public enum Role {
    ADMIN("ADMIN"), USER("USER");

    private final String name;

    private Role(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
