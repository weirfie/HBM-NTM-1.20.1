package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.LeadAnvilBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.WoodBurningGeneratorBlockEntity;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.recipe.AnvilBlockRecipe;
import net.StrayBead.hbm_ntm.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class ButtonPressedPacket {
    private final BlockPos pos;

    public ButtonPressedPacket(BlockPos pos) { this.pos = pos; }
    public ButtonPressedPacket(FriendlyByteBuf buf) { this.pos = buf.readBlockPos(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeBlockPos(pos); }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ServerLevel level = player.serverLevel();

            if (level.hasChunkAt(pos)) {
                BlockEntity be = level.getBlockEntity(pos);

                if (be instanceof WoodBurningGeneratorBlockEntity generator) {
                    boolean currentState = generator.data.get(2) != 0;
                    generator.data.set(2, currentState ? 0 : 1);
                }

                else if (be instanceof LeadAnvilBlockEntity) {
                    // Collect player inventory into a container for matching
                    SimpleContainer playerInv = new SimpleContainer(player.getInventory().getContainerSize());
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        playerInv.setItem(i, player.getInventory().getItem(i));
                    }

                    System.out.println("Recipes loaded: " + level.getRecipeManager().getAllRecipesFor(ModRecipes.ANVIL_TYPE.get()).size());

                    Optional<AnvilBlockRecipe> recipeOpt = level.getRecipeManager()
                            .getRecipeFor(ModRecipes.ANVIL_TYPE.get(), playerInv, level);

                    if (recipeOpt.isPresent()) {
                        AnvilBlockRecipe recipe = recipeOpt.get();
                        if (player.experienceLevel >= 1 || player.isCreative()) {

                            // Consume ingredients
                            for (Ingredient ingredient : recipe.getIngredients()) {
                                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                                    ItemStack stack = player.getInventory().getItem(i);
                                    if (!stack.isEmpty() && ingredient.test(stack)) {
                                        stack.shrink(1);
                                        break;
                                    }
                                }
                            }

                            if (!player.isCreative()) player.giveExperienceLevels(-1);

                            ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
                            if (!player.getInventory().add(result)) {
                                player.drop(result, false);
                            }

                            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        } else {
                            player.sendSystemMessage(Component.literal("You need 1 XP Level!"));
                        }
                    } else {
                        player.sendSystemMessage(Component.literal("Missing materials in your inventory for a valid recipe!"));
                    }
                }
            }
        });
        return true;
    }
}