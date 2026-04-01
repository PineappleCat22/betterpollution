package com.pineapple.betterpollution;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.common.Mod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

@Mod(BetterPollution.MODID)
public class BetterPollutionCommands {
    //bonus, do commands run on the client side or server side?
    //server side it seems

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build the command structure using Commands.literal and Commands.argument
        dispatcher.register(Commands.literal("mycommand")
                .requires(source -> source.hasPermission(2)) // Set required permission level
                .then(Commands.literal("subcommand")
                        .then(Commands.argument("number", IntegerArgumentType.integer(1, 10)) // Add an argument
                                .executes(BetterPollutionCommands::execute) // Specify the method to run
                        )
                )
        );
        dispatcher.register((Commands.literal(BetterPollution.MODID)
                .then(Commands.literal("getChunk")
                        .executes(BetterPollutionCommands::getChunk)
                )
        ));
        BetterPollution.LOGGER.debug("BetterPollution commands registered!"); //...probably.
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        // Logic to execute when the command is run
        int number = IntegerArgumentType.getInteger(context, "number");
        context.getSource().sendSuccess(() -> Component.literal("You entered the number: " + number), false);

        // Return a result value (optional, but standard practice)
        return number;
    }

    private static int getChunk(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal(
                String.valueOf(Math.floor(context.getSource().getPlayer().getX()/16))
                        + " "
                        + String.valueOf(Math.floor(context.getSource().getPlayer().getY()/16))
                        + " "
                        + String.valueOf(Math.floor(context.getSource().getPlayer().getZ()/16))
        ), false);
        return 2;
    }
}
