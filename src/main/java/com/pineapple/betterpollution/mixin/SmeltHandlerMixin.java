package com.pineapple.betterpollution.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.pineapple.betterpollution.BetterPollution;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class SmeltHandlerMixin {

    @Inject(method = "serverTick", at = @At("TAIL"), remap = false)
    private static void onSmeltComplete(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {

        // Slot 2 = output
        ItemStack output = blockEntity.getItem(2);
        if (blockEntity.isLit()) { //blockEntity.isLit()
            BetterPollution.LOGGER.info("hi! im a furnace");
            BetterPollution.LOGGER.info(output.toString());
        }
    }
}
// TODO: GET ACCESS TRANSFORMER WORKING!!!
