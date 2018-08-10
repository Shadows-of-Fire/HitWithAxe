package shadows.hitwithaxe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITransformCall {

	void call(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand);

}
