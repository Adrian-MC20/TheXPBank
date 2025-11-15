package ro.maleficent.essentia;

import net.fabricmc.api.ModInitializer;


public class Essentia implements ModInitializer {
	public static final String MOD_ID = "essentia";

	@Override
	public void onInitialize() {
        ModItems.register();
	}
}