package rose.individualkeepinv.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static rose.individualkeepinv.KeepInvMap.kim;

@Mixin(PlayerEntity.class)
public abstract class PlayerXpMixin {

    @Inject(method = "getExperienceToDrop", at = @At("HEAD"), cancellable = true)
    public void onGetExperienceToDrop(CallbackInfoReturnable<Integer> info) {
        PlayerEntity player = ((PlayerEntity) (Object) this); // Getting the "this" instance in the PlayerEntity class
        if (kim.invStateMap.get(player.getUuid()) || player.isSpectator()) {
            info.setReturnValue(0);
        }
        else {
            int i = player.experienceLevel * 7;
            if (i > 100) {
                info.setReturnValue(100);
            }
            info.setReturnValue(i);
        }
    }
}
