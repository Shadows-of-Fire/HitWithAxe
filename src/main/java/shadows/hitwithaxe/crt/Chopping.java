package shadows.hitwithaxe.crt;

import com.google.common.base.Predicate;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
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
		Predicate<IBlockState> ing = s -> s == inState;
		NonNullList<ItemStack> inputs = NonNullList.create();
		if (matchBlock) {
			ing = s -> s.getBlock() == inState.getBlock();
			Item.getItemFromBlock(inState.getBlock()).getSubItems(CreativeTabs.SEARCH, inputs);
		} else {
			inputs.add(fromState(inState));
		}
		NonNullList<ItemStack> outputs = NonNullList.withSize(2, ItemStack.EMPTY);
		outputs.set(0, fromState(outState));
		outputs.set(1, stack);
		HitWithAxe.RECIPES.add(new TransformRecipe(ing, call, harvestLevel, inputs, outputs));
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

	@ZenMethod
	public static void addRecipe(crafttweaker.api.block.IBlockState input, crafttweaker.api.block.IBlockState output, @Optional int harvestLevel, @Optional IItemStack result) {
		addRecipe(CraftTweakerMC.getBlockState(input), false, CraftTweakerMC.getBlockState(output), harvestLevel, CraftTweakerMC.getItemStack(result));
	}

	private static ItemStack fromState(IBlockState state) {
		ItemStack stack;
		try {
			stack = state.getBlock().getPickBlock(state, new RayTraceResult(Type.BLOCK, new Vec3d(0.5, 0.5, 0.5), EnumFacing.DOWN, BlockPos.ORIGIN), null, BlockPos.ORIGIN, null);
		} catch (Exception e) {
			e.printStackTrace();
			stack = ItemStack.EMPTY;
		}
		if (stack.isEmpty()) try {
			stack = new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().getMetaFromState(state));
		} catch (Exception e) {
			e.printStackTrace();
			stack = ItemStack.EMPTY;
		}
		return stack;
	}

}
