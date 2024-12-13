package com.github.stefvanschie.quickskript.bukkit.skript;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;
import com.destroystokyo.paper.event.player.PlayerStopSpectatingEntityEvent;
import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.integration.region.RegionIntegration;
import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.bukkit.event.ComplexEventProxyFactory;
import com.github.stefvanschie.quickskript.bukkit.event.EventProxyFactory;
import com.github.stefvanschie.quickskript.bukkit.event.SimpleEventProxyFactory;
import com.github.stefvanschie.quickskript.bukkit.psi.condition.*;
import com.github.stefvanschie.quickskript.bukkit.psi.effect.*;
import com.github.stefvanschie.quickskript.bukkit.psi.expression.*;
import com.github.stefvanschie.quickskript.bukkit.psi.function.PsiLocationFunctionImpl;
import com.github.stefvanschie.quickskript.bukkit.psi.function.PsiVectorFunctionImpl;
import com.github.stefvanschie.quickskript.bukkit.psi.function.PsiWorldFunctionImpl;
import com.github.stefvanschie.quickskript.bukkit.psi.literal.PsiMoneyLiteralImpl;
import com.github.stefvanschie.quickskript.bukkit.psi.literal.PsiPlayerLiteralImpl;
import com.github.stefvanschie.quickskript.bukkit.skript.util.ExecutionTarget;
import com.github.stefvanschie.quickskript.bukkit.util.*;
import com.github.stefvanschie.quickskript.bukkit.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.QuickSkriptPostEnableEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.ServerTickEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.WorldTimeChangeEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.region.RegionEnterEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.region.RegionEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.region.RegionLeaveEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.script.ScriptLoadEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.script.ScriptUnloadEvent;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileLine;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.TypeGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.condition.*;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiChangeEffect;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiContinueEffect;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiDoIfEffect;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiExitEffect;
import com.github.stefvanschie.quickskript.core.psi.expression.*;
import com.github.stefvanschie.quickskript.core.psi.function.*;
import com.github.stefvanschie.quickskript.core.psi.literal.*;
import com.github.stefvanschie.quickskript.core.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.core.psi.section.PsiWhile;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.*;
import com.github.stefvanschie.quickskript.core.util.literal.Color;
import com.github.stefvanschie.quickskript.core.util.literal.GameMode;
import com.github.stefvanschie.quickskript.core.util.literal.TreeType;
import com.github.stefvanschie.quickskript.core.util.literal.WeatherType;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import com.github.stefvanschie.quickskript.core.util.registry.EntityTypeRegistry;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.*;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Instances of this class contain everything necessary for loading Skript files.
 * This allows addons to be easily loaded, as well as dropping all data after
 * all Skripts have been loaded (in order to save memory).
 * This means that the static modifier should only be used when the data is required
 * to also be present when the Skripts are being ran, not only when they are being loaded.
 *
 * @since 0.1.0
 */
public class BukkitSkriptLoader extends SkriptLoader {

    /**
     * The run environment this instance operates in.
     * While loading should be separate from execution,
     * it's not possible to separate event listener registration
     * and command registration from the runtime.
     */
    private final SkriptRunEnvironment environment;

    /**
     * A list of all event proxy factories.
     */
    private List<EventProxyFactory> events;

    /**
     * The wrapper that allows the creation and registration of commands.
     */
    @NotNull
    private final CommandMapWrapper commandMapWrapper = new CommandMapWrapper();

    /**
     * Creates a new Bukkit skript loader.
     *
     * @param environment the run environment this instance operates in.
     * While loading should be separate from execution,
     * it's not possible to separate event listener registration
     * and command registration from the runtime.
     * @since 0.1.0
     */
    public BukkitSkriptLoader(@NotNull SkriptRunEnvironment environment) {
        this.environment = environment;

        RegionIntegration regionIntegration = QuickSkript.getInstance().getRegionIntegration();

        getRegionRegistry().addRegions(regionIntegration.getRegions());
    }

