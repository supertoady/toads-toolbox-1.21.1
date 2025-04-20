package toady.toolbox.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Vec3d;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.custom.*;

public class ModItems {

    public static final Item NIGHT_VISION_GOGGLES = registerItem("night_vision_goggles",
            new NightVisionGogglesItem(new Item.Settings().maxCount(1)));

    public static final Item BANDANA = registerItem("bandana",
            new BandanaItem(new Item.Settings().maxCount(1)));

    public static final Item CASCADE_STRIDERS = registerItem("cascade_striders",
            new CascadeStridersItem(ModArmorMaterial.CASCADE_STRIDERS, ArmorItem.Type.BOOTS, new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1)));

    public static final Item SLIME_BOOTS = registerItem("slime_boots",
            new SlimeBootsItem(ModArmorMaterial.SLIME_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));

    public static final Item BREEZE_JAR = registerItem("breeze_jar",
            new BreezeJarItem(new Item.Settings().rarity(Rarity.UNCOMMON).component(ModComponents.BREEZE_JAR_DASHES, 2).maxCount(1)));

    public static final Item MAGIC_MIRROR = registerItem("magic_mirror",
            new MagicMirrorItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1)));

    public static final Item ENDER_MIRROR = registerItem("ender_mirror",
            new EnderMirrorItem(new Item.Settings().rarity(Rarity.UNCOMMON).component(ModComponents.ENDER_MIRROR_POS, Vec3d.ZERO)
                    .component(ModComponents.ENDER_MIRROR_DIMENSION, "none").maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, ToadsToolbox.id(name), item);
    }

    public static void initialize() {}
}
