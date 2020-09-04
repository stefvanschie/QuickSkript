package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.context.CommandContext;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.condition.*;
import com.github.stefvanschie.quickskript.core.psi.effect.*;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.expression.*;
import com.github.stefvanschie.quickskript.core.psi.function.*;
import com.github.stefvanschie.quickskript.core.psi.literal.*;
import com.github.stefvanschie.quickskript.core.psi.section.PsiBaseSection;
import com.github.stefvanschie.quickskript.core.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.core.psi.section.PsiWhile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * An impl. of {@link SkriptLoader} for applications that are not tied to a specific Minecraft server platform impl. If
 * you're working with a Minecraft server platform please use the {@link SkriptLoader} for that platform. If this
 * platform impl. does not exist, you should create one yourself by extending the {@link SkriptLoader} class and
 * providing impl. for all unimplemented methods.
 *
 * @since 0.1.0
 */
public class StandaloneSkriptLoader extends SkriptLoader {

    /**
     * A map to hold all commands
     */
    private final Map<String, PsiBaseSection> commands = new HashMap<>();

    /**
     * A map to hold all events
     */
    private final Map<String, PsiBaseSection> events = new HashMap<>();

    private final Set<Pattern> registeredEvents = Set.of(
        //TODO: Add all events
    );

    @Override
    public void registerDefaultElements() {
        //effects
        //these are at the top, cause they are always the outermost element
        registerElement(new PsiActionBarEffect.Factory());
        registerElement(new PsiBanEffect.Factory());
        registerElement(new PsiCancelEventEffect.Factory());
        registerElement(new PsiChangeEffect.Factory());
        registerElement(new PsiCloseInventoryEffect.Factory());
        registerElement(new PsiCommandEffect.Factory());
        registerElement(new PsiContinueEffect.Factory());
        registerElement(new PsiDoIfEffect.Factory());
        registerElement(new PsiExitEffect.Factory());
        registerElement(new PsiExplosionEffect.Factory());
        registerElement(new PsiFeedEffect.Factory());
        registerElement(new PsiFlyEffect.Factory());
        registerElement(new PsiHidePlayerFromServerListEffect.Factory());
        registerElement(new PsiKickEffect.Factory());
        registerElement(new PsiKillEffect.Factory());
        registerElement(new PsiLoadServerIconEffect.Factory());
        registerElement(new PsiLogEffect.Factory());
        registerElement(new PsiMessageEffect.Factory());
        registerElement(new PsiOpEffect.Factory());
        registerElement(new PsiPlayerInfoEffect.Factory());
        registerElement(new PsiPlayerVisibilityEffect.Factory());
        registerElement(new PsiResetTitleEffect.Factory());
        registerElement(new PsiSayEffect.Factory());
        registerElement(new PsiShearEffect.Factory());
        registerElement(new PsiToggleFlightEffect.Factory());
        registerElement(new PsiUnbanEffect.Factory());

        registerElement(new PsiParseExpression.Factory());

        //conditions
        registerElement(new PsiCanFlyCondition.Factory());
        registerElement(new PsiCanSeeCondition.Factory());
        registerElement(new PsiChanceCondition.Factory());
        registerElement(new PsiEndsWithCondition.Factory());
        registerElement(new PsiEventCancelledCondition.Factory());
        registerElement(new PsiExistsCondition.Factory());
        registerElement(new PsiHasClientWeatherCondition.Factory());
        registerElement(new PsiHasPermissionCondition.Factory());
        registerElement(new PsiHasPlayedBeforeCondition.Factory());
        registerElement(new PsiHasScoreboardTagCondition.Factory());
        registerElement(new PsiIsAliveCondition.Factory());
        registerElement(new PsiIsBannedCondition.Factory());
        registerElement(new PsiIsBlockingCondition.Factory());
        registerElement(new PsiIsBurningCondition.Factory());
        registerElement(new PsiIsFlyingCondition.Factory());
        registerElement(new PsiIsOnGroundCondition.Factory());
        registerElement(new PsiIsOnlineCondition.Factory());
        registerElement(new PsiIsPoisonedCondition.Factory());
        registerElement(new PsiIsSleepingCondition.Factory());
        registerElement(new PsiIsSneakingCondition.Factory());
        registerElement(new PsiIsSprintingCondition.Factory());
        registerElement(new PsiIsSwimmingCondition.Factory());
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
        registerElement(new PsiPlayerLiteral.Factory());
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
        registerElement(new PsiAttackerExpression.Factory());
        registerElement(new PsiBukkitVersionExpression.Factory());
        registerElement(new PsiCapitalizationTextExpression.Factory());
        registerElement(new PsiChatMessageExpression.Factory());
        registerElement(new PsiCommandExpression.Factory());
        registerElement(new PsiCommandSenderExpression.Factory());
        registerElement(new PsiConsoleSenderExpression.Factory());
        registerElement(new PsiDamageExpression.Factory());
        registerElement(new PsiDeathMessageExpression.Factory());
        registerElement(new PsiDefaultMOTDExpression.Factory());
        registerElement(new PsiDefaultServerIconExpression.Factory());
        registerElement(new PsiDefaultValueExpression.Factory());
        registerElement(new PsiDisplayedMOTDExpression.Factory());
        registerElement(new PsiElementOfExpression.Factory());
        registerElement(new PsiExhaustionExpression.Factory());
        registerElement(new PsiExperienceExpression.Factory());
        registerElement(new PsiFakeMaxPlayersExpression.Factory());
        registerElement(new PsiFakeOnlinePlayerCountExpression.Factory());
        registerElement(new PsiFilterExpression.Factory());
        registerElement(new PsiFilterInputExpression.Factory());
        registerElement(new PsiFinalDamageExpression.Factory());
        registerElement(new PsiFlyModeExpression.Factory());
        registerElement(new PsiFoodLevelExpression.Factory());
        registerElement(new PsiFormatDateTimeExpression.Factory());
        registerElement(new PsiGlidingStateExpression.Factory());
        registerElement(new PsiGlowingExpression.Factory());
        registerElement(new PsiGravityExpression.Factory());
        registerElement(new PsiHashExpression.Factory());
        registerElement(new PsiHealthExpression.Factory());
        registerElement(new PsiHiddenPlayersExpression.Factory());
        registerElement(new PsiHoverListExpression.Factory());
        registerElement(new PsiIndexOfExpression.Factory());
        registerElement(new PsiIndicesExpression.Factory());
        registerElement(new PsiInfinityExpression.Factory());
        registerElement(new PsiIPExpression.Factory());
        registerElement(new PsiJoinExpression.Factory());
        registerElement(new PsiJoinMessageExpression.Factory());
        registerElement(new PsiLanguageExpression.Factory());
        registerElement(new PsiLeashHolderExpression.Factory());
        registerElement(new PsiLeaveMessageExpression.Factory());
        registerElement(new PsiLengthExpression.Factory());
        registerElement(new PsiLevelExpression.Factory());
        registerElement(new PsiLevelProgressExpression.Factory());
        registerElement(new PsiLoadedServerIconExpression.Factory());
        registerElement(new PsiMaxHealthExpression.Factory());
        registerElement(new PsiMeExpression.Factory());
        registerElement(new PsiMinecraftVersionExpression.Factory());
        registerElement(new PsiNaNExpression.Factory());
        registerElement(new PsiNowExpression.Factory());
        registerElement(new PsiNumbersExpression.Factory());
        registerElement(new PsiOfflinePlayersExpression.Factory());
        registerElement(new PsiPassengersExpression.Factory());
        registerElement(new PsiPermissionsExpression.Factory());
        registerElement(new PsiPingExpression.Factory());
        registerElement(new PsiPlayerListFooterExpression.Factory());
        registerElement(new PsiPlayerListHeaderExpression.Factory());
        registerElement(new PsiProtocolVersionExpression.Factory());
        registerElement(new PsiRandomExpression.Factory());
        registerElement(new PsiRandomNumberExpression.Factory());
        registerElement(new PsiRealMaxPlayersExpression.Factory());
        registerElement(new PsiRealOnlinePlayerCountExpression.Factory());
        registerElement(new PsiRoundExpression.Factory());
        registerElement(new PsiSaturationExpression.Factory());
        registerElement(new PsiScoreboardTagsExpression.Factory());
        registerElement(new PsiScriptNameExpression.Factory());
        registerElement(new PsiShownServerIconExpression.Factory());
        registerElement(new PsiShuffleExpression.Factory());
        registerElement(new PsiSkriptVersionExpression.Factory());
        registerElement(new PsiSortExpression.Factory());
        registerElement(new PsiSpeedExpression.Factory());
        registerElement(new PsiSplitExpression.Factory());
        registerElement(new PsiSubstringExpression.Factory());
        registerElement(new PsiTamerExpression.Factory());
        registerElement(new PsiTernaryExpression.Factory());
        registerElement(new PsiTPSExpression.Factory());
        registerElement(new PsiUnixTimestampExpression.Factory());
        registerElement(new PsiVehicleExpression.Factory());
        registerElement(new PsiVersionStringExpression.Factory());

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
        registerElement(new PsiLocationFunction.Factory());
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
        registerElement(new PsiVectorFunction.Factory());
        registerElement(new PsiWorldFunction.Factory());

        //this one is here, because it has special identifiers around it
        registerElement(new PsiStringLiteral.Factory());

        //these are slow and match a lot, therefore at the bottom
        registerElement(new PsiItemCategoryLiteral.Factory());
        registerElement(new PsiItemLiteral.Factory());
        registerElement(new PsiMoneyLiteral.Factory());
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
    public void registerDefaultEvents() {
        //TODO: find a way to register events without relying on impl.
    }

    @SuppressWarnings("HardcodedFileSeparator")
    @Override
    public void tryRegisterCommand(Skript skript, SkriptFileSection section) {
        if (!section.getText().startsWith("command /")) {
            return;
        }

        String command = section.getText().substring("command /".length());

        SkriptFileSection trigger = (SkriptFileSection) section.getNodes()
            .stream()
            .filter(node -> node instanceof SkriptFileSection && node.getText().equalsIgnoreCase("trigger"))
            .findAny()
            .orElse(null);

        if (trigger == null) {
            throw new ParseException("Unable to find a trigger for the command", section.getLineNumber());
        }

        PsiBaseSection baseSection = new PsiBaseSection(this, skript, trigger, CommandContext.class);

        commands.put(command, baseSection);
    }

    @Override
    public void tryRegisterEvent(Skript skript, SkriptFileSection section) {
        String event = section.getText();

        if (registeredEvents.stream().anyMatch(pattern -> pattern.matcher(event).matches())) {
            events.put(event, new PsiBaseSection(this, skript, section, EventContext.class));
        }
    }
}