    @Override
    public void registerDefaultElements() {
        //effects
        //these are at the top, cause they are always the outermost element
        registerElement(new PsiActionBarEffectImpl.Factory());
        registerElement(new PsiBanEffectImpl.Factory());
        registerElement(new PsiCancelEventEffectImpl.Factory());
        registerElement(new PsiChangeEffect.Factory());
        registerElement(new PsiCloseInventoryEffectImpl.Factory());
        registerElement(new PsiCommandEffectImpl.Factory());
        registerElement(new PsiContinueEffect.Factory());
        registerElement(new PsiDoIfEffect.Factory());
        registerElement(new PsiExitEffect.Factory());
        registerElement(new PsiExplosionEffectImpl.Factory());
        registerElement(new PsiFeedEffectImpl.Factory());
        registerElement(new PsiFlyEffectImpl.Factory());
        registerElement(new PsiHidePlayerFromServerListEffectImpl.Factory());
        registerElement(new PsiKickEffectImpl.Factory());
        registerElement(new PsiKillEffectImpl.Factory());
        registerElement(new PsiLoadServerIconEffectImpl.Factory());
        registerElement(new PsiLogEffectImpl.Factory());
        registerElement(new PsiMessageEffectImpl.Factory());
        registerElement(new PsiOpEffectImpl.Factory());
        registerElement(new PsiPlayerInfoEffectImpl.Factory(), Platform.PAPER);
        registerElement(new PsiPlayerVisibilityEffectImpl.Factory());
        registerElement(new PsiResetTitleEffectImpl.Factory());
        registerElement(new PsiSayEffectImpl.Factory());
        registerElement(new PsiShearEffectImpl.Factory());
        registerElement(new PsiToggleFlightEffectImpl.Factory());
        registerElement(new PsiUnbanEffectImpl.Factory());

        registerElement(new PsiParseExpression.Factory());

        //conditions
        registerElement(new PsiCanBuildConditionImpl.Factory());
        registerElement(new PsiCanFlyConditionImpl.Factory());
        registerElement(new PsiCanHoldConditionImpl.Factory());
        registerElement(new PsiCanPickUpItemsConditionImpl.Factory());
        registerElement(new PsiCanSeeConditionImpl.Factory());
        registerElement(new PsiChanceCondition.Factory());
        registerElement(new PsiContainsItemTypeConditionImpl.Factory());
        registerElement(new PsiContainsTextCondition.Factory());
        registerElement(new PsiContainsObjectCondition.Factory()); //explicitly after contains texts, otherwise this would match texts as well
        registerElement(new PsiDamageCauseConditionImpl.Factory());
        registerElement(new PsiDoRespawnAnchorsWorkConditionImpl.Factory());
        registerElement(new PsiEggWillHatchConditionImpl.Factory());
        registerElement(new PsiEndsWithCondition.Factory());
        registerElement(new PsiEntityIsInBubbleColumnConditionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiEntityIsInLavaConditionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiEntityIsInRainConditionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiEntityIsInWaterConditionImpl.Factory());
        registerElement(new PsiEntityIsShearedConditionImpl.Factory());
        registerElement(new PsiEntityIsWetConditionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiEventCancelledConditionImpl.Factory());
        registerElement(new PsiExistsCondition.Factory());
        registerElement(new PsiHasAIConditionImpl.Factory());
        registerElement(new PsiHasChatFilteringConditionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiHasClientWeatherConditionImpl.Factory());
        registerElement(new PsiHasPassed.Factory());
        registerElement(new PsiHasPermissionConditionImpl.Factory());
        registerElement(new PsiHasPlayedBeforeConditionImpl.Factory());
        registerElement(new PsiHasScoreboardTagConditionImpl.Factory());
        registerElement(new PsiIsAdultConditionImpl.Factory());
        registerElement(new PsiIsAliveConditionImpl.Factory());
        registerElement(new PsiIsAlphanumericCondition.Factory());
        registerElement(new PsiIsAnchorSpawnConditionImpl.Factory());
        registerElement(new PsiIsBabyConditionImpl.Factory());
        registerElement(new PsiIsBannedConditionImpl.Factory());
        registerElement(new PsiIsBedSpawnConditionImpl.Factory());
        registerElement(new PsiIsBlockConditionImpl.Factory());
        registerElement(new PsiIsBlockingConditionImpl.Factory());
        registerElement(new PsiIsBurningConditionImpl.Factory());
        registerElement(new PsiIsFlyingConditionImpl.Factory());
        registerElement(new PsiIsOnGroundConditionImpl.Factory());
        registerElement(new PsiIsOnlineConditionImpl.Factory());
        registerElement(new PsiIsPoisonedConditionImpl.Factory());
        registerElement(new PsiIsSleepingConditionImpl.Factory());
        registerElement(new PsiIsSneakingConditionImpl.Factory());
        registerElement(new PsiIsSprintingConditionImpl.Factory());
        registerElement(new PsiIsSwimmingConditionImpl.Factory());
        registerElement(new PsiStartsWithCondition.Factory());

        registerElement(new PsiIsCondition.Factory());

        //literals
        registerElement(new PsiBiomeLiteral.Factory());
        registerElement(new PsiBlockDataLiteral.Factory());
        registerElement(new PsiBooleanLiteral.Factory());
        registerElement(new PsiCatTypeLiteral.Factory());
        registerElement(new PsiClickTypeLiteral.Factory());
        registerElement(new PsiColorLiteral.Factory());
        registerElement(new PsiDamageCauseLiteral.Factory());
        registerElement(new PsiEnchantmentLiteral.Factory());
        registerElement(new PsiEntityTypeLiteral.Factory());
        registerElement(new PsiExperienceLiteral.Factory());
        registerElement(new PsiFireworkTypeLiteral.Factory());
        registerElement(new PsiGameModeLiteral.Factory());
        registerElement(new PsiGeneLiteral.Factory());
        registerElement(new PsiInventoryActionLiteral.Factory());
        registerElement(new PsiInventoryTypeLiteral.Factory());
        registerElement(new PsiNumberLiteral.Factory());
        registerElement(new PsiPlayerLiteralImpl.Factory());
        registerElement(new PsiRegionLiteral.Factory());
        registerElement(new PsiResourcePackStatus.Factory());
        registerElement(new PsiSoundCategoryLiteral.Factory());
        registerElement(new PsiSpawnReasonLiteral.Factory());
        registerElement(new PsiStatusEffectTypeLiteral.Factory());
        registerElement(new PsiStringLiteral.Factory());
        registerElement(new PsiTeleportCauseLiteral.Factory());
        registerElement(new PsiTimeLiteral.Factory());
        registerElement(new PsiTimePeriodLiteral.Factory());
        registerElement(new PsiTimeSpanLiteral.Factory());
        registerElement(new PsiTreeTypeLiteral.Factory());
        registerElement(new PsiTypeLiteral.Factory());
        registerElement(new PsiVisualEffectLiteral.Factory());
        registerElement(new PsiWeatherTypeLiteral.Factory());
        registerElement(new PsiWorldLiteral.Factory());

        //expressions
        registerElement(new PsiAbsoluteDirectionExpression.Factory());
        registerElement(new PsiAffectedEntitiesExpressionImpl.Factory());
        registerElement(new PsiAlphabeticalSortExpression.Factory());
        registerElement(new PsiAmountExpression.Factory());
        registerElement(new PsiArithmeticExpression.Factory());
        registerElement(new PsiAtExpression.Factory());
        registerElement(new PsiAttackerExpressionImpl.Factory());
        registerElement(new PsiBukkitVersionExpressionImpl.Factory());
        registerElement(new PsiCapitalizationTextExpression.Factory());
        registerElement(new PsiChatMessageExpressionImpl.Factory());
        registerElement(new PsiClickedInventoryExpressionImpl.Factory());
        registerElement(new PsiCommandExpressionImpl.Factory());
        registerElement(new PsiCommandSenderExpressionImpl.Factory());
        registerElement(new PsiConsoleSenderExpressionImpl.Factory());
        registerElement(new PsiDamageExpressionImpl.Factory());
        registerElement(new PsiDeathMessageExpressionImpl.Factory());
        registerElement(new PsiDefaultMOTDExpressionImpl.Factory());
        registerElement(new PsiDefaultServerIconExpressionImpl.Factory());
        registerElement(new PsiDefaultValueExpression.Factory());
        registerElement(new PsiDirectionExpression.Factory());
        registerElement(new PsiDisplayedMOTDExpressionImpl.Factory());
        registerElement(new PsiElementOfExpression.Factory());
        registerElement(new PsiExhaustionExpressionImpl.Factory());
        registerElement(new PsiExperienceExpressionImpl.Factory());
        registerElement(new PsiFakeMaxPlayersExpressionImpl.Factory());
        registerElement(new PsiFakeOnlinePlayerCountExpressionImpl.Factory());
        registerElement(new PsiFilterExpression.Factory());
        registerElement(new PsiFilterInputExpression.Factory());
        registerElement(new PsiFinalDamageExpressionImpl.Factory());
        registerElement(new PsiFlyModeExpressionImpl.Factory());
        registerElement(new PsiFoodLevelExpressionImpl.Factory());
        registerElement(new PsiFormatDateTimeExpression.Factory());
        registerElement(new PsiGlidingStateExpressionImpl.Factory());
        registerElement(new PsiGlowingExpressionImpl.Factory());
        registerElement(new PsiGravityExpressionImpl.Factory());
        registerElement(new PsiHashExpression.Factory());
        registerElement(new PsiHealthExpressionImpl.Factory());
        registerElement(new PsiHiddenPlayersExpressionImpl.Factory(), Platform.SPIGOT);
        registerElement(new PsiHorizontalDirectionExpression.Factory());
        registerElement(new PsiHoverListExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiIndexOfExpression.Factory());
        registerElement(new PsiIndicesExpression.Factory());
        registerElement(new PsiInfinityExpression.Factory());
        registerElement(new PsiInitiatorInventoryExpressionImpl.Factory());
        registerElement(new PsiIPExpressionImpl.Factory());
        registerElement(new PsiJoinExpression.Factory());
        registerElement(new PsiJoinMessageExpressionImpl.Factory());
        registerElement(new PsiLanguageExpressionImpl.Factory());
        registerElement(new PsiLeashHolderExpressionImpl.Factory());
        registerElement(new PsiLeaveMessageExpressionImpl.Factory());
        registerElement(new PsiLengthExpression.Factory());
        registerElement(new PsiLevelExpressionImpl.Factory());
        registerElement(new PsiLevelProgressExpressionImpl.Factory());
        registerElement(new PsiListExpression.Factory());
        registerElement(new PsiLoadedServerIconExpressionImpl.Factory());
        registerElement(new PsiLocationAtExpression.Factory());
        registerElement(new PsiMaxHealthExpressionImpl.Factory());
        registerElement(new PsiMeExpressionImpl.Factory());
        registerElement(new PsiMinecraftVersionExpressionImpl.Factory());
        registerElement(new PsiNaNExpression.Factory());
        registerElement(new PsiNowExpression.Factory());
        registerElement(new PsiNumbersExpression.Factory());
        registerElement(new PsiOfflinePlayersExpressionImpl.Factory());
        registerElement(new PsiPassengersExpressionImpl.Factory());
        registerElement(new PsiPermissionsExpressionImpl.Factory());
        registerElement(new PsiPingExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiPlayerListFooterExpressionImpl.Factory());
        registerElement(new PsiPlayerListHeaderExpressionImpl.Factory());
        registerElement(new PsiProtocolVersionExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiRandomExpression.Factory());
        registerElement(new PsiRandomNumberExpression.Factory());
        registerElement(new PsiRealMaxPlayersExpressionImpl.Factory());
        registerElement(new PsiRealOnlinePlayerCountExpressionImpl.Factory());
        registerElement(new PsiRoundExpression.Factory());
        registerElement(new PsiSaturationExpressionImpl.Factory());
        registerElement(new PsiScoreboardTagsExpressionImpl.Factory());
        registerElement(new PsiScriptNameExpression.Factory());
        registerElement(new PsiShownServerIconExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiShuffleExpression.Factory());
        registerElement(new PsiSkriptVersionExpression.Factory());
        registerElement(new PsiSortExpression.Factory());
        registerElement(new PsiSpeedExpressionImpl.Factory());
        registerElement(new PsiSplitExpression.Factory());
        registerElement(new PsiSubstringExpression.Factory());
        registerElement(new PsiTamerExpressionImpl.Factory());
        registerElement(new PsiTernaryExpression.Factory());
        registerElement(new PsiTPSExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiUnixTimestampExpression.Factory());
        registerElement(new PsiVehicleExpressionImpl.Factory());
        registerElement(new PsiVersionStringExpressionImpl.Factory(), Platform.PAPER);

        //functions
        registerElement(new PsiAbsoluteValueFunction.Factory());
        registerElement(new PsiAtan2Function.Factory());
        registerElement(new PsiCalculateExperienceFunction.Factory());
        registerElement(new PsiCeilFunction.Factory());
        registerElement(new PsiCosineFunction.Factory());
        registerElement(new PsiDateFunction.Factory());
        registerElement(new PsiExponentialFunction.Factory());
        registerElement(new PsiFloorFunction.Factory());
        registerElement(new PsiInverseCosineFunction.Factory());
        registerElement(new PsiInverseSineFunction.Factory());
        registerElement(new PsiInverseTangentFunction.Factory());
        registerElement(new PsiLocationFunctionImpl.Factory());
        registerElement(new PsiLogarithmFunction.Factory());
        registerElement(new PsiMaximumFunction.Factory());
        registerElement(new PsiMinimumFunction.Factory());
        registerElement(new PsiModuloFunction.Factory());
        registerElement(new PsiNaturalLogarithmFunction.Factory());
        registerElement(new PsiProductFunction.Factory());
        registerElement(new PsiRoundFunction.Factory());
        registerElement(new PsiSineFunction.Factory());
        registerElement(new PsiSquareRootFunction.Factory());
        registerElement(new PsiSumFunction.Factory());
        registerElement(new PsiTangentFunction.Factory());
        registerElement(new PsiVectorFunctionImpl.Factory());
        registerElement(new PsiWorldFunctionImpl.Factory());

        //these are slow and match a lot, therefore at the bottom
        registerElement(new PsiItemCategoryLiteral.Factory());
        registerElement(new PsiItemLiteral.Factory());
        registerElement(new PsiMoneyLiteralImpl.Factory());
    }

