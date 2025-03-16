package toady.toolbox.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import toady.toolbox.ToadsToolbox;

public class ModComponents {
    public static final ComponentType<Integer> BREEZE_JAR_DASHES = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ToadsToolbox.MOD_ID, "breeze_jar_dashes"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Vec3d> ENDER_MIRROR_POS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ToadsToolbox.MOD_ID, "ender_mirror_pos"),
            ComponentType.<Vec3d>builder().codec(Vec3d.CODEC).build()
    );
    public static final ComponentType<String> ENDER_MIRROR_DIMENSION = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ToadsToolbox.MOD_ID, "ender_mirror_dimension"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static void initialize() {}
}
