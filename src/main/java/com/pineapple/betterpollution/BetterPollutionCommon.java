package com.pineapple.betterpollution;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.common.Mod;

@Mod(BetterPollution.MODID)
public class BetterPollutionCommon {

    public static int[] getPollutionAtPlayer(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        LevelChunk playerChunk = level.getChunkAt(player.blockPosition());
        return playerChunk.getData(BetterPollution.POLLUTION_DATA);
    }

    public static void setPollutionAtPlayer(ServerPlayer player, int value) {
        ServerLevel level = player.serverLevel();
        LevelChunk playerChunk = level.getChunkAt(player.blockPosition());
        int playerSection = (player.getBlockY() >> 4) + 4;
        if (playerSection < 0) {
            BetterPollution.LOGGER.warn("playerSection resolved below 0! Player is below bedrock. Aborting.");
            return;
        }
        int[] data = playerChunk.getData(BetterPollution.POLLUTION_DATA);
        data[playerSection] = value;
        playerChunk.setData(BetterPollution.POLLUTION_DATA, data);
        // maybe return success status?
    }

    public static int[] getPollutionAtPos(BlockPos blockPos, ServerLevel level) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        return posChunk.getData(BetterPollution.POLLUTION_DATA);
    }

    public static void setPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        int posSection = (blockPos.getY() >> 4) + 4;
        if (posSection < 0) {
            BetterPollution.LOGGER.warn("posSection resolved below 0! Position is below bedrock. Aborting.");
            return;
        }
        int[] data = posChunk.getData(BetterPollution.POLLUTION_DATA);
        data[posSection] = value;
        posChunk.setData(BetterPollution.POLLUTION_DATA, data);
    }
}