    @Override
    public void registerDefaultSections() {
        registerSection(new PsiIf.Factory());
        registerSection(new PsiWhile.Factory());
    }

    @Override
    public void registerDefaultConverters() {
        registerConverter("number", new PsiNumberLiteral.Converter());
        registerConverter("text", new PsiStringLiteral.Converter());
    }

    @Override
    @SuppressWarnings("HardcodedFileSeparator")
    public void registerDefaultEvents() {
        events = new ArrayList<>(); //this has to go here, because this method will be called before the class's initialization

        registerEvent(new SimpleEventProxyFactory()
            .registerEvent(AreaEffectCloudApplyEvent.class, "[on] (area|AoE) [cloud] effect")
            .registerEvent(PlayerAnimationEvent.class, "[on] [player] arm swing")
            .registerEvent("com.destroystokyo.paper.event.player.PlayerArmorChangeEvent", "[on] [player] armor change[d]", Platform.PAPER)
            .registerEvent(BlockCanBuildEvent.class, "[on] [block] can build check")
            .registerEvent(BlockDamageEvent.class, "[on] block damag(ing|e)")
            .registerEvent(BlockFertilizeEvent.class, "[on] [block] fertilize")
            .registerEvent(BlockFromToEvent.class, "[on] [block] (flow[ing]|mov(e|ing))")
            .registerEvent(BlockIgniteEvent.class, "[on] [block] ignit[e|ion]")
            .registerEvent(BlockPhysicsEvent.class, "[on] [block] physics")
            .registerEvent(BlockPistonExtendEvent.class, "[on] piston extend[ing]")
            .registerEvent(BlockPistonRetractEvent.class, "[on] piston retract[ing]")
            .registerEvent(BlockRedstoneEvent.class, "[on] redstone [current] [chang(e|ing)]")
            .registerEvent(BlockSpreadEvent.class, "[on] spread[ing]")
            .registerEvent(ChunkLoadEvent.class, "[on] chunk load[ing]")
            .registerEvent(ChunkPopulateEvent.class, "[on] chunk (generat|populat)(e|ing)")
            .registerEvent(ChunkUnloadEvent.class, "[on] chunk unload[ing]")
            .registerEvent(CreeperPowerEvent.class, "[on] creeper power")
            .registerEvent(EnchantItemEvent.class, "[on] [item] enchant")
            .registerEvent(EntityBreakDoorEvent.class, "[on] zombie break[ing] [a] [wood[en]] door")
            .registerEvent(EntityCombustEvent.class, "[on] combust[ing]")
            .registerEvent(EntityDismountEvent.class, "[on] dismount[ing]")
            .registerEvent(EntityExplodeEvent.class, "[on] explo(d(e|ing)|sion)")
            .registerEvent(EntityMountEvent.class, "[on] mount[ing]")
            .registerEvent(EntityPortalEnterEvent.class, "[on] (portal enter[ing]|entering [a] portal)")
            .registerEvent(EntityPortalEvent.class, "[on] entity portal")
            .registerEvent(EntityRegainHealthEvent.class, "[on] heal[ing]")
            .registerEvent(EntityResurrectEvent.class, "[on] [entity] resurrect[ion] [attempt]")
            .registerEvent(EntityTameEvent.class, "[on] [entity] tam(e|ing)")
            .registerEvent(EntityTargetEvent.class, "[on] [entity] [un[-]]target")
            .registerEvent(EntityToggleGlideEvent.class, "[on] (gliding state change|toggl(e|ing) gliding)")
            .registerEvent(EntityToggleSwimEvent.class, "[on] [entity] (toggl(e|ing) swim|swim toggl(e|ing))")
            .registerEvent(ExperienceOrbSpawnEvent.class, "[on] ([e]xp[erience] [orb] spawn|spawn of [a[n]] [e]xp[erience] [orb])")
            .registerEvent(ExplosionPrimeEvent.class, "[on] explosion prime")
            .registerEvent(FoodLevelChangeEvent.class, "[on] (food|hunger) (level|met(er|re)|bar) chang(e|ing)")
            .registerEvent(FurnaceBurnEvent.class, "[on] fuel burn[ing]")
            .registerEvent(FurnaceSmeltEvent.class, "[on] [ore] smelt[ing] [of ore]")
            .registerEvent(HorseJumpEvent.class, "[on] horse jump")
            .registerEvent(InventoryCloseEvent.class, "[on] inventory clos(e[d]|ing)")
            .registerEvent(InventoryOpenEvent.class, "[on] inventory open[ed]")
            .registerEvent(InventoryPickupItemEvent.class, "[on] inventory pick[ ]up")
            .registerEvent(LeavesDecayEvent.class, "[on] leaves decay[ing]")
            .registerEvent(LightningStrikeEvent.class, "[on] lightning [strike]")
            .registerEvent(PigZapEvent.class, "[on] pig[ ]zap")
            .registerEvent(PlayerBedEnterEvent.class, "[on] (bed enter[ing]|[player] enter[ing] [a] bed)")
            .registerEvent(PlayerBedLeaveEvent.class, "[on] (bed leav(e|ing)|[player] leav(e|ing) [a] bed)")
            .registerEvent(PlayerBucketEmptyEvent.class, "[on] [player] [empty[ing]] [a] bucket [empty[ing]]")
            .registerEvent(PlayerBucketFillEvent.class, "[on] [player] [fill[ing]] [a] bucket [fill[ing]]")
            .registerEvent(PlayerChangedWorldEvent.class, "[on] [player] world chang(ing|e[d])")
            /* TODO: deprecated, but no way to ensure that the underlying code can be executed safely async, in the
               future maybe do some analysis to switch over to the async variant if deemed safe. This should be the same
               as listening to the async variant and moving everything over to the main thread */
            .registerEvent(PlayerChatEvent.class, "[on] chat")
            .registerEvent(PlayerEggThrowEvent.class, "[on] (throw[ing] [of] [an] egg|[player] egg throw)")
            .registerEvent(PlayerFishEvent.class, "[on] [player] fish[ing]")
            .registerEvent(PlayerItemBreakEvent.class, "[on] [player] (tool break[ing]|break[ing] [a|the] tool)")
            .registerEvent(PlayerItemDamageEvent.class, "[on] item damag(e|ing)")
            .registerEvent(PlayerItemHeldEvent.class, "[on] [player['s]] (tool|item held|held item) chang(e|ing)")
            .registerEvent(PlayerItemMendEvent.class, "[on] item mend[ing]")
            .registerEvent(PlayerJoinEvent.class, "[on] [player] (log[ging] in|join[ing])")
            .registerEvent("com.destroystokyo.paper.event.player.PlayerJumpEvent", "[on] [player] jump[ing]", Platform.PAPER)
            .registerEvent(PlayerKickEvent.class, "[on] [player] [being] kick[ed]")
            .registerEvent(PlayerLevelChangeEvent.class, "[on] [player] level [change]")
            .registerEvent(PlayerLocaleChangeEvent.class, "[on] [player] ((language|locale) chang(e|ing)|chang(e|ing) (language|locale))")
            .registerEvent(PlayerLoginEvent.class, "[on] [player] connect[ing]")
            .registerEvent(PlayerMoveEvent.class, "[on] player (move|walk|step)")
            .registerEvent(PlayerPortalEvent.class, "[on] [player] portal")
            .registerEvent(PlayerQuitEvent.class, "[on] (quit[ting]|disconnect[ing]|log[ging | ]out)")
            .registerEvent(PlayerResourcePackStatusEvent.class, "[on] resource pack [request] response")
            .registerEvent(PlayerRespawnEvent.class, "[on] [player] respawn[ing]")
            .registerEvent(PlayerRiptideEvent.class, "[on] [use of] riptide [enchant[ment]]")
            .registerEvent(PlayerSwapHandItemsEvent.class, "[on] swap[ping of] [hand|held] item[s]")
            .registerEvent(PlayerTeleportEvent.class, "[on] [player] teleport[ing]")
            .registerEvent(PlayerToggleFlightEvent.class, "[on] [player] (flight toggl(e|ing)|toggl(e|ing) flight)")
            .registerEvent(PlayerToggleSneakEvent.class, "[on] [player] (toggl(e|ing) sneak|sneak toggl(e|ing))")
            .registerEvent(PlayerToggleSprintEvent.class, "[on] [player] (toggl(e|ing) sprint|sprint toggl(e|ing))")
            .registerEvent(PortalCreateEvent.class, "[on] portal creat(e|ion)")
            .registerEvent(PrepareItemEnchantEvent.class, "[on] [item] enchant prepare")
            .registerEvent("com.destroystokyo.paper.event.entity.ProjectileCollideEvent", "[on] projectile collide", Platform.PAPER)
            .registerEvent(ProjectileHitEvent.class, "[on] projectile hit")
            .registerEvent(ProjectileLaunchEvent.class, "[on] [projectile] shoot")
            .registerEvent(
                Platform.getPlatform() == Platform.PAPER ? PaperServerListPingEvent.class : ServerListPingEvent.class,
                "[on] server [list] ping"
            )
            .registerEvent(QuickSkriptPostEnableEvent.class, "[on] (server|skript) (start|load|enable)")
            .registerEvent(RegionEnterEvent.class, "[on] (region enter[ing]|enter[ing] [of] [a] region)")
            .registerEvent(RegionLeaveEvent.class, "[on] (region (leav(e|ing)|exit[ing])|(leav(e|ing)|exit[ing]) [of] [a] region)")
            .registerEvent(SheepRegrowWoolEvent.class, "[on] sheep [re]grow[ing] wool")
            .registerEvent(SignChangeEvent.class, "[on] (sign (chang[e]|edit)[ing]|[player] (chang[e]|edit)[ing] [a] sign)")
            .registerEvent(SlimeSplitEvent.class, "[on] slime split[ting]")
            .registerEvent(SpawnChangeEvent.class, "[on] [world] spawn change")
            .registerEvent(SpongeAbsorbEvent.class, "[on] sponge absorb")
            .registerEvent(VehicleCreateEvent.class, "[on] (vehicle create|creat(e|ing|ion of) [a] vehicle)")
            .registerEvent(VehicleDamageEvent.class, "[on] (vehicle damage|damag(e|ing) [a] vehicle)")
            .registerEvent(VehicleDestroyEvent.class, "[on] (vehicle destroy|destr(oy[ing]|uction of) [a] vehicle)")
            .registerEvent(VehicleEnterEvent.class, "[on] (vehicle enter|enter[ing] [a] vehicle)")
            .registerEvent(VehicleExitEvent.class, "[on] (vehicle exit|exit[ing] [a] vehicle)")
            .registerEvent(WorldInitEvent.class, "[on] world init[ialization]")
            .registerEvent(WorldLoadEvent.class, "[on] world load[ing]")
            .registerEvent(WorldSaveEvent.class, "[on] world sav(e|ing)")
            .registerEvent(WorldUnloadEvent.class, "[on] world unload[ing]")
        );

        registerEvent(new ComplexEventProxyFactory(this)
            .registerEvent(BlockBurnEvent.class, "[on] [block] burn[ing] [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length > 0) {
                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        return defaultItemTypeComparison((ItemType) object);
                    }

                    return event -> true;
                }

                return null;
            })
            .registerEvent(BlockDispenseEvent.class, "[on] dispens(e|ing) [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof ItemType)) {
                        continue;
                    }

                    Iterable<String> entries = ((ItemType) object).getAllKeys();

                    return event -> {
                        ItemStack item = event.getItem();

                        for (String entry : entries) {
                            if (ItemComparisonUtil.compare(item, entry)) {
                                return true;
                            }
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(BlockFadeEvent.class, "[on] [block] fad(e|ing) [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof ItemType)) {
                        continue;
                    }

                    return defaultItemTypeComparison((ItemType) object);
                }

                return null;
            })
            .registerEvent(BlockFormEvent.class, "[on] [block] form[ing] [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof ItemType)) {
                        continue;
                    }

                    Collection<BlockData> blockData = new HashSet<>();

                    for (ItemTypeRegistry.Entry entry : ((ItemType) object).getItemTypeEntries()) {
                        blockData.add(Bukkit.createBlockData(entry.getFullNamespacedKey()));
                    }

                    return event -> {
                        BlockData eventData = event.getNewState().getBlockData();

                        for (BlockData data : blockData) {
                            if (eventData.matches(data)) {
                                return true;
                            }
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(BlockGrowEvent.class, "[on] ((plant|crop|block) grow[th|ing]|grow) [[of] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length > 0) {
                            Object object = elements[0].execute(null, null);

                            if (!(object instanceof ItemType)) {
                                continue;
                            }

                            return defaultItemTypeComparison((ItemType) object);
                        }

                        return event -> true;
                    }

                    return null;
                }
            )
            .registerEvent(BlockPlaceEvent.class, "[on] [block] (plac(e|ing)|build[ing]) [[of] %item types/block datas%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (object instanceof ItemType) {
                            return defaultItemTypeComparison((ItemType) object);
                        } else if (object instanceof com.github.stefvanschie.quickskript.core.util.literal.BlockData) {
                            var blockData = (com.github.stefvanschie.quickskript.core.util.literal.BlockData) object;

                            BlockData data = Bukkit.createBlockData(blockData.convertToString());

                            return event -> event.getBlock().getBlockData().matches(data);
                        }
                    }

                    return null;
                }
            )
            .registerEvent(CraftItemEvent.class, "[on] [player] craft[ing] [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof ItemType)) {
                        continue;
                    }

                    Iterable<String> entries = ((ItemType) object).getAllKeys();

                    return event -> {
                        ItemStack item = event.getRecipe().getResult();

                        for (String entry : entries) {
                            if (ItemComparisonUtil.compare(item, entry)) {
                                return true;
                            }
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(EntityDamageEvent.class, "[on] damag(e|ing) [of %entity type%]",
                defaultEntityComparison())
            .registerEvent(EntityDeathEvent.class, "[on] death [of %entity types%]", defaultEntityComparison())
            .registerEvent("io.papermc.paper.event.entity.EntityMoveEvent", "[on] %entity type% (move|walk|step)", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof EntityTypeRegistry.Entry)) {
                        continue;
                    }

                    EntityTypeRegistry.Entry entityType = (EntityTypeRegistry.Entry) object;

                    //already handled by the simple player move event
                    if (entityType.getName().equals("player")) {
                        return null;
                    }

                    return event -> {
                        Entity entity = ((EntityMoveEvent) event).getEntity();
                        EntityType type = entity.getType();

                        return equals(entityType, type);
                    };
                }

                return null;
            }, Platform.PAPER)
            .registerEvent(FireworkExplodeEvent.class, "[on] [a] firework explo(d(e|ing)|sion) [colo[u]red %colors%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof Color)) {
                            continue;
                        }

                        org.bukkit.Color fireworkColor = org.bukkit.Color.fromRGB(((Color) object).getFireworkColor());

                        return event -> {
                            Collection<org.bukkit.Color> colors = new HashSet<>();

                            for (FireworkEffect effect : event.getEntity().getFireworkMeta().getEffects()) {
                                colors.addAll(effect.getColors());
                            }

                            return colors.contains(fireworkColor);
                        };
                    }

                    return null;
                })
            .registerEvent(InventoryClickEvent.class, "[on] [player] inventory(-| )click[ing] [[at] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        Iterable<String> entries = ((ItemType) object).getAllKeys();

                        return event -> {
                            ItemStack item = event.getCurrentItem();

                            if (item == null) {
                                return false;
                            }

                            for (String entry : entries) {
                                if (ItemComparisonUtil.compare(item, entry)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return null;
                })
            .registerEvent(ItemDespawnEvent.class, "[on] ((item[ ][stack]|[item] %item types%) despawn[ing]|[item[ ][stack]] despawn[ing] [[of] %item types%])",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        Iterable<String> entries = ((ItemType) object).getAllKeys();

                        return event -> {
                            ItemStack item = event.getEntity().getItemStack();

                            for (String entry : entries) {
                                if (ItemComparisonUtil.compare(item, entry)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return null;
                })
            .registerEvent(ItemMergeEvent.class, "[on] ((item[ ][stack]|[item] %item types%) merg(e|ing)|item[ ][stack] merg(e|ing) [[of] %item types%])",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        Iterable<String> entries = ((ItemType) object).getAllKeys();

                        return event -> {
                            ItemStack item = event.getEntity().getItemStack();

                            for (String entry : entries) {
                                if (ItemComparisonUtil.compare(item, entry)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return null;
                })
            .registerEvent(ItemSpawnEvent.class, "[on] item spawn[ing] [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof ItemType)) {
                        continue;
                    }

                    Iterable<String> entries = ((ItemType) object).getAllKeys();

                    return event -> {
                        ItemStack item = event.getEntity().getItemStack();

                        for (String entry : entries) {
                            if (ItemComparisonUtil.compare(item, entry)) {
                                return true;
                            }
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(PlayerCommandPreprocessEvent.class, "[on] command [%text%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length > 0) {
                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof String)) {
                            continue;
                        }

                        String command = object.toString();

                        if (command.charAt(0) == '/') {
                            command = command.substring(1);
                        }

                        final String finalCommand = command;

                        return event -> {
                            String message = event.getMessage();

                            if (message.charAt(0) == '/') {
                                message = message.substring(1);
                            }

                            return finalCommand.equals(message);
                        };
                    }

                    return event -> true;
                }

                return null;
            })
            .registerEvent(PlayerDropItemEvent.class, "[on] [player] drop[ing] [[of] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        Iterable<String> entries = ((ItemType) object).getAllKeys();

                        return event -> {
                            ItemStack item = event.getItemDrop().getItemStack();

                            for (String entry : entries) {
                                if (ItemComparisonUtil.compare(item, entry)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return null;
                })
            .registerEvent(PlayerGameModeChangeEvent.class, "[on] game[ ]mode change [to %gamemode%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof GameMode)) {
                            continue;
                        }

                        org.bukkit.GameMode gameMode = org.bukkit.GameMode.valueOf(((GameMode) object).name());

                        return event -> event.getNewGameMode() == gameMode;
                    }

                    return null;
                })
            .registerEvent(PlayerItemConsumeEvent.class, "[on] [player] ((eat|drink)[ing]|consum(e|ing)) [[of] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length > 0) {
                            Object object = elements[0].execute(null, null);

                            if (!(object instanceof ItemType)) {
                                continue;
                            }

                            Iterable<String> entries = ((ItemType) object).getAllKeys();

                            return event -> {
                                ItemStack item = event.getItem();

                                for (String entry : entries) {
                                    if (ItemComparisonUtil.compare(item, entry)) {
                                        return true;
                                    }
                                }

                                return false;
                            };
                        }

                        return event -> true;
                    }

                    return null;
                })
            .registerEvent("com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent", "[on] [player] (swap|switch) spectating [(of|from) %*entity types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        PsiElement<?> element = elements[0];
                        Object object = element.execute(null, null);

                        if (!(object instanceof EntityTypeRegistry.Entry entityType)) {
                            continue;
                        }

                        return event -> {
                            PlayerStartSpectatingEntityEvent playerEvent = (PlayerStartSpectatingEntityEvent) event;

                            if (playerEvent.getCurrentSpectatorTarget().equals(playerEvent.getPlayer())) {
                                return false;
                            }

                            return equals(entityType, playerEvent.getNewSpectatorTarget().getType());
                        };
                    }

                    return null;
                }, Platform.PAPER)
            .registerEvent("com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent", "[on] [player] start spectating [of %*entity types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements.length == 0) {
                            return event -> {
                                PlayerStartSpectatingEntityEvent playerEvent = (PlayerStartSpectatingEntityEvent) event;

                                return playerEvent.getCurrentSpectatorTarget().equals(playerEvent.getPlayer());
                            };
                        }

                        PsiElement<?> element = elements[0];
                        Object object = element.execute(null, null);

                        if (!(object instanceof EntityTypeRegistry.Entry entityType)) {
                            continue;
                        }

                        return event -> {
                            PlayerStartSpectatingEntityEvent playerEvent = (PlayerStartSpectatingEntityEvent) event;

                            if (playerEvent.getCurrentSpectatorTarget().equals(playerEvent.getPlayer())) {
                                return equals(entityType, playerEvent.getNewSpectatorTarget().getType());
                            }

                            return false;
                        };
                    }

                    return null;
                }, Platform.PAPER)
            .registerEvent("com.destroystokyo.paper.event.player.PlayerStopSpectatingEntityEvent", "[on] [player] stop spectating [(of|from) %*entity types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        PsiElement<?> element = elements[0];
                        Object object = element.execute(null, null);

                        if (!(object instanceof EntityTypeRegistry.Entry entityType)) {
                            continue;
                        }

                        return event -> {
                            Entity spectatorTarget = ((PlayerStopSpectatingEntityEvent) event).getSpectatorTarget();

                            return equals(entityType, spectatorTarget.getType());
                        };
                    }

                    return null;
                }, Platform.PAPER)
            .registerEvent(PrepareItemCraftEvent.class, "[on] [player] (preparing|beginning) craft[ing] [[of] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ItemType)) {
                            continue;
                        }

                        Iterable<String> entries = ((ItemType) object).getAllKeys();

                        return event -> {
                            Recipe recipe = event.getRecipe();

                            if (recipe == null) {
                                return false;
                            }

                            ItemStack item = recipe.getResult();

                            for (String entry : entries) {
                                if (ItemComparisonUtil.compare(item, entry)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return null;
                })
            .registerEvent(PlayerResourcePackStatusEvent.class, "[on] resource pack [request] %resource pack states%",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null || elements.length != 1) {
                            continue;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof ResourcePackStatus)) {
                            continue;
                        }

                        ResourcePackStatus quickSkriptStatus = (ResourcePackStatus) object;
                        PlayerResourcePackStatusEvent.Status bukkitStatus = ResourcePackStatusUtil.convert(quickSkriptStatus);

                        return event -> event.getStatus() == bukkitStatus;
                    }

                    return null;
                }
            )
            .registerEvent(RegionEnterEvent.class, "[on] enter[ing] [of] [[the] region] %regions%",
                defaultRegionComparison())
            .registerEvent(RegionLeaveEvent.class, "[on] (leav(e|ing)|exit[ing]) [of] [[the] region] %regions%",
                defaultRegionComparison())
            .registerEvent(ScriptLoadEvent.class, "[on] [async] [script] (load|init|enable)", //TODO: async loading
                (script, event) -> event.getScript().equals(script))
            .registerEvent(ScriptUnloadEvent.class, "[on] [async] [script] (unload|stop|disable)", //TODO: async loading
                (script, event) -> event.getScript().equals(script))
            .registerEvent(StructureGrowEvent.class, "[on] grow [of %tree type%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length == 0) {
                        return event -> true;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof TreeType)) {
                        continue;
                    }

                    Collection<org.bukkit.TreeType> treeTypes = TreeTypeUtil.convert((TreeType) object);

                    return event -> treeTypes.contains(event.getSpecies());
                }

                return null;
            })
            .registerEvent(WorldTimeChangeEvent.class, "at %time% [in %worlds%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof Time)) {
                        continue;
                    }

                    long time = ((Time) object).asTicks();

                    if (elements.length > 1) {
                        Object world = elements[1].execute(null, null);

                        if (!(world instanceof World)) {
                            continue;
                        }

                        String worldName = ((World) world).getName();

                        return event -> event.getTime() == time && event.getWorld().getName().equals(worldName);
                    } else {
                        return event -> event.getTime() == time;
                    }
                }

                return null;
            })
            .registerEvent(BlockBreakEvent.class, "[on] [block] (break[ing]|min(e|ing)) [[of] %item types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length > 0) {
                            Object itemType = elements[0].execute(null, null);

                            if (!(itemType instanceof ItemType)) {
                                continue;
                            }

                            return defaultItemTypeComparison((ItemType) itemType);
                        }

                        return event -> true;
                    }

                    return null;
                })
            .registerEvent(EntityChangeBlockEvent.class, "[on] enderman place",
                matches -> event -> event.getEntity() instanceof Enderman && event.getTo() != Material.AIR)
            .registerEvent(EntityChangeBlockEvent.class, "[on] enderman pickup",
                matches -> event -> event.getEntity() instanceof Enderman && event.getTo() == Material.AIR)
            .registerEvent(EntityChangeBlockEvent.class, "[on] sheep eat", matches -> event ->
                event.getEntity() instanceof Sheep)
            .registerEvent(EntityChangeBlockEvent.class, "[on] silverfish enter",
                matches -> event ->
                    event.getEntity() instanceof Silverfish && EnumSet.of(
                        Material.INFESTED_COBBLESTONE,
                        Material.INFESTED_STONE,
                        Material.INFESTED_CHISELED_STONE_BRICKS,
                        Material.INFESTED_CRACKED_STONE_BRICKS,
                        Material.INFESTED_MOSSY_STONE_BRICKS,
                        Material.INFESTED_STONE_BRICKS
                    ).contains(event.getTo()))
            .registerEvent(EntityChangeBlockEvent.class, "[on] silverfish exit",
                matches -> event -> event.getEntity() instanceof Silverfish && event.getTo() == Material.AIR)
            .registerEvent(EntityChangeBlockEvent.class, "[on] falling block land[ing]", matches -> event ->
                event.getEntity() instanceof FallingBlock)
            .registerEvent(EntityPickupItemEvent.class, "[on] [1\u00A6player|entity] (pick[ ]up|picking up) [[of] %item types%]", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    if (elements.length > 0) {
                        Object itemType = elements[0].execute(null, null);

                        if (!(itemType instanceof ItemType)) {
                            continue;
                        }

                        Collection<String> keys = ((ItemType) itemType).getAllKeys();

                        return event -> {
                            if (match.getParseMark() == 1 && !(event.getEntity() instanceof Player)) {
                                return false;
                            }

                            for (String key : keys) {
                                if (ItemComparisonUtil.compare(event.getItem().getItemStack(), key)) {
                                    return true;
                                }
                            }

                            return false;
                        };
                    }

                    return event -> true;
                }

                return null;
            })
            .registerEvent(EntitySpawnEvent.class, "[on] spawn[ing] [of %entity types%]",
                defaultEntityComparison())
            .registerEvent(PlayerCommandPreprocessEvent.class, "[on] command %text%", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null) {
                        continue;
                    }

                    Object object = elements[0].execute(null, null);

                    if (!(object instanceof String)) {
                        continue;
                    }

                    String command = object.toString();
                    String finalCommand = command.startsWith("/") ? command.substring(1) : command;

                    return event -> {
                        String message = event.getMessage();
                        return message.startsWith(finalCommand, message.startsWith("/") ? 1 : 0);
                    };
                }

                return null;
            })
            .registerEvent(PlayerEditBookEvent.class, "[on] book (edit|change|write|1\u00A6sign|1\u00A6signing)",
                matches -> {
                    //iterates once
                    for (SkriptMatchResult match : matches) {
                        return match.getParseMark() == 1 ? PlayerEditBookEvent::isSigning : event -> !event.isSigning();
                    }

                    //will never be reached
                    return null;
                })
            .registerEvent(PlayerInteractEvent.class,
                "[on] [(1\u00A6right|2\u00A6left)[( |-)]][mouse[( |-)]]click[ing] ([4\u00A6on %item type%] [8\u00A6(with|using|holding) %item type%]|28\u00A6(with|using|holding) %item type% on %item type%)",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        int parseMark = match.getParseMark();

                        if (parseMark == 0) {
                            return event -> true;
                        }

                        EnumSet<Action> allowedActions = EnumSet.allOf(Action.class);
                        Collection<BlockData> targets = new HashSet<>();
                        Collection<String> holding = new HashSet<>();

                        if ((parseMark & 1) > 0) {
                            allowedActions = EnumSet.of(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
                        } else if ((parseMark & 2) > 0) {
                            allowedActions = EnumSet.of(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK);
                        }

                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        int elementIndex = 0;

                        if ((parseMark & 4) > 0) {
                            Object object = elements[0].execute(null, null);

                            if (!(object instanceof ItemType)) {
                                continue;
                            }

                            for (ItemTypeRegistry.Entry entry : ((ItemType) object).getItemTypeEntries()) {
                                String namespacedKey = entry.getFullNamespacedKey();

                                if ((parseMark & 16) > 0) {
                                    holding.add(namespacedKey);
                                } else {
                                    targets.add(Bukkit.createBlockData(namespacedKey));
                                }
                            }

                            elementIndex++;
                        }

                        if ((parseMark & 8) > 0) {
                            Object object = elements[elementIndex].execute(null, null);

                            if (!(object instanceof ItemType)) {
                                continue;
                            }

                            for (ItemTypeRegistry.Entry entry : ((ItemType) object).getItemTypeEntries()) {
                                String namespacedKey = entry.getFullNamespacedKey();

                                if ((parseMark & 16) > 0) {
                                    targets.add(Bukkit.createBlockData(namespacedKey));
                                } else {
                                    holding.add(namespacedKey);
                                }
                            }
                        }

                        final EnumSet<Action> finalAllowedActions = allowedActions;

                        return event -> {
                            if (!finalAllowedActions.contains(event.getAction())) {
                                return false;
                            }

                            Block clickedBlock = event.getClickedBlock();

                            if (clickedBlock != null) {
                                BlockData eventData = clickedBlock.getBlockData();

                                for (BlockData data : targets) {
                                    if (!eventData.matches(data)) {
                                        return false;
                                    }
                                }
                            } else if (!targets.isEmpty()) {
                                return false;
                            }

                            ItemStack item = event.getItem();

                            if (item != null) {
                                for (String data : holding) {
                                    if (!ItemComparisonUtil.compare(item, data)) {
                                        return false;
                                    }
                                }
                            } else if (!holding.isEmpty()) {
                                return false;
                            }

                            return true;
                        };
                    }

                    return null;
                }
            )
            .registerEvent(PlayerInteractAtEntityEvent.class,
                "[on] [(1\u00A6right|2\u00A6left)[( |-)]][mouse[( |-)]]click[ing] ([4\u00A6on %entity type%] [8\u00A6(with|using|holding) %item type%]|28\u00A6(with|using|holding) %item type% on %entity type%)",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        int parseMark = match.getParseMark();

                        if (parseMark == 0) {
                            return event -> true;
                        }

                        //we have to differentiate between this not being specified, being null and being actual text, so here's a nullable optional
                        //noinspection OptionalAssignedToNull
                        Optional<String> entityTypeKey = null;
                        Collection<String> holding = new HashSet<>();

                        if ((parseMark & 2) > 0) {
                            return event -> false;
                        }

                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        int elementIndex = 0;

                        if ((parseMark & 16) > 0) {
                            if ((parseMark & 4) > 0) {
                                PsiElement<?> element = elements[0];
                                Object itemType = element.execute(null, null);

                                if (!(itemType instanceof ItemType)) {
                                    continue;
                                }

                                holding.addAll(((ItemType) itemType).getAllKeys());

                                elementIndex++;
                            }

                            if ((parseMark & 8) > 0) {
                                Object entityType = elements[elementIndex].execute(null, null);

                                if (!(entityType instanceof EntityTypeRegistry.Entry)) {
                                    continue;
                                }

                                //may actually be null
                                entityTypeKey = Optional.ofNullable(((EntityTypeRegistry.Entry) entityType).getKey());
                            }
                        } else {
                            if ((parseMark & 4) > 0) {
                                Object entityType = elements[0].execute(null, null);

                                if (!(entityType instanceof EntityTypeRegistry.Entry)) {
                                    continue;
                                }

                                //may actually be null
                                entityTypeKey = Optional.ofNullable(((EntityTypeRegistry.Entry) entityType).getKey());

                                elementIndex++;
                            }

                            if ((parseMark & 8) > 0) {
                                PsiElement<?> element = elements[elementIndex];
                                Object itemType = element.execute(null, null);

                                if (!(itemType instanceof ItemType)) {
                                    continue;
                                }

                                holding.addAll(((ItemType) itemType).getAllKeys());
                            }
                        }

                        final Optional<String> finalEntityTypeKey = entityTypeKey;

                        return event -> {
                            Entity clickedEntity = event.getRightClicked();

                            //see comments above
                            //noinspection OptionalAssignedToNull
                            if (finalEntityTypeKey != null) {
                                EntityType type = clickedEntity.getType();
                                boolean isUnknown = type == EntityType.UNKNOWN;

                                //xor check: only return false if entity type is unknown and a key exists or entity type is not unknown, but a key doesn't exist
                                if (isUnknown != finalEntityTypeKey.isEmpty()) {
                                    return false;
                                }

                                if (!isUnknown) {
                                    NamespacedKey key = type.getKey();

                                    if (!finalEntityTypeKey.get().equals(key.getNamespace() + ':' + key.getKey())) {
                                        return false;
                                    }
                                }
                            }

                            PlayerInventory inventory = event.getPlayer().getInventory();
                            ItemStack item;

                            if (event.getHand() == EquipmentSlot.HAND) {
                                item = inventory.getItemInMainHand();
                            } else {
                                item = inventory.getItemInOffHand();
                            }

                            if (item.getType() != Material.AIR) {
                                for (String data : holding) {
                                    if (!ItemComparisonUtil.compare(item, data)) {
                                        return false;
                                    }
                                }
                            } else if (!holding.isEmpty()) {
                                return false;
                            }

                            return true;
                        };
                    }

                    return null;
                }
            )
            .registerEvent(PlayerInteractEvent.class, "[on] [step[ping] on] [a] [pressure] plate", matches ->
                event -> {
                    Block clickedBlock = event.getClickedBlock();

                    return event.getAction() == Action.PHYSICAL && clickedBlock != null && EnumSet.of(
                        Material.OAK_PRESSURE_PLATE,
                        Material.SPRUCE_PRESSURE_PLATE,
                        Material.BIRCH_PRESSURE_PLATE,
                        Material.JUNGLE_PRESSURE_PLATE,
                        Material.ACACIA_PRESSURE_PLATE,
                        Material.DARK_OAK_PRESSURE_PLATE,
                        Material.STONE_PRESSURE_PLATE,
                        Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
                        Material.LIGHT_WEIGHTED_PRESSURE_PLATE
                    ).contains(clickedBlock.getType());
                })
            .registerEvent(PlayerInteractEvent.class, "[on] [trip|step[ping] on] [a] tripwire)", matches ->
                event -> {
                    Block clickedBlock = event.getClickedBlock();

                    return event.getAction() == Action.PHYSICAL && clickedBlock != null && EnumSet.of(
                        Material.TRIPWIRE,
                        Material.TRIPWIRE_HOOK
                    ).contains(clickedBlock.getType());
                })
            .registerEvent(PlayerJoinEvent.class, "[on] first (join|login)", matches -> event ->
                !event.getPlayer().hasPlayedBefore())
            .registerEvent(PlayerMoveEvent.class, "[on] (step|walk)[ing] (on|over) %*item types%", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null || elements.length == 0) {
                        continue;
                    }

                    Object itemType = elements[0].execute(null, null);

                    if (!(itemType instanceof ItemType)) {
                        continue;
                    }

                    Collection<BlockData> blockData = new HashSet<>();

                    for (ItemTypeRegistry.Entry entry : ((ItemType) itemType).getItemTypeEntries()) {
                        blockData.add(Bukkit.createBlockData(entry.getFullNamespacedKey()));
                    }

                    return event -> {
                        org.bukkit.Location to = event.getTo();
                        org.bukkit.Location from = event.getFrom();

                        int toBlockX = to.getBlockX();
                        int fromBlockX = from.getBlockX();

                        int toBlockY = to.getBlockY();
                        int fromBlockY = from.getBlockY();

                        int toBlockZ = to.getBlockZ();
                        int fromBlockZ = from.getBlockZ();

                        if (toBlockX == fromBlockX && toBlockY == fromBlockY && toBlockZ == fromBlockZ) {
                            return false;
                        }

                        BlockData eventData = to.getBlock().getRelative(BlockFace.DOWN).getBlockData();

                        for (BlockData data : blockData) {
                            if (eventData.matches(data)) {
                                return true;
                            }
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(PluginDisableEvent.class, "[on] (server|skript) (stop|unload|disable)", matches ->
                event -> event.getPlugin().equals(QuickSkript.getInstance()))
            .registerEvent(ServerTickEvent.class, "[on] every %time span%", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null || elements.length == 0) {
                        continue;
                    }

                    Object timeSpan = elements[0].execute(null, null);

                    if (!(timeSpan instanceof TimeSpan)) {
                        continue;
                    }

                    int ticks = ((TimeSpan) timeSpan).getTicks();
                    int[] totalTicks = {0};

                    return event -> {
                        totalTicks[0]++;

                        if (totalTicks[0] == ticks) {
                            totalTicks[0] = 0;

                            return true;
                        }

                        return false;
                    };
                }

                return null;
            })
            .registerEvent(ServerTickEvent.class, "[on] every %time span% in [world[s]] %worlds%", matches -> {
                for (SkriptMatchResult match : matches) {
                    PsiElement<?>[] elements = tryParseAllTypes(match);

                    if (elements == null || elements.length != 2) {
                        continue;
                    }

                    Object timeSpan = elements[0].execute(null, null);

                    if (!(timeSpan instanceof TimeSpan)) {
                        continue;
                    }

                    int ticks = ((TimeSpan) timeSpan).getTicks();

                    Object world = elements[1].execute(null, null);

                    if (!(world instanceof World)) {
                        continue;
                    }

                    String worldName = ((World) world).getName();

                    return event -> {
                        org.bukkit.World bukkitWorld = Bukkit.getWorld(worldName);

                        return bukkitWorld != null && bukkitWorld.getFullTime() % ticks == 0;
                    };
                }

                return null;
            })
            .registerEvent(ThunderChangeEvent.class, WeatherChangeEvent.class, "[on] weather change [to %weather types%]",
                matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof WeatherType)) {
                            continue;
                        }

                        return event -> {
                            if (event.toThunderState()) {
                                return object == WeatherType.THUNDERSTORM;
                            }

                            if (event.getWorld().hasStorm()) {
                                return object == WeatherType.RAIN;
                            }

                            return object == WeatherType.CLEAR;
                        };
                    }

                    return null;
                }, matches -> {
                    for (SkriptMatchResult match : matches) {
                        PsiElement<?>[] elements = tryParseAllTypes(match);

                        if (elements == null) {
                            continue;
                        }

                        if (elements.length == 0) {
                            return event -> true;
                        }

                        Object object = elements[0].execute(null, null);

                        if (!(object instanceof WeatherType)) {
                            continue;
                        }

                        return event -> {
                            if (event.getWorld().isThundering()) {
                                return object == WeatherType.THUNDERSTORM;
                            }

                            if (event.toWeatherState()) {
                                return object == WeatherType.RAIN;
                            }

                            return object == WeatherType.CLEAR;
                        };
                    }

                    return null;
                }
            )
        );
    }

    @SuppressWarnings("HardcodedFileSeparator")
    @Override
    public void tryRegisterCommand(Skript skript, SkriptFileSection section) {
        String commandName = section.getText().substring("command /".length());

        PluginCommand command = commandMapWrapper.create(commandName);

        List<SkriptFileLine> lines = section.getNodes().stream()
            .filter(node -> node instanceof SkriptFileLine)
            .map(node -> (SkriptFileLine) node)
            .collect(Collectors.toList());

        String description = getFileLineValue(lines, "description:",
            "Command " + commandName + " has multiple valid descriptions");
        if (description != null) {
            command.setDescription(description);
        }

        String aliases = getFileLineValue(lines, "aliases:",
            "Command " + commandName + " has multiple valid aliases");
        if (aliases != null) {
            command.setAliases(Arrays.asList(aliases.replace(" ", "").split(",")));
        }

        String permission = getFileLineValue(lines, "permission:",
            "Command " + commandName + " has multiple valid permissions");
        if (permission != null) {
            command.setPermission(permission);
        }

        String permissionMessage = getFileLineValue(lines, "permission message:",
            "Command " + commandName + " has multiple valid permission messages");
        if (permissionMessage != null) {
            command.setPermissionMessage(permissionMessage);
        }

        String usage = getFileLineValue(lines, "usage:",
            "Command " + commandName + " has multiple valid usages");
        if (usage != null) {
            command.setUsage(usage);
        }

        String rawTarget = getFileLineValue(lines, "usage:",
            "Command " + commandName + " has multiple valid execution targets");
        ExecutionTarget target = rawTarget == null ? null : ExecutionTarget.parse(rawTarget);

        SkriptFileSection trigger = null;

        for (SkriptFileNode node : section.getNodes()) {
            if (node instanceof SkriptFileSection && node.getText().equalsIgnoreCase("trigger")) {

                if (trigger != null) {
                    QuickSkript.getInstance().getLogger().warning("Command " + commandName +
                        " has multiple valid triggers");
                    break;
                }

                trigger = (SkriptFileSection) node;
            }
        }

        if (trigger == null) {
            QuickSkript.getInstance().getLogger().severe("Command " + commandName +
                " failed to load, because no trigger is set");
            return;
        }

        command.setExecutor(new SkriptCommandExecutor(this, environment, skript, trigger, target));

        commandMapWrapper.register(command);
    }

    @Override
    public void tryRegisterEvent(Skript skript, SkriptFileSection section) {
        String input = section.getText();

        input = input.trim();

        for (EventProxyFactory factory : events) {
            if (factory.tryRegister(input, () -> new SkriptEventExecutor(this, environment, skript, section))) {
                return;
            }
        }
    }

    /**
     * Gets a standard comparator for regions and a region event. This takes an iterator of skript match results, checks
     * all matches for a valid match and returns a predicate that returns true if the region event has the same region
     * as the one parsed from the skript match result. If no valid skript match result is found, this returns null.
     *
     * @return a region comparator
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private <T extends RegionEvent> Function<Iterable<SkriptMatchResult>, Predicate<T>> defaultRegionComparison() {
        return matches -> {
            for (SkriptMatchResult match : matches) {
                PsiElement<?>[] elements = tryParseAllTypes(match);

                if (elements == null) {
                    continue;
                }

                if (elements.length != 1) {
                    throw new IllegalStateException("Got zero elements, but expected one");
                }

                Object object = elements[0].execute(null, null);

                if (!(object instanceof Region)) {
                    continue;
                }

                Region region = (Region) object;

                return event -> event.getRegion().equals(region);
            }

            return null;
        };
    }

    /**
     * Creates a default predicate which takes in a block event, comparing the block data to the provided item type,
     * returning true if any of the block data matches and false otherwise.
     *
     * @param itemType the item type to compare against
     * @return a predicate comparing the block event's block data to the item type
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private <T extends BlockEvent> Predicate<T> defaultItemTypeComparison(@NotNull ItemType itemType) {
        Collection<BlockData> blockData = new HashSet<>();

        for (ItemTypeRegistry.Entry entry : itemType.getItemTypeEntries()) {
            blockData.add(Bukkit.createBlockData(entry.getFullNamespacedKey()));
        }

        return event -> {
            BlockData eventData = event.getBlock().getBlockData();

            for (BlockData data : blockData) {
                if (eventData.matches(data)) {
                    return true;
                }
            }

            return false;
        };
    }

    /**
     * Returns a bi function taking in a skript match result and an array of psi element and returns an event predicate
     * returning whether the fired event should be executed or not. This does a standard comparison, always returning
     * true if there are no elements to compare and otherwise comparing the entity type from the first psi element in
     * the array with the entity type involved in the event, returning true if the entity type involved in the event is
     * the same as the entity type specified.
     *
     * @param <T> the type of entity event to compare for
     * @return a bi function of a skript match result and a psi element array, returning a predicate for the event
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private <T extends EntityEvent> Function<Iterable<SkriptMatchResult>, Predicate<T>> defaultEntityComparison() {
        return matches -> {
            for (SkriptMatchResult match : matches) {
                PsiElement<?>[] elements = tryParseAllTypes(match);

                if (elements.length == 0) {
                    return event -> true;
                }

                PsiElement<?> element = elements[0];
                Object object = element.execute(null, null);

                if (!(object instanceof EntityTypeRegistry.Entry skriptEntityType)) {
                    continue;
                }

                return event -> equals(skriptEntityType, event.getEntityType());
            }

            return null;
        };
    }

    /**
     * Tries to parse all the types in the given skript match result. This returns an array of all elements that were
     * parsed corresponding to the given match. The order of the elements is the same as the order in which the
     * corresponding types are specified. If a type in the match failed to be parsed, the result will be null, even if
     * some types could be parsed - the returned array is guaranteed to have all elements if it's not null. The type in
     * the provided match result is respected: if the type is a number, then the element will be one that can return a
     * number (the element may also specify to return object(s) - these are valid for every type).
     *
     * @param match the match to parse all types of
     * @return an array of parsed elements
     * @since 0.1.0
     */
    @Nullable
    private PsiElement<?>[] tryParseAllTypes(@NotNull SkriptMatchResult match) {
        List<PsiElement<?>> elements = new ArrayList<>();

        for (Pair<SkriptPatternGroup, String> pair : match.getMatchedGroups()) {
            if (!(pair.getX() instanceof TypeGroup)) {
                continue;
            }

            Type[] types = ((TypeGroup) pair.getX()).getTypes();
            PsiElement<?> psiElement = null;

            for (Type type : types) {
                psiElement = tryParseElement(pair.getY(), type, -1);

                if (psiElement != null) {
                    break;
                }
            }

            if (psiElement == null) {
                return null;
            }

            elements.add(psiElement);
        }

        return elements.toArray(PsiElement<?>[]::new);
    }

    /**
     * Finds a line which starts with the specified key.
     *
     * @param lines the lines in which to search
     * @param key the key of the value
     * @param multipleMatchWarning the warning to log in case of multiple matches
     * @return the found value or null in case none were found
     */
    @Nullable
    @Contract(pure = true)
    private static String getFileLineValue(@NotNull List<SkriptFileLine> lines, @NotNull String key,
        @NotNull String multipleMatchWarning) {
        String value = null;

        for (SkriptFileLine line : lines) {
            if (line.getText().startsWith(key)) {
                if (value != null) {
                    QuickSkript.getInstance().getLogger().warning(multipleMatchWarning);
                }

                value = line.getText().substring(key.length()).trim();
            }
        }

        return value;
    }

    /**
     * Registers this factory if the specified platform is available
     *
     * @param factory the factory to register
     * @param minimumPlatform the minimum platform necessary
     * @since 0.1.0
     */
    private void registerElement(@NotNull PsiElementFactory factory, @NotNull Platform minimumPlatform) {
        if (Platform.getPlatform().isAvailable(minimumPlatform)) {
            registerElement(factory);
        }
    }

    /**
     * Registers the specified {@link EventProxyFactory}
     *
     * @param eventProxyFactory the event proxy factory
     * @since 0.1.0
     */
    private void registerEvent(@NotNull EventProxyFactory eventProxyFactory) {
        events.add(eventProxyFactory);
    }

    /**
     * Checks if the two entity types represent the same entity type.
     *
     * @param skriptEntityType a skript entity type
     * @param bukkitEntityType a Bukkit entity type
     * @return true if both arguments represent the same entity type, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    private static boolean equals(@NotNull EntityTypeRegistry.Entry skriptEntityType,
        @NotNull EntityType bukkitEntityType) {
        boolean isUnknown = bukkitEntityType == EntityType.UNKNOWN;
        String skriptEntityTypeKey = skriptEntityType.getKey();

        /* xor check: only return false if entity type is unknown and a key exists or entity type is not unknown, but a
           key doesn't exist */
        if (isUnknown != (skriptEntityTypeKey == null)) {
            return false;
        }

        if (!isUnknown) {
            NamespacedKey key = bukkitEntityType.getKey();

            return skriptEntityTypeKey.equals(key.getNamespace() + ':' + key.getKey());
        }

        return true;
    }
}