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
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.ITransformCall;
import shadows.hitwithaxe.TransformRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.hitwithaxe.Chopping")
@ZenRegister
public class Chopping {

	@SuppressWarnings("deprecation")
	@ZenMethod
	public static void addRecipe(String blockIn, int metaIn, String blockOut, int metaOut, int harvestLevel, IItemStack result) {
		IBlockState inState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockIn)).getStateFromMeta(metaIn == 32767 ? 0 : metaIn);
		IBlockState outState = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockOut)).getStateFromMeta(metaOut == 32767 ? 0 : metaOut);
		ItemStack stack = CraftTweakerMC.getItemStack(result);
		ITransformCall call = (world, pos, state, player, hand) -> {
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
			player.getCooldownTracker().setCooldown(held.getItem(), 10);
		};
		if (metaIn == OreDictionary.WILDCARD_VALUE) HitWithAxe.RECIPES.add(new TransformRecipe(s -> s.getBlock() == inState.getBlock(), call, harvestLevel));
		else HitWithAxe.RECIPES.add(new TransformRecipe(s -> s == inState, call, harvestLevel));
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

}
