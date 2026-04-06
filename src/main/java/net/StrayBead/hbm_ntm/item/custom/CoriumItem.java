package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class CoriumItem extends BucketItem {
    public CoriumItem() {
        super(ModFluids.CORIUM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
    }
}
