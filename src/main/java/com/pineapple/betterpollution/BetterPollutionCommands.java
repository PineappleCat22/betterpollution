package com.pineapple.betterpollution;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.Mod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.Arrays;

@Mod(BetterPollution.MODID)
public class BetterPollutionCommands {
    //bonus, do commands run on the client side or server side?
    //server side it seems

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build the command structure using Commands.literal and Commands.argument
        dispatcher.register((Commands.literal(BetterPollution.MODID)
                .then(Commands.literal("getPollution")
                        .executes(ctx -> BetterPollutionCommands.getPollution(ctx))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(ctx -> BetterPollutionCommands.getPollutionWithPos(ctx, BlockPosArgument.getBlockPos(ctx, "pos"))
                        )
                )
        )));
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
}
