package Engine.Entity.Items;

public enum Type {
    KEY,
    AMMO,
    HEAL;

    public static Type getTypeByName(String name) {
        switch (name) {
            case "KEY" -> {
                return KEY;
            }
            case "AMMO" -> {
                return AMMO;
            }
            case "HEAL" -> {
                return HEAL;
            }
            default -> {
                return null;
            }
        }
    }
}
