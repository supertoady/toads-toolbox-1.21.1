package toady.toolbox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
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
import toady.toolbox.util.ModLootTableModifiers;

public class ToadsToolbox implements ModInitializer {

	public static final String MOD_ID = "toads-toolbox";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModItemGroups.initialize();

		ModSounds.initialize();
		ModParticles.initialize();
		ModLootTableModifiers.modifyLootTables();

		LOGGER.info("Toolbox loaded!");
	}

	//random util methods
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