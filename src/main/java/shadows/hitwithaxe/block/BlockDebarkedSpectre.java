package shadows.hitwithaxe.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import shadows.hitwithaxe.EnumBarkType;

public class BlockDebarkedSpectre extends BlockDebarkedLog {

	public BlockDebarkedSpectre(EnumBarkType type) {
		super(type);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state != world.getBlockState(pos.offset(side));
	}

}
