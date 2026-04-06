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

    public static void setPollutionAtPlayer(ServerPlayer player, int index, int value) {
        ServerLevel level = player.serverLevel();
        LevelChunk playerChunk = level.getChunkAt(player.blockPosition());
        int[] data = playerChunk.getData(BetterPollution.POLLUTION_DATA);
        data[index] = value;
        playerChunk.setData(BetterPollution.POLLUTION_DATA, data);
        // maybe return success status?
    }

    public static int[] getPollutionAtPos(BlockPos blockPos, ServerLevel level) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        return posChunk.getData(BetterPollution.POLLUTION_DATA);
    }

    public static void setPollutionAtPos(BlockPos blockPos, ServerLevel level, int index, int value) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        int[] data = posChunk.getData(BetterPollution.POLLUTION_DATA);
        data[index] = value;
        posChunk.setData(BetterPollution.POLLUTION_DATA, data);
    }
}
