package toady.toolbox.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import toady.toolbox.item.ModItems;

public class ModLootTableModifiers {
    private static final Identifier TRIAL_VAULT_ID
            = Identifier.ofVanilla("chests/trial_chambers/reward_common");
    private static final Identifier STRONGHOLD_LIBRARY_ID
            = Identifier.ofVanilla( "chests/stronghold_library");
    private static final Identifier NETHER_FORTRESS_ID
            = Identifier.ofVanilla("chests/nether_bridge");
    private static final Identifier BURIED_CHEST_ID
            = Identifier.ofVanilla("chests/buried_treasure");
    private static final Identifier END_CITY_ID
            = Identifier.ofVanilla("chests/end_city_treasure");

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            if(TRIAL_VAULT_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) // 10% chance
                        .with(ItemEntry.builder(ModItems.BREEZE_JAR))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if(STRONGHOLD_LIBRARY_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.75f)) // 75% chance
                        .with(ItemEntry.builder(ModItems.MAGIC_MIRROR))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            RegistryWrapper.Impl<Enchantment> wrapper = registry.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
            if(NETHER_FORTRESS_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.3f)) // 30% chance
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK))
                        .apply(new SetEnchantmentsLootFunction.Builder().enchantment(wrapper.getOrThrow(ModEnchantments.MOLTENWALKER), ConstantLootNumberProvider.create(1.0F)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(BURIED_CHEST_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.5f)) // 50% chance
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK))
                        .apply(new SetEnchantmentsLootFunction.Builder().enchantment(wrapper.getOrThrow(ModEnchantments.JETSTREAM), ConstantLootNumberProvider.create(1.0F)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(END_CITY_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) // 10% chance
                        .with(ItemEntry.builder(Items.ENCHANTED_BOOK))
                        .apply(new SetEnchantmentsLootFunction.Builder().enchantment(wrapper.getOrThrow(ModEnchantments.GUST), ConstantLootNumberProvider.create(1.0F)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(BURIED_CHEST_ID.equals(key.getValue())) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.0f)) // 100% chance
                        .with(ItemEntry.builder(ModItems.CASCADE_STRIDERS))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
