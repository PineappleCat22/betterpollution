package com.pineapple.betterpollution;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.Arrays;
import java.util.function.Supplier;

/*
* TODO: clean up this file.
*  code in here should be related to setup.
*  unrelated example stuff throw in the TestClassPleaseDelete
* TODO: revisit file structure
*  */
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BetterPollutionMain.MODID)
public class BetterPollutionMain {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "betterpollution";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BetterPollutionMain.MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BetterPollutionMain.MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "betterpollution" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BetterPollutionMain.MODID);
    //temp placement: this should maybe go somewhere else
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BetterPollutionMain.MODID);
    // for counting ticks
    private static long tickCounter = 0;
    private static int pollutionTickInterval;

    //todo: figure out how this actually works
    public static final Supplier<AttachmentType<int[]>> POLLUTION_DATA = ATTACHMENT_TYPES.register(
            "pollution_data",
            () -> AttachmentType.builder(() -> new int[32])
                    .serialize(Codec.INT.listOf().xmap(
                            list -> list.stream().mapToInt(i -> i).toArray(), // From List to int[]
                            array -> Arrays.stream(array).boxed().toList()    // From int[] to List
                    ))
                    .build()
    );



    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public BetterPollutionMain(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        TestClassPleaseDelete.testBLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        TestClassPleaseDelete.testITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        TestClassPleaseDelete.testCREATIVE_MODE_TABS.register(modEventBus);
        // okay now the real stuff
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        ATTACHMENT_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (BetterPollution) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, BetterPollutionConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        pollutionTickInterval = BetterPollutionConfig.POLLUTION_TICK_INTERVAL.getAsInt();

        /*if (BetterPollutionConfig.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", BetterPollutionConfig.MAGIC_NUMBER_INTRODUCTION.get(), BetterPollutionConfig.MAGIC_NUMBER.getAsInt());

        BetterPollutionConfig.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));*/
    }

    // idk whats going on but i cant move this and idk why
    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(TestClassPleaseDelete.EXAMPLE_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        // Call a method to register your command
        BetterPollutionCommands.register(event.getDispatcher());
    }

    /*
    @SubscribeEvent
    public void onSmelt(PlayerEvent.ItemSmeltedEvent event) {
        LOGGER.info("smelted a " + event.getSmelting());
    }*/ // very first method for handling smelting (sad!)

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        tickCounter++;
        // due to numbers, this will crash the server if the play time exceeds 29 billion years.
        // if this has happened to you, go outside.
        if (tickCounter % (pollutionTickInterval*20) == 0) {
            doPollutionTick();
        }
    }

    private void doPollutionTick () {
        LOGGER.info("pollution tick");
        // if pollution near player exceeds uhhh some number, increase exposure
        // else lower exposure
        // more pollution = more exposure increase
        // if pollution can go to a higher cubic chunk, make it be so.
        // if it cant, subtract 1/3rd and add it to surrounding chunks.
        // same thing with heavy pollution, except heavy pollution doesnt ever go up.
        // if exposure is at certain levels, apply certain effects.
        // after a certain point, pollution should start translating into heavy pollution
        // biome heat shift
        // how hard is it to change the water pallette?
        // slower crop growth at severe levels of pollution? todo: research pollution effect on agri
        // if possible chunk based changes should be executed if the chunk is loaded, to avoid lag???
        // the pollutiondata number should be interpreted as POLLUTIONDATA/1000 = ug/m^3 pm2.5 so we have three decimals to work with
        // also assuming (for the furnace) that 1 ppm co2 = 1 ug/m^3 pm2.5 but it varies
    }
}
