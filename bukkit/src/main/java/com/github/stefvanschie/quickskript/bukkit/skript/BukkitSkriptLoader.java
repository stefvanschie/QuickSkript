package com.github.stefvanschie.quickskript.bukkit.skript;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.integration.region.RegionIntegration;
import com.github.stefvanschie.quickskript.bukkit.integration.region.WorldGuardIntegration;
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
import com.github.stefvanschie.quickskript.bukkit.util.CommandMapWrapper;
import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import com.github.stefvanschie.quickskript.bukkit.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.QuickSkriptPostEnableEvent;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileLine;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
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
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
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
     * @since 0.1.0
     */
    public BukkitSkriptLoader() {
        RegionIntegration regionIntegration = QuickSkript.getInstance().getRegionIntegration();

        if (regionIntegration != null) {
            getRegionRegistry().addRegions(regionIntegration.getRegions());
        }
    }

    @Override
    public void registerDefaultElements() {
        //effects
        //these are at the top, cause they are always the outermost element
        registerElement(new PsiActionBarEffectImpl.Factory(), Platform.SPIGOT);
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
        registerElement(new PsiCanFlyConditionImpl.Factory());
        registerElement(new PsiCanSeeConditionImpl.Factory());
        registerElement(new PsiChanceCondition.Factory());
        registerElement(new PsiEndsWithCondition.Factory());
        registerElement(new PsiEventCancelledConditionImpl.Factory());
        registerElement(new PsiExistsCondition.Factory());
        registerElement(new PsiHasClientWeatherConditionImpl.Factory());
        registerElement(new PsiHasPermissionConditionImpl.Factory());
        registerElement(new PsiHasPlayedBeforeConditionImpl.Factory());
        registerElement(new PsiHasScoreboardTagConditionImpl.Factory());
        registerElement(new PsiIsAliveConditionImpl.Factory());
        registerElement(new PsiIsBannedConditionImpl.Factory());
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
        registerElement(new PsiTeleportCauseLiteral.Factory());
        registerElement(new PsiTimeLiteral.Factory());
        registerElement(new PsiTimePeriodLiteral.Factory());
        registerElement(new PsiTimeSpanLiteral.Factory());

        //expressions
        registerElement(new PsiAlphabeticalSortExpression.Factory());
        registerElement(new PsiAmountExpression.Factory());
        registerElement(new PsiArithmeticExpression.Factory());
        registerElement(new PsiAttackerExpressionImpl.Factory());
        registerElement(new PsiBukkitVersionExpressionImpl.Factory());
        registerElement(new PsiCapitalizationTextExpression.Factory());
        registerElement(new PsiChatMessageExpressionImpl.Factory());
        registerElement(new PsiCommandExpressionImpl.Factory());
        registerElement(new PsiCommandSenderExpressionImpl.Factory());
        registerElement(new PsiConsoleSenderExpressionImpl.Factory());
        registerElement(new PsiDamageExpressionImpl.Factory());
        registerElement(new PsiDeathMessageExpressionImpl.Factory());
        registerElement(new PsiDefaultMOTDExpressionImpl.Factory());
        registerElement(new PsiDefaultServerIconExpressionImpl.Factory());
        registerElement(new PsiDisplayedMOTDExpressionImpl.Factory());
        registerElement(new PsiDefaultValueExpression.Factory());
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
        registerElement(new PsiHoverListExpressionImpl.Factory(), Platform.PAPER);
        registerElement(new PsiIndexOfExpression.Factory());
        registerElement(new PsiIndicesExpression.Factory());
        registerElement(new PsiInfinityExpression.Factory());
        registerElement(new PsiIPExpressionImpl.Factory());
        registerElement(new PsiJoinExpression.Factory());
        registerElement(new PsiJoinMessageExpressionImpl.Factory());
        registerElement(new PsiLanguageExpressionImpl.Factory());
        registerElement(new PsiLeashHolderExpressionImpl.Factory());
        registerElement(new PsiLeaveMessageExpressionImpl.Factory());
        registerElement(new PsiLengthExpression.Factory());
        registerElement(new PsiLevelExpressionImpl.Factory());
        registerElement(new PsiLevelProgressExpressionImpl.Factory());
        registerElement(new PsiLoadedServerIconExpressionImpl.Factory());
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

        //this one is here, because it has special identifiers around it
        registerElement(new PsiStringLiteral.Factory());

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
                .registerEvent(AreaEffectCloudApplyEvent.class, "(?:on )?(?:(?:area)|(?:AoE)) (?:cloud )?effect")
                .registerEvent(BlockCanBuildEvent.class, "(?:on )?(?:block )?can build check")
                .registerEvent(BlockDamageEvent.class, "(?:on )?block damag(?:ing|e)")
                .registerEvent(BlockFromToEvent.class, "(?:on )?(?:block )?(?:flow(?:ing)?|mov(?:e|ing))")
                .registerEvent(BlockIgniteEvent.class, "(?:on )?(?:block )?ignit(?:e|ion)?")
                .registerEvent(BlockPhysicsEvent.class, "(?:on )?(?:block )?physics")
                .registerEvent(BlockPistonExtendEvent.class, "(?:on )?piston extend(?:ing)?")
                .registerEvent(BlockPistonRetractEvent.class, "(?:on )?piston retract(?:ing)?")
                .registerEvent(BlockRedstoneEvent.class, "(?:on )?redstone(?: current)?(?: chang(e|ing))?")
                .registerEvent(BlockSpreadEvent.class, "(?:on )?spread(ing)?")
                .registerEvent(ChunkLoadEvent.class, "(?:on )?chunk load(?:ing)?")
                .registerEvent(ChunkPopulateEvent.class, "(?:on )?chunk (?:generat|populat)(?:e|ing)")
                .registerEvent(ChunkUnloadEvent.class, "(?:on )?chunk unload(?:ing)?")
                .registerEvent(CreeperPowerEvent.class, "(?:on )?creeper power")
                .registerEvent(EntityBreakDoorEvent.class, "(?:on )?zombie break(?:ing)?(?: a)?( wood(?:en)?)? door")
                .registerEvent(EntityCombustEvent.class, "(?:on )?combust(?:ing)?")
                .registerEvent("org.spigotmc.event.entity.EntityDismountEvent", "(?:on )?dismount(?:ing)?", Platform.SPIGOT)
                .registerEvent(EntityExplodeEvent.class, "(?:on )?explo(?:(?:d(?:e|ing))|(?:sion))")
                .registerEvent("org.spigotmc.event.entity.EntityMountEvent", "(?:on )?mount(?:ing)?", Platform.SPIGOT)
                .registerEvent(EntityPortalEnterEvent.class, "(?:on )?(?:portal enter(?:ing)?|entering(?: a)? portal)")
                .registerEvent(EntityRegainHealthEvent.class, "(?:on )?heal(?:ing)?")
                .registerEvent(EntityResurrectEvent.class, "(?:on )?(?:entity )?resurrect(?:ion)?(?: attempt)?")
                .registerEvent(EntityTameEvent.class, "(?:on )?(?:entity )?(?:tam(?:e|ing))")
                .registerEvent(EntityTargetEvent.class, "(?:on )?(?:entity )?(?:un-?)?target")
                .registerEvent(EntityToggleGlideEvent.class, "(?:on )?(?:toggle )?glid(?:e|ing)(?: state change)?")
                .registerEvent(EntityToggleSwimEvent.class, "(?:on )?(?:entity )?(?:toggl(?:e|ing) swim|swim toggl(?:e|ing))")
                .registerEvent(ExperienceOrbSpawnEvent.class, "(?:on )?(?:e?xp(?:erience)?(?: orb)? spawn|spawn of(?: a(?:n)?)? e?xp(?:erience)?(?: orb)?)")
                .registerEvent(ExplosionPrimeEvent.class, "(?:on )?explosion prime")
                .registerEvent(FoodLevelChangeEvent.class, "(?:on )?(?:food|hunger) (?:level|met(?:er|re)|bar) chang(?:e|ing)")
                .registerEvent(FurnaceBurnEvent.class, "(?:on )?fuel burn(?:ing)?")
                .registerEvent(FurnaceSmeltEvent.class, "(?:on )?(?:(?:ore )?smelt(?:ing)?(?: of ore)?)")
                .registerEvent(InventoryCloseEvent.class, "(?:on )?inventory clos(?:ed?|ing)")
                .registerEvent(InventoryOpenEvent.class, "(?:on )?inventory open(?:ed)?")
                .registerEvent(LeavesDecayEvent.class, "(?:on )?leaves decay(?:ing)?")
                .registerEvent(LightningStrikeEvent.class, "(?:on )?lightning(?: strike)?")
                .registerEvent(PigZapEvent.class, "(?:on )?pig ?zap")
                .registerEvent(PlayerBedEnterEvent.class, "(?:on )?(?:(?:bed enter(?:ing)?)|(?:(?:player )?enter(?:ing)? (?:a )?bed))")
                .registerEvent(PlayerBedLeaveEvent.class, "(?:on )?(?:(?:bed leav(?:e|ing))|(?:(player )?leav(?:e|ing) (a )?bed))")
                .registerEvent(PlayerBucketEmptyEvent.class, "(?:on )?(?:player )?(?:empty(?:ing)? )?(?:a )?bucket(?: empty(?:ing)?)?")
                .registerEvent(PlayerBucketFillEvent.class, "(?:on )?(?:player )?(?:fill(?:ing)? )?(?:a )?bucket(?: fill(?:ing)?)?")
                .registerEvent(PlayerChangedWorldEvent.class, "(?:on )?(?:player )?world chang(?:ing|ed?)")
                .registerEvent(PlayerCommandPreprocessEvent.class, "(?:on )?command")
                .registerEvent(PlayerEggThrowEvent.class, "(?:on )?(?:throw(?:ing)?(?: of)?(?: an)? egg|(?:player )?egg throw)")
                .registerEvent(PlayerFishEvent.class, "(?:on )?(?:player )?fish(?:ing)?")
                .registerEvent(PlayerItemBreakEvent.class, "(?:on )?(?:player )?(?:tool break(?:ing)?|break(?:ing)?(?: (?:a|the))? tool)")
                .registerEvent(PlayerItemHeldEvent.class, "(?:on )?(?:player(?:'s)? )?(?:tool|item held|held item) chang(?:e|ing)")
                .registerEvent(PlayerJoinEvent.class, "(?:on )?(?:player )?(?:log(?:ging )?in|join(?:ing)?)")
                .registerEvent("com.destroystokyo.paper.event.player.PlayerJumpEvent", "(?:on )?(?:player )?jump(?:ing)?", Platform.PAPER)
                .registerEvent(PlayerKickEvent.class, "(?:on )?(?:player )?(?:being )?kick(?:ed)?")
                .registerEvent(PlayerLevelChangeEvent.class, "(?:on )?(?:player )?level(?: change)?")
                .registerEvent(PlayerLocaleChangeEvent.class, "(?:on )?(?:player )?(?:(?:language|locale) chang(?:e|ing)|chang(?:e|ing) (?:language|locale))")
                .registerEvent(PlayerLoginEvent.class, "(?:on )?(?:player )?connect(?:ing)?")
                .registerEvent(PlayerPortalEvent.class, "(?:on )?(?:player )?portal")
                .registerEvent(PlayerQuitEvent.class, "(?:on )?(?:quit(?:ting)?|disconnect(?:ing)?|log(?:ging | )?out)")
                .registerEvent(PlayerRespawnEvent.class, "(?:on )?(?:player )?respawn(?:ing)?")
                .registerEvent(PlayerSwapHandItemsEvent.class, "(?:on )?swap(ping of)?(?: (?:hand|held))? items?")
                .registerEvent(PlayerTeleportEvent.class, "(?:on )?(?:player )?teleport(?:ing)?")
                .registerEvent(PlayerToggleFlightEvent.class, "(?:on )?(?:player )?(?:flight toggl(?:e|ing)|toggl(?:e|ing) flight)")
                .registerEvent(PlayerToggleSneakEvent.class, "(?:on )?(?:player )?(?:toggl(?:e|ing) sneak|sneak toggl(?:e|ing))")
                .registerEvent(PlayerToggleSprintEvent.class, "(?:on )?(?:player )?(?:toggl(?:e|ing) sprint|sprint toggl(?:e|ing))")
                .registerEvent(PortalCreateEvent.class, "(?:on )?portal creat(?:e|ion)")
                .registerEvent(ProjectileHitEvent.class, "(?:on )?projectile hit")
                .registerEvent(ProjectileLaunchEvent.class, "(?:on )?(projectile )?shoot")
                .registerEvent(
                    Platform.getPlatform() == Platform.PAPER ? PaperServerListPingEvent.class : ServerListPingEvent.class,
                    "(?:on )?server(?: list)? ping"
                )
                .registerEvent(QuickSkriptPostEnableEvent.class, "(?:on )?(server|skript) (start|load|enable)")
                .registerEvent(SheepRegrowWoolEvent.class, "(?:on )?sheep (?:re)?grow(?:ing)? wool")
                .registerEvent(SignChangeEvent.class, "(?:on )?(?:sign (?:change?|edit)(?:ing)?|(?:player )?(?:change?|edit)(?:ing)?(?: a)? sign)")
                .registerEvent(SlimeSplitEvent.class, "(?:on )?slime split(?:ting)?")
                .registerEvent(SpawnChangeEvent.class, "(?:on )?(?:world )?spawn change")
                .registerEvent(VehicleCreateEvent.class, "(?:on )?(?:vehicle create|creat(?:e|ing|ion of)(?: a)? vehicle)")
                .registerEvent(VehicleDamageEvent.class, "(?:on )?(?:vehicle damage|damag(?:e|ing)(?: a)? vehicle)")
                .registerEvent(VehicleDestroyEvent.class, "(?:on )?(?:vehicle destroy|destr(?:oy(?:ing)?|uction of)(?: a)? vehicle)")
                .registerEvent(VehicleEnterEvent.class, "(?:on )?(?:vehicle enter|enter(?:ing)?(?: a)? vehicle)")
                .registerEvent(VehicleExitEvent.class, "(?:on )?(?:vehicle exit|exit(?:ing)?(?: a)? vehicle)")
                .registerEvent(WorldInitEvent.class, "(?:on )?world init(?:ialization)?")
                .registerEvent(WorldLoadEvent.class, "(?:on )?world load(?:ing)?")
                .registerEvent(WorldSaveEvent.class, "(?:on )?world sav(?:e|ing)")
                .registerEvent(WorldUnloadEvent.class, "(?:on )?world unload(?:ing)?")
        );

        registerEvent(new ComplexEventProxyFactory()
                .registerEvent(EntityChangeBlockEvent.class, "(?:on )?enderman place", matcher -> event ->
                        event.getEntity() instanceof Enderman && event.getTo() != Material.AIR)
                .registerEvent(EntityChangeBlockEvent.class, "(?:on )?enderman pickup", matcher -> event ->
                        event.getEntity() instanceof Enderman && event.getTo() == Material.AIR)
                .registerEvent(EntityChangeBlockEvent.class, "(?:on )?sheep eat", matcher -> event ->
                        event.getEntity() instanceof Sheep)
                .registerEvent(EntityChangeBlockEvent.class, "(?:on )?silverfish enter", matcher -> event ->
                        event.getEntity() instanceof Silverfish && EnumSet.of(
                                Material.INFESTED_COBBLESTONE,
                                Material.INFESTED_STONE,
                                Material.INFESTED_CHISELED_STONE_BRICKS,
                                Material.INFESTED_CRACKED_STONE_BRICKS,
                                Material.INFESTED_MOSSY_STONE_BRICKS,
                                Material.INFESTED_STONE_BRICKS
                        ).contains(event.getTo()))
                .registerEvent(EntityChangeBlockEvent.class, "(?:on )?silverfish exit", matcher -> event ->
                        event.getEntity() instanceof Silverfish && event.getTo() == Material.AIR)
                .registerEvent(PlayerCommandPreprocessEvent.class, "(?:on )?command \"([\\s\\S]+)\"", matcher -> {
                    String command = matcher.group(1); //TODO the regex of this group is probably incorrect
                    String finalCommand = command.startsWith("/") ? command.substring(1) : command;

                    return event -> {
                        String message = event.getMessage();
                        return message.startsWith(finalCommand, message.startsWith("/") ? 1 : 0);
                    };
                })
                .registerEvent(PlayerEditBookEvent.class, "(?:on )?book (edit|change|write|sign|signing)", matcher -> {
                    String group = matcher.group(1).toLowerCase();
                    return group.equals("sign") || group.equals("signing")
                            ? PlayerEditBookEvent::isSigning : event -> !event.isSigning();
                })
                .registerEvent(PlayerInteractEvent.class, "(?:on )?(?:(right|left)(?: |-)?)?(?:mouse(?: |-)?)?click(?:ing)?", matcher -> {
                    //TODO: This expression needs to be completed in the future, since it's missing optional additional parts
                    String clickType = matcher.group(1);

                    if (clickType == null) {
                        return event -> true;
                    }

                    if (clickType.equals("left")) {
                        return event -> {
                            Action action = event.getAction();
                            return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
                        };
                    }

                    if (clickType.equals("right")) {
                        return event -> {
                            Action action = event.getAction();
                            return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
                        };
                    }

                    throw new AssertionError("Unknown click type detected for event registration");
                })
                .registerEvent(PlayerInteractEvent.class, "(?:on )?(?:step(?:ping)? on )?(?:a )?(?:pressure )?plate",
                    matcher -> event -> {
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
                .registerEvent(PlayerInteractEvent.class, "(?:on )?(?:trip |(?:step(?:ping)? on )?(?:a )?tripwire)",
                    matcher -> event -> {
                        Block clickedBlock = event.getClickedBlock();

                        return event.getAction() == Action.PHYSICAL && clickedBlock != null && EnumSet.of(
                            Material.TRIPWIRE,
                            Material.TRIPWIRE_HOOK
                        ).contains(clickedBlock.getType());
                    })
                .registerEvent(PlayerJoinEvent.class, "(?:on )?first (?:join|login)", matcher -> event ->
                        !event.getPlayer().hasPlayedBefore())
                .registerEvent(PluginDisableEvent.class, "(?:on )?(server|skript) (stop|unload|disable)",
                    matcher -> event -> event.getPlugin().equals(QuickSkript.getInstance()))
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
            command.setDescription(Text.parse(description).toString());
        }

        String aliases = getFileLineValue(lines, "aliases:",
            "Command " + commandName + " has multiple valid aliases");
        if (aliases != null) {
            command.setAliases(Arrays.asList(StringUtils.replace(aliases, " ", "").split(",")));
        }

        String permission = getFileLineValue(lines, "permission:",
            "Command " + commandName + " has multiple valid permissions");
        if (permission != null) {
            command.setPermission(Text.parse(permission).toString());
        }

        String permissionMessage = getFileLineValue(lines, "permission message:",
            "Command " + commandName + " has multiple valid permission messages");
        if (permissionMessage != null) {
            command.setPermissionMessage(Text.parse(permissionMessage).toString());
        }

        String usage = getFileLineValue(lines, "usage:",
            "Command " + commandName + " has multiple valid usages");
        if (usage != null) {
            command.setUsage(Text.parse(usage).toString());
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

        command.setExecutor(new SkriptCommandExecutor(this, skript, trigger, target));

        commandMapWrapper.register(command);
    }

    @Override
    public void tryRegisterEvent(Skript skript, SkriptFileSection section) {
        String input = section.getText();

        input = input.trim();

        for (EventProxyFactory factory : events) {
            if (factory.tryRegister(input, () -> new SkriptEventExecutor(this, skript, section))) {
                return;
            }
        }
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
}
