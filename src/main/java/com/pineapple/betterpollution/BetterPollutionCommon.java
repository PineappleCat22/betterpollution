package com.pineapple.betterpollution;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.common.Mod;

@Mod(BetterPollutionMain.MODID)
public class BetterPollutionCommon {

    public static int[] getPollutionAtPos(BlockPos blockPos, ServerLevel level) {
        return getPollutionAtPos(blockPos, level, false);
    }

    public static int[] getPollutionAtPos(BlockPos blockPos, ServerLevel level, boolean heavy) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        if (heavy) {
            return posChunk.getData(BetterPollutionMain.HEAVY_POLLUTION_DATA);
        }
        return posChunk.getData(BetterPollutionMain.POLLUTION_DATA);
    }

    public static void setPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {
        setPollutionAtPos(blockPos, level, value, false);
    }

    public static void setPollutionAtPos(BlockPos blockPos, ServerLevel level, int value, boolean heavy) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        int posSection = (blockPos.getY() >> 4) + 4;
        if (posSection < 0) {
            BetterPollutionMain.LOGGER.warn("posSection resolved below 0! Position is below bedrock. Aborting.");
            return;
        }
        if (heavy) {
            int[] data = posChunk.getData(BetterPollutionMain.HEAVY_POLLUTION_DATA);
            data[posSection] = value;
            posChunk.setData(BetterPollutionMain.HEAVY_POLLUTION_DATA, data);
            return;
        }
        int[] data = posChunk.getData(BetterPollutionMain.POLLUTION_DATA);
        data[posSection] = value;
        posChunk.setData(BetterPollutionMain.POLLUTION_DATA, data);
    }

    public static void addPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {
        addPollutionAtPos(blockPos, level, value, false);
    }

    public static void addPollutionAtPos(BlockPos blockPos, ServerLevel level, int value, boolean heavy) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        int posSection = (blockPos.getY() >> 4) + 4;
        if (posSection < 0) {
            BetterPollutionMain.LOGGER.warn("posSection resolved below 0! Position is below bedrock. Aborting.");
            return;
        }
        if (value == 0) {
            return;
        }
        if (heavy) {
            int[] pollutionArray = getPollutionAtPos(blockPos, level, true);
            pollutionArray[posSection] = pollutionArray[posSection] + value;
            posChunk.setData(BetterPollutionMain.HEAVY_POLLUTION_DATA, pollutionArray);
            return;
        }
        int[] pollutionArray = getPollutionAtPos(blockPos, level);
        pollutionArray[posSection] = pollutionArray[posSection] + value;
        posChunk.setData(BetterPollutionMain.POLLUTION_DATA, pollutionArray);
    }

    public static void remPollutionAtPos(BlockPos blockPos, ServerLevel level, int value) {
        remPollutionAtPos(blockPos, level, value, false);
    }

    public static void remPollutionAtPos(BlockPos blockPos, ServerLevel level, int value, boolean heavy) {
        LevelChunk posChunk = level.getChunkAt(blockPos);
        int posSection = (blockPos.getY() >> 4) + 4;
        if (posSection < 0) {
            BetterPollutionMain.LOGGER.warn("posSection resolved below 0! Position is below bedrock. Aborting.");
            return;
        }
        if (value == 0) {
            return;
        }
        if (heavy) {
            int[] pollutionArray = getPollutionAtPos(blockPos, level, true);
            pollutionArray[posSection] = pollutionArray[posSection] - value;
            posChunk.setData(BetterPollutionMain.HEAVY_POLLUTION_DATA, pollutionArray);
            return;
        }
        int[] pollutionArray = getPollutionAtPos(blockPos, level);
        pollutionArray[posSection] = pollutionArray[posSection] - value;
        posChunk.setData(BetterPollutionMain.POLLUTION_DATA, pollutionArray);
    }

}
