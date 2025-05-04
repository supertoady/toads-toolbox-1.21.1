package toady.toolbox.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.effects.ModParticles;
import toady.toolbox.item.ModComponents;
import toady.toolbox.effects.ModSounds;

import java.util.List;
import java.util.Objects;

public class EnderMirrorItem extends Item {
    public EnderMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.getItemCooldownManager().isCoolingDown(this)){

            ItemStack stack = user.getStackInHand(hand);
            if (user.isSneaking()){
                if (isBound(stack)) {
                    user.sendMessage(Text.of("§aSuccesfully unbound target position."), true);
                    world.playSound(null, user.getBlockPos(), ModSounds.ITEM_ENDER_MIRROR_BIND, SoundCategory.PLAYERS);

                    stack.set(ModComponents.ENDER_MIRROR_POS, Vec3d.ZERO);
                    stack.set(ModComponents.ENDER_MIRROR_DIMENSION, "none");
                }
                else {
                    user.sendMessage(Text.of("§aSuccesfully bound target position."), true);
                    world.playSound(null, user.getBlockPos(), ModSounds.ITEM_ENDER_MIRROR_BIND, SoundCategory.PLAYERS);

                    stack.set(ModComponents.ENDER_MIRROR_POS, user.getPos());
                    stack.set(ModComponents.ENDER_MIRROR_DIMENSION, world.getRegistryKey().toString());
                }
                user.getItemCooldownManager().set(this, 20);
                user.setStackInHand(hand, stack);
                return TypedActionResult.success(user.getStackInHand(hand));
            }

            if (isBound(stack)){
                Vec3d pos = stack.get(ModComponents.ENDER_MIRROR_POS);
                assert pos != null;

                if (Objects.equals(stack.get(ModComponents.ENDER_MIRROR_DIMENSION), world.getRegistryKey().toString())){
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);
                    ToadsToolbox.showParticlesToAll(world, ModParticles.ENDER_MIRROR, user.getPos().add(0, 1, 0), 0.3, 15, 0.02);
                    user.requestTeleport(pos.x, pos.y, pos.z);

                    world.playSound(null, pos.x, pos.y, pos.z, ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);
                    ToadsToolbox.showParticlesToAll(world, ModParticles.ENDER_MIRROR, pos.add(0, 1, 0), 0.3, 15, 0.02);

                    user.fallDistance = 0;
                    user.getItemCooldownManager().set(this, user.isCreative() ? 100 : 500);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
                else {
                    user.sendMessage(Text.of("§aThe set target position is in another dimension."), true);
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_INVALID, SoundCategory.PLAYERS, 1, 1);
                }
            }
            else {
                user.sendMessage(Text.of("§aNo target position set."), true);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_INVALID, SoundCategory.PLAYERS, 1, 1);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return isBound(stack);
    }

    public boolean isBound(ItemStack stack){
        return !Objects.equals(stack.get(ModComponents.ENDER_MIRROR_DIMENSION), "none");
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (isBound(stack)){
            Vec3d pos = stack.get(ModComponents.ENDER_MIRROR_POS);
            assert pos != null;
            Vec3i posi = new Vec3i((int) Math.round(pos.x), (int) Math.round(pos.y), (int) Math.round(pos.z));
            tooltip.add(Text.of("§8Bound to §7(" + posi.getX() + ", " + posi.getY() + ", " + posi.getZ() + ")"));
            tooltip.add(Text.of(" "));
        }

        if (ToadsToolbox.hasShiftDown()){
            tooltip.add(Text.literal("§7On Shift+Right Click:"));
            tooltip.add(Text.literal("§8Binds/unbinds the mirror to a certain position."));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.literal("§7On Right Click:"));
            tooltip.add(Text.literal("§8Teleports you to the mirror's bound position."));
            tooltip.add(Text.literal("§8Does not work if the bound position is in"));
            tooltip.add(Text.literal("§8another dimension."));
        }
        else {
            tooltip.add(Text.literal("§8Press [§7Sneak§8] for Summary"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
