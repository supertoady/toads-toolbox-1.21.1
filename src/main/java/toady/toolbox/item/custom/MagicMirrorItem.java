package toady.toolbox.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.effects.ModParticles;
import toady.toolbox.effects.ModSounds;

import java.util.List;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && !user.getItemCooldownManager().isCoolingDown(this)){
            if (user.getServer() != null){
                ServerPlayerEntity serverPlayerEntity = user.getServer().getPlayerManager().getPlayer(user.getUuid());
                if (serverPlayerEntity != null){
                    BlockPos pos = serverPlayerEntity.getSpawnPointPosition();
                    if (pos != null){
                        BlockState state = world.getBlockState(pos);
                        Vec3d respawnPos = pos.toCenterPos();

                        if (serverPlayerEntity.getSpawnPointDimension() == world.getRegistryKey()){
                            ToadsToolbox.showParticlesToAll(world, ModParticles.MAGIC_MIRROR, user.getPos().add(0, 1, 0), 0.3, 15, 0.02);
                            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);
                            Vec3d vecPos = respawnPos;
                            if (!state.isOf(Blocks.AIR)){
                                user.requestTeleport(respawnPos.x, respawnPos.y + 1, respawnPos.z);
                            }
                            else {
                                vecPos = world.getSpawnPos().toCenterPos();
                                user.requestTeleport(vecPos.x, vecPos.y + 1, vecPos.z);
                            }
                            user.fallDistance = 0;
                            user.getItemCooldownManager().set(this, user.isCreative() ? 100 : 500);

                            ToadsToolbox.showParticlesToAll(world, ModParticles.MAGIC_MIRROR, vecPos.add(0, 1, 0), 0.3, 15, 0.02);
                            world.playSound(null, vecPos.x, vecPos.y, vecPos.z, ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);
                            return TypedActionResult.success(user.getStackInHand(hand));
                        }
                        else {
                            user.sendMessage(Text.of("§bThe set spawn position is in another dimension."), true);
                            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_INVALID, SoundCategory.PLAYERS, 1, 1);
                        }
                    }
                    else {
                        pos = world.getSpawnPos();
                        Vec3d vecPos = pos.toCenterPos();
                        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);
                        world.playSound(null, vecPos.x, vecPos.y, vecPos.z, ModSounds.ITEM_MAGIC_MIRROR_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1);

                        user.requestTeleport(vecPos.x, vecPos.y + 1, vecPos.z);
                        user.fallDistance = 0;
                        user.getItemCooldownManager().set(this, user.isCreative() ? 100 : 500);
                        return TypedActionResult.success(user.getStackInHand(hand));
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (ToadsToolbox.hasShiftDown()){
            tooltip.add(Text.literal("§7On Right Click:"));
            tooltip.add(Text.literal("§8Teleports you to your spawn point. Does not work"));
            tooltip.add(Text.literal("§8if your spawn point is in another dimension."));
        }
        else {
            tooltip.add(Text.literal("§8Press [§7Sneak§8] for Summary"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
