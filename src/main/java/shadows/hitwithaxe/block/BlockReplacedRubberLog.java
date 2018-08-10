package shadows.hitwithaxe.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import shadows.hitwithaxe.EnumBarkType;
import shadows.hitwithaxe.HitWithAxe;
import thebetweenlands.common.block.terrain.BlockRubberLog;

public class BlockReplacedRubberLog extends BlockRubberLog {

	BlockDebarkedRubber block = (BlockDebarkedRubber) HitWithAxe.DEBARKED_LOGS.getBlock(EnumBarkType.RUBBER);

	@Override
	public boolean canConnectTo(IBlockAccess world, BlockPos pos) {
		return block.canConnectTo(world, pos);
	}
}
