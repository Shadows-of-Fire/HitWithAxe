package shadows.hitwithaxe;

import java.util.function.Function;

import net.minecraft.item.ItemStack;
import shadows.hitwithaxe.block.BlockDebarkedCrystal;
import shadows.hitwithaxe.block.BlockDebarkedLog;
import shadows.hitwithaxe.block.BlockDebarkedRubber;
import shadows.hitwithaxe.block.BlockDebarkedSpectre;
import shadows.placebo.interfaces.IPropertyEnum;

public enum EnumBarkType implements IPropertyEnum {
	ACACIA,
	ASH("plants2"),
	BIRCH,
	BLACK_KAURI("plants2"),
	BLAZE("plants2"),
	BRAZILLIAN_PINE("plants2"),
	CRYSTAL(e -> new BlockDebarkedCrystal(e), "plants2"),
	DARK_CRYSTAL(e -> new BlockDebarkedCrystal(e), "plants2"),
	DARK_OAK,
	FIR("traverse"),
	GREATWOOD("thaumcraft"),
	INCENSE_CEDAR("plants2"),
	JUNGLE,
	MENRIL("integrateddynamics"),
	MURRAY_PINE("plants2"),
	OAK,
	ROOT(HitWithAxe.BETWEENLANDS),
	RUBBER(e -> new BlockDebarkedRubber(e), HitWithAxe.BETWEENLANDS),
	SILVERWOOD("thaumcraft"),
	SPECTRE(e -> new BlockDebarkedSpectre(e), "randomthings"),
	SPRUCE,
	WEEDWOOD(HitWithAxe.BETWEENLANDS);

	final Function<EnumBarkType, BlockDebarkedLog> block;
	final String modid;

	EnumBarkType(Function<EnumBarkType, BlockDebarkedLog> block, String modid) {
		this.block = block;
		this.modid = modid;
	}

	EnumBarkType(String modid) {
		this(HitWithAxe.DEFAULT_FUNC, modid);
	}

	EnumBarkType() {
		this(HitWithAxe.DEFAULT_FUNC, "minecraft");
	}

	@Override
	public ItemStack get() {
		return new ItemStack(HitWithAxe.BARK, 1, ordinal());
	}

	public BlockDebarkedLog createBlock() {
		return block.apply(this);
	}

	public String getReqMod() {
		return modid;
	}

}
