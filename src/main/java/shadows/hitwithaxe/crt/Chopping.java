package shadows.hitwithaxe.crt;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import shadows.hitwithaxe.DefaultTransformCall;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.ITransformCall;
import shadows.hitwithaxe.TransformRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.hitwithaxe.Chopping")
@ZenRegister
@SuppressWarnings("deprecation")
public class Chopping {

	/**
	 * Adds a chopping recipe
	 * @param inState The matching blockstate
	 * @param matchBlock If the matcher should test for Block instead of IBlockState
	 * @param outState The output state, may be Air.
	 * @param harvestLevel The required harvest level. 
	 * @param stack The result stack, may be empty.
	 */
	public static void addRecipe(IBlockState inState, boolean matchBlock, IBlockState outState, int harvestLevel, ItemStack stack) {
		ITransformCall call = new DefaultTransformCall(stack, outState);
		if (matchBlock) HitWithAxe.RECIPES.add(new TransformRecipe(s -> s.getBlock() == inState.getBlock(), call, harvestLevel));
		else HitWithAxe.RECIPES.add(new TransformRecipe(s -> s == inState, call, harvestLevel));
	}

	/**
	 * Adds a chopping recipe, converting string - int pairs into IBlockStates.
	 */
	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, String blockOut, int metaOut, int harvestLevel, IItemStack result) {
		IBlockState inState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockIn)).getStateFromMeta(metaIn == 32767 ? 0 : metaIn);
		IBlockState outState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockOut)).getStateFromMeta(metaOut == 32767 ? 0 : metaOut);
		ItemStack stack = CraftTweakerMC.getItemStack(result);
		addRecipe(inState, metaIn == OreDictionary.WILDCARD_VALUE, outState, harvestLevel, stack);
	}

	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, int harvestLevel, IItemStack result) {
		addRecipe(blockIn, metaIn, "minecraft:air", 0, harvestLevel, result);
	}

	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, String blockOut, int metaOut, IItemStack result) {
		addRecipe(blockIn, metaIn, blockOut, metaOut, 0, result);
	}

	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, IItemStack result) {
		addRecipe(blockIn, metaIn, "minecraft:air", 0, 0, result);
	}

	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, String blockOut, int metaOut) {
		addRecipe(blockIn, metaIn, blockOut, metaOut, 0, MCItemStack.EMPTY);
	}

	/**
	 * Adds a chopping recipe, converting IItemStacks to IBlockStates
	 */
	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, int harvestLevel, @Optional IItemStack result) {
		ItemStack in = CraftTweakerMC.getItemStack(input);
		ItemStack out = CraftTweakerMC.getItemStack(output);
		int metaIn = in.getMetadata();
		int metaOut = out.getMetadata();
		IBlockState inState = Block.getBlockFromItem(in.getItem()).getStateFromMeta(metaIn == 32767 ? 0 : metaIn);
		IBlockState outState = Block.getBlockFromItem(out.getItem()).getStateFromMeta(metaOut == 32767 ? 0 : metaOut);
		ItemStack stack = CraftTweakerMC.getItemStack(result);
		addRecipe(inState, metaIn == OreDictionary.WILDCARD_VALUE, outState, harvestLevel, stack);
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, int harvestLevel, @Optional IItemStack result) {
		addRecipe(input, MCItemStack.EMPTY, harvestLevel, result);
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output, @Optional IItemStack result) {
		addRecipe(input, output, 0, result);
	}

}
