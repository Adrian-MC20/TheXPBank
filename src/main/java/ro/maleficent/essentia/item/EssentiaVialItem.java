package ro.maleficent.essentia.item;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import ro.maleficent.essentia.registry.ModDataComponents;

import java.util.function.Consumer;

public class EssentiaVialItem extends Item {

    public static final int CAPACITY = 350;

    public EssentiaVialItem(Settings settings){
        super(settings);
    }

    // Helper: get stored XP from the stack
    public static int getStoredXp(ItemStack stack){
        Integer value = stack.getOrDefault(ModDataComponents.STORED_XP, 0);
        return Math.max(0, Math.min(CAPACITY, value));
    }

    // Helper: set stored XP and update visual damage
    public static void setStoredXp(ItemStack stack, int amount){
        int clamped = Math.max(0, Math.min(CAPACITY, amount));
        stack.set(ModDataComponents.STORED_XP, clamped);

        // Damage grows with fill level
        int damage = clamped;
        stack.setDamage(damage);
    }

    // Helper: compute how much space is left
    public static int getRemainingCapacity(ItemStack stack){
        return CAPACITY - getStoredXp(stack);
    }

    // Helper: rough total XP of player (simplified)
    public static int getPlayerTotalXp(PlayerEntity player){
        return player.totalExperience;
    }

    private static void addXpToPlayer(PlayerEntity player, int amount){
        if (amount <= 0) return;
        player.addExperience(amount);
    }

    public static void removeXpFromPlayer(PlayerEntity player, int amount){
        if (amount <= 0) return;
        player.addExperience(-amount);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand){
        ItemStack stack = player.getStackInHand(hand);

        // Don't do logic twice on the client
        if (world.isClient()){
            return ActionResult.SUCCESS;
        }

        int stored = getStoredXp(stack);

        if (player.isSneaking()){
            // Deposit: player -> vial
            int remaining = getRemainingCapacity(stack);
            if (remaining > 0){
                int playerXp = getPlayerTotalXp(player);
                int toDeposit = Math.min(remaining, playerXp);

                if (toDeposit > 0) {
                    removeXpFromPlayer(player, toDeposit);
                    setStoredXp(stack, stored + toDeposit);
                }
            }
            return ActionResult.SUCCESS;
        } else {
            // Withdraw: vial -> player
            if (stored > 0){
                addXpToPlayer(player, stored);
                setStoredXp(stack, 0);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        }
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack){
        // Hide vanilla durability bar; we use damage only for model selection
        return false;
    }

    @SuppressWarnings("deprecated")
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        int stored = getStoredXp(stack);
        textConsumer.accept(Text.literal("Stored: " + stored + " / " + CAPACITY + " XP"));
    }
}
