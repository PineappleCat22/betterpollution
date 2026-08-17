package com.pineapple.betterpollution;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.pineapple.betterpollution.BetterPollutionMain.BLOCKS;
import static com.pineapple.betterpollution.BetterPollutionMain.ITEMS;

@Mod(BetterPollutionMain.MODID)
public class BetterPollutionBlocks {
    public static final DeferredBlock<Block> HEPA_FILTER = BLOCKS.registerSimpleBlock("hepa_filter", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredItem<BlockItem> HEPA_FILTER_ITEM = ITEMS.registerSimpleBlockItem("hepa_filter", HEPA_FILTER);
}
