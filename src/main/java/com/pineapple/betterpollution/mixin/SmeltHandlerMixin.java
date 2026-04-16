package com.pineapple.betterpollution.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.pineapple.betterpollution.BetterPollution;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class SmeltHandlerMixin {

    @Inject(method = "burn", at = @At("TAIL"))
    private static void onSmeltComplete(RegistryAccess registryAccess, RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Boolean> cir) {

        // Slot 2 = output
        ItemStack output = furnace.getItem(2);

        if (!output.isEmpty()) {
            BetterPollution.LOGGER.info("hi! im a furnace");
            BetterPollution.LOGGER.info(output.toString());
        }
    }
}
