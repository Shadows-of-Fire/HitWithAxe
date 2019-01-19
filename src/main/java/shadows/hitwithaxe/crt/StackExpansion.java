package shadows.hitwithaxe.crt;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion(value = "crafttweaker.item.IItemStack")
@ZenRegister
public class StackExpansion {

	@SuppressWarnings("deprecation")
	@ZenMethod
	public static IBlockState toState(IItemStack stack) {
		return CraftTweakerMC.getBlockState(Block.getBlockFromItem(((ItemStack) stack.getInternal()).getItem()).getStateFromMeta(stack.getMetadata()));
	}

}
