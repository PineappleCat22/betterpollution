package com.pineapple.betterpollution.mixin;

import com.pineapple.betterpollution.BetterPollutionCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class SmeltHandlerMixin {

    @Inject(method = "serverTick", at = @At("TAIL"), remap = false)
    private static void onSmeltComplete(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {

        ServerLevel serverLevel = null;
        if (!level.isClientSide()) { //make sure this runs on the server
            serverLevel = (ServerLevel) level;
        }
        else {
            return;
        }

        ItemStack output = blockEntity.getItem(2);
        if (blockEntity.isLit()) { //blockEntity.isLit()
            BetterPollutionCommon.addPollutionAtPos(pos, serverLevel, 1);
            // eqv. to adding 0.001 ug/m^3 pm2.5
            // this assumes that the fuel is 1kg of wood expended over 1600 ticks
            // source: https://ustravelersleague.com/how-much-pollution-does-a-campfire.html
        }
    }
}
