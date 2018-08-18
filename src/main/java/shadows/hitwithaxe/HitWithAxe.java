package shadows.hitwithaxe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import shadows.hitwithaxe.block.BlockDebarkedLog;
import shadows.hitwithaxe.compat.CompatBetweenlands;
import shadows.hitwithaxe.proxy.Proxy;
import shadows.placebo.registry.RegistryInformationV2;
import shadows.placebo.util.EnumBlockFactory;

@Mod(modid = HitWithAxe.MODID, name = HitWithAxe.MODNAME, version = HitWithAxe.VERSION, dependencies = "required-after:placebo@[1.4.1,);required-after:crafttweaker;after:thebetweenlands;after:plants2;after:thaumcraft;after:randomthings;after:traverse;after:integrateddynamics")
public class HitWithAxe {

	public static final String MODID = "hitwithaxe";
	public static final String MODNAME = "Hit With Axe";
	public static final String VERSION = "1.0.0";

	public static final Logger LOG = LogManager.getLogger(MODID);
	public static final List<TransformRecipe> RECIPES = new ArrayList<>();
	public static final RegistryInformationV2 INFO = new RegistryInformationV2(MODID, CreativeTabs.MATERIALS);
	public static final Function<EnumBarkType, BlockDebarkedLog> DEFAULT_FUNC = (e) -> new BlockDebarkedLog(e);
	public static final String BETWEENLANDS = "thebetweenlands";

	@SidedProxy(serverSide = "shadows.hitwithaxe.proxy.Proxy", clientSide = "shadows.hitwithaxe.proxy.ClientProxy")
	public static Proxy proxy;

	public static final ItemBark BARK = new ItemBark("bark", EnumBarkType.values(), INFO);

	public static final EnumBlockFactory<EnumBarkType, BlockDebarkedLog> DEBARKED_LOGS = new EnumBlockFactory<>(e -> Loader.isModLoaded(e.getReqMod()) ? e.createBlock() : null, EnumBarkType.values());

	public static int hungerCost = 1;
	public static int cooldown = 10;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(proxy);
		Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
		hungerCost = cfg.getInt("Food Cost", "general", hungerCost, 0, 20, "How much hunger is used per swing.  This will consume saturation when possible.  1 = half shank.");
		cooldown = cfg.getInt("Swing Cooldown", "general", cooldown, 0, Integer.MAX_VALUE, "The cooldown after using an axe, in ticks");
		if (cfg.hasChanged()) cfg.save();
	}

	@SubscribeEvent
	public void registerBlocks(Register<Block> e) {
		INFO.getBlockList().register(e.getRegistry());
		if (Loader.isModLoaded(EnumBarkType.CRYSTAL.getReqMod())) {
			DEBARKED_LOGS.getBlock(EnumBarkType.CRYSTAL).setSoundType(SoundType.GLASS);
			DEBARKED_LOGS.getBlock(EnumBarkType.DARK_CRYSTAL).setSoundType(SoundType.GLASS);
		}
		if (Loader.isModLoaded(BETWEENLANDS)) CompatBetweenlands.replaceLog(e.getRegistry());
	}

	@SubscribeEvent
	public void registerItems(Register<Item> e) {
		INFO.getItemList().register(e.getRegistry());
		OreDictionary.registerOre("barkWood", new ItemStack(BARK, 1, OreDictionary.WILDCARD_VALUE));
		for (EnumBarkType ebt : EnumBarkType.values())
			OreDictionary.registerOre("logWood", DEBARKED_LOGS.getBlock(ebt));
	}

	@SubscribeEvent
	public void onItemUse(RightClickBlock e) {
		World world = e.getWorld();
		BlockPos pos = e.getPos();
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = e.getItemStack();
		EntityPlayer player = e.getEntityPlayer();
		if (stack.getItem().getToolClasses(stack).contains("axe") && hasFood(player) && player.getCooldownTracker().getCooldown(stack.getItem(), 0) <= 0) {
			for (TransformRecipe recipe : RECIPES) {
				if (recipe.matches(state) && stack.getItem().getHarvestLevel(stack, "axe", player, state) >= recipe.getHarvestLevel()) {
					recipe.action(world, pos, state, player, e.getHand());
					break;
				}
			}
		}
	}

	public static boolean hasFood(EntityPlayer player) {
		FoodStats stats = player.getFoodStats();
		float combined = stats.getFoodLevel() + stats.getSaturationLevel();
		return combined >= hungerCost;
	}

	public static void consumeFood(EntityPlayer player) {
		if (player.capabilities.isCreativeMode) return;
		FoodStats stats = player.getFoodStats();
		int food = stats.getFoodLevel();
		float saturation = stats.getSaturationLevel();
		if (saturation >= hungerCost) {
			stats.addStats(-hungerCost, 1);
			stats.setFoodLevel(food);
		} else {
			stats.setFoodLevel(food - hungerCost);
		}
	}
}
