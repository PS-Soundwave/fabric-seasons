package io.github.lucaargolo.seasons.block;

import io.github.lucaargolo.seasons.FabricSeasons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.Random;

public class SeasonalSnowBlock extends SnowBlock {
    public SeasonalSnowBlock(Settings settings) {
        super(settings);
    }

    private boolean sunlit(BlockState state, ServerWorld world, BlockPos pos) {
        if (state.get(LAYERS) < 8) {
            return world.getLightLevel(LightType.SKY, pos) > 0;
        }
        else {
            if (world.getLightLevel(LightType.SKY, pos.up()) > 0) {
                return true;
            }
            else if (world.getLightLevel(LightType.SKY, pos.south()) > 1) {
                return true;
            }
            else if (world.getLightLevel(LightType.SKY, pos.east()) > 1) {
                return true;
            }
            else if (world.getLightLevel(LightType.SKY, pos.north()) > 1) {
                return true;
            }
            else if (world.getLightLevel(LightType.SKY, pos.west()) > 1) {
                return true;
            }
            return world.getLightLevel(LightType.SKY, pos.down()) > 1;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (sunlit(state, world, pos) && world.getBiome(pos).getTemperature(pos) >= 0.15F) {
            Block.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public String getTranslationKey() {
        return FabricSeasons.ORIGINAL_SNOW.getTranslationKey();
    }

    @Override
    protected Block asBlock() {
        return FabricSeasons.ORIGINAL_SNOW;
    }

    @Override
    public Item asItem() {
        return FabricSeasons.ORIGINAL_SNOW.asItem();
    }
}