package com.pineapple.betterpollution;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.common.Mod;

@Mod(BetterPollution.MODID)
public class BetterPollutionCommon {

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

    public static void addPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {
        int[] oldData = getPollutionAtPos(blockPos, level);
        int posSection = (blockPos.getY() >> 4) + 4;
        if (posSection < 0) {
            BetterPollution.LOGGER.warn("posSection resolved below 0! Position is below bedrock. Aborting.");
            return;
        }
    }
    
    public static void remPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {

    }

}
