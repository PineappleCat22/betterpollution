package com.pineapple.betterpollution;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BetterPollutionMain.MODID) // so, extra classes need to add this to be loaded. client side code needs to be loaded a different way.
public class TestClassPleaseDelete {
    //example code

    // Create a Deferred Register to hold Blocks which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister.Blocks testBLOCKS = DeferredRegister.createBlocks(BetterPollutionMain.MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister.Items testITEMS = DeferredRegister.createItems(BetterPollutionMain.MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister<CreativeModeTab> testCREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterPollutionMain.MODID);

    // Creates a new Block with the id "betterpollution:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = testBLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "betterpollution:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = testITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "betterpollution:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = testITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "betterpollution:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = testCREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.betterpollution")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

}
