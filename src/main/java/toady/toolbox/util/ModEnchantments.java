package toady.toolbox.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> MOLTENWALKER = of("moltenwalker");
    public static final RegistryKey<Enchantment> LEAPING = of("leaping");
    public static final RegistryKey<Enchantment> JETSTREAM = of("jetstream");
    public static final RegistryKey<Enchantment> GUST = of("gust");

    private static RegistryKey<Enchantment> of(String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, ToadsToolbox.id(name));
    }

    public static int getLevel(World world, RegistryKey<Enchantment> enchant, ItemStack stack){
        RegistryEntryLookup<Enchantment> lookup = world.getRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.ENCHANTMENT);
        Optional<RegistryEntry.Reference<Enchantment>> opt = lookup.getOptional(enchant);
        if (opt.isPresent()){
            if (stack.get(DataComponentTypes.ENCHANTMENTS) != null){
                return opt.map(enchantmentReference -> Objects.requireNonNull(
                        stack.get(DataComponentTypes.ENCHANTMENTS)).getLevel(enchantmentReference)).orElse(0);
            }
            else return 0;
        }
        else return 0;
    }

    public static ItemStack getBook(World world, RegistryKey<Enchantment> enchant, int lvl){
        RegistryEntryLookup<Enchantment> lookup = world.getRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.ENCHANTMENT);
        Optional<RegistryEntry.Reference<Enchantment>> opt = lookup.getOptional(enchant);
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        opt.ifPresent(entry -> stack.set(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(entry, lvl))));
        return stack.get();
    }

    public static void init() {
    }
}
