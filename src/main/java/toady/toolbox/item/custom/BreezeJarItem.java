package toady.toolbox.item.custom;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModComponents;
import toady.toolbox.effects.ModSounds;

import java.util.List;
import java.util.Objects;

public class BreezeJarItem extends Item {
    public BreezeJarItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.get(ModComponents.BREEZE_JAR_DASHES) != null){
            float dashes = Objects.requireNonNull(stack.get(ModComponents.BREEZE_JAR_DASHES));
            return Math.round((dashes / 2) * 13);
        }
        return 13;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ColorHelper.Argb.fromFloats(1.0F, 0.749F, 0.686F, 0.788F);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.get(ModComponents.BREEZE_JAR_DASHES) != null){
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                int dashes = Objects.requireNonNull(stack.get(ModComponents.BREEZE_JAR_DASHES));
                if (dashes > 0) {
                    stack.set(ModComponents.BREEZE_JAR_DASHES, dashes - 1);
                    for(int i = 0; i < user.getInventory().size(); ++i) {
                        ItemStack inventoryStack = user.getInventory().getStack(i);
                        if (inventoryStack.getItem() instanceof BreezeJarItem) {
                            if (inventoryStack.get(ModComponents.BREEZE_JAR_DASHES) != null && inventoryStack != stack) {
                                inventoryStack.set(ModComponents.BREEZE_JAR_DASHES, dashes - 1);
                            }
                        }
                    }

                    user.getItemCooldownManager().set(this, 5);

                    Vec3d dir = user.getRotationVector().normalize();
                    user.addVelocity(new Vec3d(dir.x, dir.y, dir.z));
                    user.velocityDirty = true;
                    user.fallDistance = 0;

                    if (!world.isClient) {
                        ToadsToolbox.showParticlesToAll(world, ParticleTypes.CLOUD, user.getPos(), 0.3, 15, 0);
                        world.playSound(null, user.getBlockPos(), ModSounds.ITEM_BREEZE_JAR_DASH, SoundCategory.PLAYERS);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int dashes = Objects.requireNonNull(stack.get(ModComponents.BREEZE_JAR_DASHES));
        if (entity.isOnGround() && entity instanceof PlayerEntity player) {
            stack.set(ModComponents.BREEZE_JAR_DASHES, 2);
            if (dashes == 0){
                player.getItemCooldownManager().set(this, 40);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (ToadsToolbox.hasShiftDown()){
            tooltip.add(Text.literal("§7On Right Click:"));
            tooltip.add(Text.literal("§8Allows you to dash twice midair. Only refreshes"));
            tooltip.add(Text.literal("§8your dash when you touch the ground."));
        }
        else {
            tooltip.add(Text.literal("§8Press [§7Sneak§8] for Summary"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
