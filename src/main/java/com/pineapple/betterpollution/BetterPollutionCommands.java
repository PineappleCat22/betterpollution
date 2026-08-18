package com.pineapple.betterpollution;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.common.Mod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.Arrays;

/*
TODO: make commands more responsive ie: if command fails, the command should respond with why.
if you pass a negative to my methods, i will kill you.
 */

@Mod(BetterPollutionMain.MODID)
public class BetterPollutionCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal(BetterPollutionMain.MODID)
                .then(Commands.literal("getPollution")
                        .executes(ctx -> getPollution(ctx))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(ctx -> getPollutionWithPos(
                                        ctx,
                                        BlockPosArgument.getBlockPos(ctx, "pos"))
                                )
                        )
                )
                .then(Commands.literal("getSectionY")
                        .executes(ctx -> getSection(ctx))
                )
                // this structure is a little questionable
                .then(Commands.literal("setPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> setPollution(ctx, IntegerArgumentType.getInteger(ctx, "value")))
                                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                                .executes(ctx -> setPollutionWithPos(
                                                        ctx,
                                                        BlockPosArgument.getBlockPos(ctx, "pos"),
                                                        IntegerArgumentType.getInteger(ctx, "value"))
                                                )
                                        )
                        )
                )
                .then(Commands.literal("addPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> addPollution(ctx, IntegerArgumentType.getInteger(ctx, "value")))
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ctx -> addPollutionWithPos(
                                                ctx,
                                                BlockPosArgument.getBlockPos(ctx, "pos"),
                                                IntegerArgumentType.getInteger(ctx, "value"))
                                        )
                                )
                        )
                )
                .then(Commands.literal("removePollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> removePollution(ctx, IntegerArgumentType.getInteger(ctx, "value")))
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ctx -> removePollutionWithPos(
                                                ctx,
                                                BlockPosArgument.getBlockPos(ctx, "pos"),
                                                IntegerArgumentType.getInteger(ctx, "value"))
                                        )
                                )
                        )
                )
                .then(Commands.literal("getHeavyPollution")
                        .executes(ctx -> getPollution(ctx, true))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(ctx -> getPollutionWithPos(
                                        ctx,
                                        BlockPosArgument.getBlockPos(ctx, "pos"),
                                        true)
                                )
                        )
                )
                .then(Commands.literal("setHeavyPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> setPollution(ctx, IntegerArgumentType.getInteger(ctx, "value"), true))
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ctx -> setPollutionWithPos(
                                                ctx,
                                                BlockPosArgument.getBlockPos(ctx, "pos"),
                                                IntegerArgumentType.getInteger(ctx, "value"),
                                                true)
                                        )
                                )
                        )
                )
                .then(Commands.literal("addHeavyPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> addPollution(ctx, IntegerArgumentType.getInteger(ctx, "value"), true))
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ctx -> addPollutionWithPos(
                                                ctx,
                                                BlockPosArgument.getBlockPos(ctx, "pos"),
                                                IntegerArgumentType.getInteger(ctx, "value"),
                                                true)
                                        )
                                )
                        )
                )
                .then(Commands.literal("removeHeavyPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> removePollution(ctx, IntegerArgumentType.getInteger(ctx, "value"), true))
                                .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ctx -> removePollutionWithPos(
                                                ctx,
                                                BlockPosArgument.getBlockPos(ctx, "pos"),
                                                IntegerArgumentType.getInteger(ctx, "value"),
                                                true)
                                        )
                                )
                        )
                )
        ));
        BetterPollutionMain.LOGGER.debug("BetterPollution commands registered!"); //...probably.
    }

    private static int getPollution(CommandContext<CommandSourceStack> context) {
        return getPollution(context, false);
    }

    private static int getPollution(CommandContext<CommandSourceStack> context, boolean heavy) {
        context.getSource().sendSuccess(() -> Component.literal(
                Arrays.toString(BetterPollutionCommon.getPollutionAtPos(context.getSource().getPlayer().getOnPos(), context.getSource().getLevel(), heavy))
        ), false);
        return 0; //uhhhhhh
    }

    private static int getPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos) {
        return getPollutionWithPos(context, blockPos, false);
    }

    private static int getPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, boolean heavy) {
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(
                    BetterPollutionCommon.getPollutionAtPos(blockPos, context.getSource().getLevel(), heavy)
                )), false);
        return 0;
    }

    private static int setPollution(CommandContext<CommandSourceStack> context, int value) {
        return setPollution(context, value, false);
    }

    private static int setPollution(CommandContext<CommandSourceStack> context, int value, boolean heavy) {
        ServerPlayer player = context.getSource().getPlayer();
        BetterPollutionCommon.setPollutionAtPos(player.getOnPos(), context.getSource().getLevel(), value);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(context.getSource().getPlayer().getOnPos(), context.getSource().getLevel(), heavy))), false);
        return 0;
    }

    private static int setPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value) {
        return setPollutionWithPos(context, blockPos, value, false);
    }

    private static int setPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value, boolean heavy) {
        ServerLevel level = context.getSource().getLevel();
        BetterPollutionCommon.setPollutionAtPos(blockPos, level, value);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(blockPos, level, heavy))), false);
        return 0;
    }

    private static int getSection(CommandContext<CommandSourceStack> context) {
        int section = context.getSource().getPlayer().getBlockY() >> 4;
        context.getSource().sendSuccess(() -> Component.literal(String.valueOf(section + 4)), false);
        return 0;
    }

    private static int addPollution(CommandContext<CommandSourceStack> context, int value) {
        return addPollution(context, value, false);
    }

    private static int addPollution(CommandContext<CommandSourceStack> context, int value, boolean heavy) {
        ServerPlayer player = context.getSource().getPlayer();
        BetterPollutionCommon.addPollutionAtPos(player.getOnPos(), context.getSource().getLevel(), value, heavy);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(context.getSource().getPlayer().getOnPos(), context.getSource().getLevel(), heavy))), false);
        return 0;
    }

    private static int addPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value) {
        return addPollutionWithPos(context, blockPos, value, false);
    }

    private static int addPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value, boolean heavy) {
        ServerLevel level = context.getSource().getLevel();
        BetterPollutionCommon.addPollutionAtPos(blockPos, level, value, heavy);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(blockPos, level, heavy))), false);
        return 0;
    }

    private static int removePollution(CommandContext<CommandSourceStack> context, int value) {
        return removePollution(context, value, false);
    }

    private static int removePollution(CommandContext<CommandSourceStack> context, int value, boolean heavy) {
        ServerPlayer player = context.getSource().getPlayer();
        BetterPollutionCommon.remPollutionAtPos(player.getOnPos(), context.getSource().getLevel(), value, heavy);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(context.getSource().getPlayer().getOnPos(), context.getSource().getLevel(), heavy))), false);
        return 0;
    }

    private static int removePollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value) {
        return removePollutionWithPos(context, blockPos, value, false);
    }

    private static int removePollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value, boolean heavy) {
        ServerLevel level = context.getSource().getLevel();
        BetterPollutionCommon.remPollutionAtPos(blockPos, level, value, heavy);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(blockPos, level, heavy))), false);
        return 0;
    }
}


