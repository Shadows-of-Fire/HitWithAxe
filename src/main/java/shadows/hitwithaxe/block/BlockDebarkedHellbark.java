package shadows.hitwithaxe.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import shadows.hitwithaxe.EnumBarkType;

public class BlockDebarkedHellbark extends BlockDebarkedLog {

	public BlockDebarkedHellbark(EnumBarkType type) {
		super(type);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 0;
	}

}
