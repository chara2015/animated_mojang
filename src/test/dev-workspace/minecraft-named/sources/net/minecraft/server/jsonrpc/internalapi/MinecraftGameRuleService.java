package net.minecraft.server.jsonrpc.internalapi;

import java.util.stream.Stream;
import net.minecraft.server.jsonrpc.methods.ClientInfo;
import net.minecraft.server.jsonrpc.methods.GameRulesService;
import net.minecraft.world.level.gamerules.GameRule;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/internalapi/MinecraftGameRuleService.class */
public interface MinecraftGameRuleService {
    <T> GameRulesService.GameRuleUpdate<T> updateGameRule(GameRulesService.GameRuleUpdate<T> gameRuleUpdate, ClientInfo clientInfo);

    <T> T getRuleValue(GameRule<T> gameRule);

    <T> GameRulesService.GameRuleUpdate<T> getTypedRule(GameRule<T> gameRule, T t);

    Stream<GameRule<?>> getAvailableGameRules();
}
