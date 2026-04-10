package com.pineapple.betterpollution.mixin;

package com.yourname.yourmod.mixin;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public class SmeltHandlerMixin {

    @Inject(method = "burn", at = @At("TAIL"))
    private void onSmeltComplete(CallbackInfo ci) {
        AbstractFurnaceBlockEntity furnace = (AbstractFurnaceBlockEntity)(Object)this;

        // Slot 2 = output
        ItemStack output = furnace.getItem(2);

        if (!output.isEmpty()) {
            // 🔥 Your logic here
            System.out.println("Smelting completed: " + output);
        }
    }
}
