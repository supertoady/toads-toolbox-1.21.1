package toady.toolbox;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toady.toolbox.effects.ModParticles;
import toady.toolbox.item.ModItemGroups;
import toady.toolbox.item.ModItems;
import toady.toolbox.effects.ModSounds;
import toady.toolbox.util.ModEnchantments;
import toady.toolbox.util.ModLootTableModifiers;

import java.util.Optional;

public class ToadsToolbox implements ModInitializer {

	public static final String MOD_ID = "toads-toolbox";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModItemGroups.initialize();
		ModEnchantments.init();

		ModSounds.initialize();
		ModParticles.initialize();
		ModLootTableModifiers.modifyLootTables();

		LOGGER.info("Toolbox loaded!");
	}

	//random util methods
	public static ItemStack getEquipped(int slot, PlayerEntity entity, Item item){
		Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);
		if (entity.getInventory().getArmorStack(slot).isOf(item)){
			return entity.getInventory().getArmorStack(slot);
		}
		else if (trinketComponent.isPresent() && trinketComponent.get().isEquipped(item)){
			return trinketComponent.get().getEquipped(item).getFirst().getRight();
		}
		return ItemStack.EMPTY;
	}

	public static boolean hasShiftDown(){
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
			return Screen.hasShiftDown();
		}
		else {
			return false;
		}
	}

	public static void showParticlesToAll(World world, ParticleEffect particle, Vec3d pos, double delta, int count, double speed){
		if (world.getServer() == null) return;
		ServerWorld serverWorld = world.getServer().getWorld(world.getRegistryKey());
		if (serverWorld != null){
			serverWorld.spawnParticles(particle, pos.x, pos.y, pos.z, count, delta, delta, delta, speed);
		}
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}