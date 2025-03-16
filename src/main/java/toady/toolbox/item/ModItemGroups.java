package toady.toolbox.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import toady.toolbox.ToadsToolbox;

public class ModItemGroups {
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.MAGIC_MIRROR))
            .displayName(Text.of("Toad's Toolbox"))
            .entries((context, entries) -> {
                entries.add(ModItems.MAGIC_MIRROR);
                entries.add(ModItems.ENDER_MIRROR);
                entries.add(ModItems.BREEZE_JAR);
                entries.add(ModItems.CASCADE_STRIDERS);
                entries.add(ModItems.SLIME_BOOTS);
                entries.add(ModItems.NIGHT_VISION_GOGGLES);
            })
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, ToadsToolbox.id("toads_toolbox"), ITEM_GROUP);
    }
}
