package shadows.hitwithaxe;

import java.util.function.Function;

import net.minecraft.item.ItemStack;
import shadows.hitwithaxe.block.BlockDebarkedCrystal;
import shadows.hitwithaxe.block.BlockDebarkedHellbark;
import shadows.hitwithaxe.block.BlockDebarkedLog;
import shadows.hitwithaxe.block.BlockDebarkedRubber;
import shadows.hitwithaxe.block.BlockDebarkedSpectre;
import shadows.placebo.interfaces.IPropertyEnum;

public enum EnumBarkType implements IPropertyEnum {
	ACACIA,
	ASH(HitWithAxe.PLANTS),
	BIRCH,
	BLACK_KAURI(HitWithAxe.PLANTS),
	BLAZE(HitWithAxe.PLANTS),
	BRAZILLIAN_PINE(HitWithAxe.PLANTS),
	CHERRY(HitWithAxe.BOP),
	CRYSTAL(e -> new BlockDebarkedCrystal(e), HitWithAxe.PLANTS),
	DARK_CRYSTAL(e -> new BlockDebarkedCrystal(e), HitWithAxe.PLANTS),
	DARK_OAK,
	DEAD(HitWithAxe.BOP),
	EBONY(HitWithAxe.BOP),
	ETHEREAL(HitWithAxe.BOP),
	EUCALYPTUS_BOP(HitWithAxe.BOP),
	FIR("traverse"),
	FIR_BOP(HitWithAxe.BOP),
	GREATWOOD("thaumcraft"),
	HELLBARK(e -> new BlockDebarkedHellbark(e), HitWithAxe.BOP),
	INCENSE_CEDAR(HitWithAxe.PLANTS),
	JACARANDA(HitWithAxe.BOP),
	JUNGLE,
	MAGIC(HitWithAxe.BOP),
	MAHOGANY(HitWithAxe.BOP),
	MANGROVE(HitWithAxe.BOP),
	MENRIL("integrateddynamics"),
	MURRAY_PINE(HitWithAxe.PLANTS),
	OAK,
	PALM(HitWithAxe.BOP),
	PINE(HitWithAxe.BOP),
	REDWOOD(HitWithAxe.BOP),
	ROOT(HitWithAxe.BETWEENLANDS),
	RUBBER(e -> new BlockDebarkedRubber(e), HitWithAxe.BETWEENLANDS),
	SACRED_OAK(HitWithAxe.BOP),
	SILVERWOOD("thaumcraft"),
	SPECTRE(e -> new BlockDebarkedSpectre(e), "randomthings"),
	SPRUCE,
	UMBRAN(HitWithAxe.BOP),
	WILLOW_BOP(HitWithAxe.BOP),
	WEEDWOOD(HitWithAxe.BETWEENLANDS),
	AMARANTH(HitWithAxe.NATURA),
	EUCALYPTUS(HitWithAxe.NATURA),
	HOPSEED(HitWithAxe.NATURA),
	IRONWOOD(HitWithAxe.RUSTIC),
	MAPLE(HitWithAxe.NATURA),
	OLIVE(HitWithAxe.RUSTIC),
	SAKURA(HitWithAxe.NATURA),
	SILVERBELL(HitWithAxe.NATURA),
	TIGER(HitWithAxe.NATURA),
	WILLOW(HitWithAxe.NATURA),
	ANCIENT(HitWithAxe.NA);

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
