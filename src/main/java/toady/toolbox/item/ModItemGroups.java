package toady.toolbox.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.util.ModEnchantments;

public class ModItemGroups {
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.MAGIC_MIRROR))
            .displayName(Text.of("Toad's Toolbox"))
            .entries((context, entries) -> {
                World world = MinecraftClient.getInstance().world;
                assert world != null;

                entries.add(ModItems.MAGIC_MIRROR);
                entries.add(ModItems.ENDER_MIRROR);
                entries.add(ModItems.BREEZE_JAR);
                entries.add(ModEnchantments.enchantItem(ModItems.BREEZE_JAR, world, ModEnchantments.JETSTREAM, 1));
                entries.add(ModEnchantments.enchantItem(ModItems.BREEZE_JAR, world, ModEnchantments.GUST, 1));
                entries.add(ModItems.CASCADE_STRIDERS);
                entries.add(ModEnchantments.enchantItem(ModItems.CASCADE_STRIDERS, world, ModEnchantments.MOLTENWALKER, 1));
                entries.add(ModItems.SLIME_BOOTS);
                entries.add(ModEnchantments.enchantItem(ModItems.SLIME_BOOTS, world, ModEnchantments.LEAPING, 1));
                if (!ToadsToolbox.IS_ENCHANCEMENT_LOADED){
                    entries.add(ModEnchantments.enchantItem(ModItems.SLIME_BOOTS, world, ModEnchantments.LEAPING, 2));
                }
                entries.add(ModItems.NIGHT_VISION_GOGGLES);
                entries.add(ModItems.BANDANA);
            })
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ToadsToolbox.id("toads_toolbox"), ITEM_GROUP);
    }
}
