package rose.individualkeepinv.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static rose.individualkeepinv.KeepInvMap.kim;

@Mixin(PlayerEntity.class)
    public abstract class PlayerDeathMixin {
    @Final @Shadow PlayerInventory inventory;
    @Shadow protected void vanishCursedItems() {}

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void onDropInventory(CallbackInfo info) {
        PlayerEntity player = ((PlayerEntity) (Object) this); // Getting the "this" instance in the PlayerEntity class
            if (!kim.invStateMap.get(player.getUuid())) {
                // Not using the player variable here as the @Shadow annotation allows me to use the "this" instance directly
                this.vanishCursedItems();
                this.inventory.dropAll();
            }
            info.cancel();
    }
}
