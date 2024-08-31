//package me.power16.powerplugin.commands;

//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;

//import java.util.ArrayList;

//public class MenuGUI {

  //  public Inventory inventory1;

    //public MenuGUI() {
      //  inventory1 = Bukkit.createInventory(null, 27, "Custom Menu");
        //initializeItems();
    //}

    //public void initializeItems() {
      //  ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD);
//        ItemMeta meta1 = item1.getItemMeta();
  //      meta1.setDisplayName(ChatColor.GOLD + "Sword of Power");
    //    meta1.setUnbreakable(true);
      //  ArrayList<String> list = new ArrayList<>();
//        list.add("test");
  //      meta1.setLore(list);
    //    item1.setItemMeta(meta1);
      //  inventory1.setItem(12, item1);

        // Dctr's Space Helmet
        //ItemStack dctrHelmet = new ItemStack(Material.RED_STAINED_GLASS);
//        ItemMeta dctrHelmetMeta = dctrHelmet.getItemMeta();
  //      dctrHelmetMeta.setDisplayName("§cDctr's Space Helmet");
    //    ArrayList<String> loreDctrList = new ArrayList<>();
      //  loreDctrList.add("§7§oA rare space helmet");
        //loreDctrList.add("§7§ofrom shards of moon glass.");
//        loreDctrList.add(" ");
  //      loreDctrList.add("§7To: §c[OWNER] Power16");
    //    loreDctrList.add("§7From: §c[OWNER] Power16");
      //  loreDctrList.add(" ");
        //loreDctrList.add("§8Edition #1");
//        loreDctrList.add("§8August 13, 2024");
  //      loreDctrList.add(" ");
    //    loreDctrList.add("§8This item can be reforged!");
      //  loreDctrList.add(" ");
        //loreDctrList.add("§c§lSPECIAL§c §c§lHELMET");
//        dctrHelmetMeta.setLore(loreDctrList);
  //      dctrHelmet.setItemMeta(dctrHelmetMeta);
    //    inventory1.setItem(13, dctrHelmet);

//        ItemStack item2 = new ItemStack(Material.GOLDEN_APPLE);
  //      ItemMeta meta2 = item2.getItemMeta();
    //    meta2.setDisplayName("Golden Apple");
      //  item2.setItemMeta(meta2);
        //inventory1.setItem(14, item2);

//        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
  //      ItemMeta meta3 = fill.getItemMeta();
    //    meta3.setDisplayName(" ");
      //  fill.setItemMeta(meta3);
        //for (int i = 0; i < inventory1.getSize(); i++) {
          //  if (inventory1.getItem(i) == null || inventory1.getItem(i).getType() == Material.AIR) {
            //    inventory1.setItem(i, fill);
            //}
        //}
    //}

   // public Inventory getInventory() {
     //   return inventory1;
    //}

   // public ItemStack getItem(int slot) {
     //   return inventory1.getItem(slot);
  //  }
//}
