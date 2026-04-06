package net.StrayBead.hbm_ntm.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.item.custom.CustomPickaxeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = HBMNTM.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(ModItems.RADIATION_MEASURER.get(), 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 35),
                    new ItemStack(ModItems.URANIUM_ROD.get(), 1),
                    10, 8, 0.02f));

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 60),
                    new ItemStack(ModItems.METAL_DETECTOR.get(), 1),
                    2, 12, 0.035f));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        if (stack.getItem() instanceof CustomPickaxeItem) {
            CompoundTag nbt = stack.getOrCreateTag();
            int mode = nbt.getInt("MiningMode");

            if (mode == 2 && state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            if (x == 0 && y == 0 && z == 0) continue;

                            BlockPos targetPos = pos.offset(x, y, z);
                            BlockState targetState = level.getBlockState(targetPos);

                            if (targetState.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                                if (stack.getDamageValue() >= stack.getMaxDamage() - 1) break;
                                level.destroyBlock(targetPos, true, player);
                                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                        }
                    }
                }
            }

            if (mode == 4 && !player.isCreative()) {
                if (state.getDestroySpeed(level, pos) >= 0) {
                    ItemStack silkDrop = new ItemStack(state.getBlock().asItem());

                    if (!silkDrop.isEmpty()) {
                        Block.popResource(level, pos, silkDrop);

                        event.setCanceled(true);
                        level.removeBlock(pos, false);

                        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        return;
                    }
                }
            }

            if (mode == 5 && !player.isCreative()) {
                List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, null, player, stack);

                for (ItemStack drop : drops) {
                    if (drop.getItem() != state.getBlock().asItem()) {

                        int multiplier = level.random.nextInt(3) + 1;
                        if (level.random.nextFloat() < 0.4f) {
                            multiplier += 1;
                        }

                        drop.setCount(drop.getCount() * multiplier);
                    }

                    Block.popResource(level, pos, drop);
                }

                event.setCanceled(true);
                level.removeBlock(pos, false);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                return;
            }

            if (mode == 6 && !player.isCreative()) {
                List<ItemStack> drops = Block.getDrops(state, (ServerLevel) level, pos, null, player, stack);

                boolean smeltedAny = false;
                for (ItemStack drop : drops) {
                    ItemStack smeltedResult = getSmeltingResult(level, drop);

                    if (!smeltedResult.isEmpty()) {
                        Block.popResource(level, pos, smeltedResult.copy());
                        smeltedAny = true;
                    } else {
                        Block.popResource(level, pos, drop);
                    }
                }

                if (smeltedAny) {
                    event.setCanceled(true);
                    level.removeBlock(pos, false);
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                    return;
                }
            }

            if (mode == 1 && state.is(Tags.Blocks.ORES)) {
                if (!player.isCrouching()) {
                    veinMine(level, pos, player, stack, state.getBlock());
                }
            }
        }
    }

    private static ItemStack getSmeltingResult(Level level, ItemStack input) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(input), level)
                .map(recipe -> recipe.getResultItem(level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    private static void veinMine(Level level, BlockPos pos, Player player, ItemStack stack, Block targetBlock) {
        List<BlockPos> blocksToMine = new ArrayList<>();
        findVein(level, pos, targetBlock, blocksToMine, 64);

        for (BlockPos foundPos : blocksToMine) {
            if (stack.getDamageValue() >= stack.getMaxDamage() - 1) break;

            level.destroyBlock(foundPos, true, player);

            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }

    private static void findVein(Level level, BlockPos pos, Block target, List<BlockPos> found, int max) {
        if (found.size() >= max || found.contains(pos)) return;

        BlockState state = level.getBlockState(pos);
        if (state.is(target)) {
            found.add(pos);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        findVein(level, pos.offset(x, y, z), target, found, max);
                    }
                }
            }
        }
    }
}
