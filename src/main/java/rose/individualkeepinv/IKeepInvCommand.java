package rose.individualkeepinv;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class IKeepInvCommand {

    public static void commandLogic (CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("ikeepinv")

            .then(CommandManager.literal("get")
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .requires(source -> source.hasPermissionLevel(3))
                            .executes(ctx -> {
                                ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "target");
                                ctx.getSource().sendMessage(Text.of(player.getEntityName() + "'s inventory state is currently: " + KeepInvMap.getPlayerState(player)));
                                return 1;
                            })))

            .then(CommandManager.literal("set")
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .then(CommandManager.argument("boolean", BoolArgumentType.bool())
                                    .requires(source -> source.hasPermissionLevel(3))
                                    .executes(ctx -> {
                                        ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "target");
                                        boolean bool = BoolArgumentType.getBool(ctx, "boolean");
                                        KeepInvMap.setPlayerState(player, bool);
                                        ctx.getSource().sendMessage(Text.of(player.getEntityName() + "'s inventory state has been set to: " + bool));
                                        return 1;
                                    })))));
    }
}
