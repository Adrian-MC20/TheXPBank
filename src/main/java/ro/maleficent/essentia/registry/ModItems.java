package ro.maleficent.essentia.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.item.Items;
import ro.maleficent.essentia.item.EssentiaVialItem;

public class ModItems {
    public static final RegistryKey<Item> ESSENTIAL_VIAL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("essentia","essentia_vial"));

    public static final Item ESSENTIA_VIAL = Items.register(ESSENTIAL_VIAL_KEY, EssentiaVialItem::new, new Item.Settings().maxCount(1));

    public static void register(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->{
            entries.add(ESSENTIA_VIAL);
        });
    }
}
