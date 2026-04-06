package net.StrayBead.hbm_ntm.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGameRules {
    public static final GameRules.Key<GameRules.IntegerValue> RBMK_REACTOR_HEIGHT = GameRules.register("rbmkReactorHeight", GameRules.Category.PLAYER, GameRules.IntegerValue.create(4));
}
