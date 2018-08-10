package shadows.hitwithaxe.block;

import net.minecraft.block.state.IBlockState;
import shadows.hitwithaxe.EnumBarkType;

public class BlockDebarkedCrystal extends BlockDebarkedLog {

	public BlockDebarkedCrystal(EnumBarkType type) {
		super(type);
	}

	@Override
	public int getLightValue(IBlockState state) {
		return 15;
	}

}
