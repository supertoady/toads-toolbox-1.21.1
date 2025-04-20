package toady.toolbox.effects;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import toady.toolbox.ToadsToolbox;

public class ModSounds {
    public static SoundEvent ITEM_SLIME_BOOTS_BOUNCE = registerSound("item.slime_boots.bounce");
    public static SoundEvent ITEM_BREEZE_JAR_DASH = registerSound("item.breeze_jar.dash");
    public static SoundEvent ITEM_BREEZE_JAR_ULTRADASH = registerSound("item.breeze_jar.ultradash");
    public static SoundEvent ITEM_BREEZE_JAR_DASH_WATER = registerSound("item.breeze_jar.dash_water");
    public static SoundEvent ITEM_MAGIC_MIRROR_TELEPORT = registerSound("item.magic_mirror.teleport");
    public static SoundEvent ITEM_MAGIC_MIRROR_INVALID = registerSound("item.magic_mirror.invalid");
    public static SoundEvent ITEM_ENDER_MIRROR_BIND = registerSound("item.ender_mirror.bind");

    private static SoundEvent registerSound(String name){
        Identifier id = ToadsToolbox.id(name);
        Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
        return SoundEvent.of(id);
    }

    public static void initialize() {}
}
