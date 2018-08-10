package shadows.hitwithaxe;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransformRecipe {

	Predicate<IBlockState> input;
	ITransformCall action;
	int harvestLevel;

	public TransformRecipe(Predicate<IBlockState> input, ITransformCall action, int harvestLevel) {
		this.input = input;
		this.action = action;
		this.harvestLevel = harvestLevel;
	}

	public boolean matches(IBlockState state) {
		return input.apply(state);
	}

	public void action(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
		action.call(world, pos, state, player, hand);
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

}
