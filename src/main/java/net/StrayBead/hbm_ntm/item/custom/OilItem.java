package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class OilItem extends BucketItem {
    public OilItem() {
        super(ModFluids.CRUDE_OIL, new Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
    }
}
