package ro.maleficent.essentia.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import static ro.maleficent.essentia.Essentia.MOD_ID;

public class ModDataComponents {

    // Stored XP for Essential Vial (per stack)
    public static final ComponentType<Integer> STORED_XP =
            Registry.register(
                    Registries.DATA_COMPONENT_TYPE,
                    Identifier.of(MOD_ID, "stored_xp"),
                    ComponentType.<Integer>builder()
                            .codec(Codecs.NON_NEGATIVE_INT)
                            .build());

    public static void register(){
        // This method just ensures the static initializers run
        // Can add logs here
    }
}
