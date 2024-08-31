package me.power16.powerplugin.ranks;

public enum Rank {
    ADMIN("ADMIN", "§c[ADMIN] ", new String[] { "PowerPlugin.*" , "admin.breakBlock"}),
    MOD("MOD", "§2[MOD] ", new String[] { "permission.moderate", "permission.build" }),
    PLAYER("PLAYER", "§7[Player] ", new String[] { "permission.build" }),
    VIP("VIP", "§a[VIP] ", new String[] { "permission.vip", "permission.build" }),
    HELPER("HELPER", "§b[HELPER] ", new String[] {"permission.helper"}),
    YOUTUBE("YOUTUBE", "§c[§fYOUTUBE§c] ", new String[] {"permission.YOUTUBE"}),
    PIG("PIG", "§d[PIG§b+++§d] ", new String[] {"permission.YOUTUBE"}),
    ALL("ALL","§f[ALL] ", new String[] {"admin.breakBlock","PowerPlugin.*","permission.asdlf", "permission.afsd", "permission.asiodjfo", "permission.asfdoijfiojasd", "permission.asiodjf", "permission.asdf"}),
    OWNER("OWNER", "§c[OWNER] ", new String[] { "PowerPlugin.*", "admin.breakBlock"});

    private final String displayName;
    private final String prefix;
    private final String[] permissions;

    Rank(String displayName, String prefix, String[] permissions) {
        this.displayName = displayName;
        this.prefix = prefix;
        this.permissions = permissions;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        return prefix;
    }

    public String[] getPermissions() {  // Corrected method signature
        return permissions;
    }
}
