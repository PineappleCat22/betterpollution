package com.pineapple.betterpollution;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
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
TODO: derive section # from blockPos instead of specifying
TODO: make commands more responsive ie: if command fails, the command should respond with why.
if you pass a negative to my methods, i will kill you.
 */

@Mod(BetterPollution.MODID)
public class BetterPollutionCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal(BetterPollution.MODID)
                .then(Commands.literal("getPollution")
                        .executes(ctx -> BetterPollutionCommands.getPollution(ctx))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(ctx -> BetterPollutionCommands.getPollutionWithPos(ctx, BlockPosArgument.getBlockPos(ctx, "pos"))
                        )
                ))
                .then(Commands.literal("getSectionY")
                        .executes(ctx -> getSection(ctx))
                )
                // this is all KINDS of messed up
                .then(Commands.literal("setPollution")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> setPollution(ctx, IntegerArgumentType.getInteger(ctx, "value")))
                                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                                .executes(ctx -> BetterPollutionCommands.setPollutionWithPos(
                                                        ctx,
                                                        BlockPosArgument.getBlockPos(ctx, "pos"),
                                                        IntegerArgumentType.getInteger(ctx, "value"))
                                                )
                                        )
                                )
                        )
                )
        );
        BetterPollution.LOGGER.debug("BetterPollution commands registered!"); //...probably.
    }

    private static int getPollution(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal(
                Arrays.toString(BetterPollutionCommon.getPollutionAtPlayer(context.getSource().getPlayer()))
        ), false);
        return 0; //uhhhhhh
    }

    private static int getPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos) {
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(
                    BetterPollutionCommon.getPollutionAtPos(blockPos, context.getSource().getLevel())
                )), false);
        return 0;
    }

    private static int setPollution(CommandContext<CommandSourceStack> context, int value) {
        ServerPlayer player = context.getSource().getPlayer();
        BetterPollutionCommon.setPollutionAtPlayer(player, value);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPlayer(context.getSource().getPlayer()))), false);
        BetterPollutionCommon.getPollutionAtPlayer(context.getSource().getPlayer());
        return 0;
    }

    private static int setPollutionWithPos(CommandContext<CommandSourceStack> context, BlockPos blockPos, int value) {
        ServerLevel level = context.getSource().getLevel();
        BetterPollutionCommon.setPollutionAtPos(blockPos, level, value);
        context.getSource().sendSuccess(() -> Component.literal(Arrays.toString(BetterPollutionCommon.getPollutionAtPos(blockPos, level))), false);
        return 0;
    }

    private static int getSection(CommandContext<CommandSourceStack> context) {
        int section = context.getSource().getPlayer().getBlockY() >> 4;
        context.getSource().sendSuccess(() -> Component.literal(String.valueOf(section + 4)), false);
        return 0;
    }
}
