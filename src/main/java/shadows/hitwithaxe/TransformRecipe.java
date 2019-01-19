package shadows.hitwithaxe;

import java.util.List;

import com.google.common.base.Predicate;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TransformRecipe {

	protected final Predicate<IBlockState> input;
	protected final ITransformCall action;
	protected final int harvestLevel;
	protected final List<ItemStack> displayIn;
	protected final List<ItemStack> displayOut;

	public TransformRecipe(Predicate<IBlockState> input, ITransformCall action, int harvestLevel, List<ItemStack> displayIn, List<ItemStack> displayOut) {
		this.input = input;
		this.action = action;
		this.harvestLevel = harvestLevel;
		this.displayIn = displayIn;
		this.displayOut = displayOut;
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

	public void addIngredients(IIngredients ig) {
		ig.setInputs(VanillaTypes.ITEM, this.displayIn);
		ig.setOutputs(VanillaTypes.ITEM, this.displayOut);
	}

	public List<ItemStack> getDisplayIn() {
		return displayIn;
	}

	public List<ItemStack> getDisplayOut() {
		return displayOut;
	}

	public ItemStack getFirstDisplayIn() {
		return getDisplayIn().get(0);
	}

}
