package ro.maleficent.essentia;

import net.fabricmc.api.ModInitializer;
import ro.maleficent.essentia.registry.ModDataComponents;
import ro.maleficent.essentia.registry.ModItems;


public class Essentia implements ModInitializer {
	public static final String MOD_ID = "essentia";

	@Override
	public void onInitialize() {
        ModDataComponents.register();
        ModItems.register();
	}
}