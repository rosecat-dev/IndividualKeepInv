package rose.individualkeepinv;

import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.literal;
import static rose.individualkeepinv.KeepInvMap.kim;

public class IndividualKeepInv implements ModInitializer {
	public static final String MOD_ID = "individualkeepinv";
	public static final Logger LOGGER = LoggerFactory.getLogger("individualkeepinv");

	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.JOIN.register(KeepInvMap::onJoin);

		ServerPlayerEvents.AFTER_RESPAWN.register(KeepInvMap::onRespawn);

		CommandRegistrationCallback.EVENT.register(IKeepInvCommand::commandLogic);
	}
}