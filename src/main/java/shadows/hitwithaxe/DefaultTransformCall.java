package shadows.hitwithaxe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DefaultTransformCall implements ITransformCall {

	final ItemStack stack;
	final IBlockState outState;

	public DefaultTransformCall(ItemStack stack, IBlockState outState) {
		this.stack = stack;
		this.outState = outState;
	}

	@Override
	public void call(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
		ItemStack held = player.getHeldItem(hand);
		if (!world.isRemote) {
			world.playEvent(2001, pos, Block.getStateId(state));
			world.setBlockState(pos, outState);
			if (!stack.isEmpty()) {
				ItemStack use = stack.copy();
				Block.spawnAsEntity(world, pos.offset(player.getHorizontalFacing(), -1), use);
			}
			held.damageItem(1, player);
			HitWithAxe.consumeFood(player);
		}
		player.swingArm(hand);
		player.getCooldownTracker().setCooldown(held.getItem(), HitWithAxe.cooldown);
	};

}
