package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.context.CommandContext;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.condition.*;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiCancelEventEffect;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiExplosionEffect;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiConsoleSenderExpression;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiParseExpression;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiRandomNumberExpression;
import com.github.stefvanschie.quickskript.core.psi.function.*;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiBooleanLiteral;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiPlayerLiteral;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiStringLiteral;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDefaultElements() {
        //effects
        //these are at the top, cause they are always the outermost element
        registerElement(new PsiCancelEventEffect.Factory());
        registerElement(new PsiExplosionEffect.Factory());
        registerElement(new PsiMessageEffect.Factory());

        //this one is here, because it has special identifiers around it
        registerElement(new PsiStringLiteral.Factory());

        registerElement(new PsiParseExpression.Factory());

        //conditions
        registerElement(new PsiCanFlyCondition.Factory());
        registerElement(new PsiCanSeeCondition.Factory());
        registerElement(new PsiChanceCondition.Factory());
        registerElement(new PsiEventCancelledCondition.Factory());
        registerElement(new PsiExistsCondition.Factory());
        registerElement(new PsiHasClientWeatherCondition.Factory());
        registerElement(new PsiHasPermissionCondition.Factory());
        registerElement(new PsiHasPlayedBeforeCondition.Factory());
        registerElement(new PsiHasScoreboardTagCondition.Factory());
        registerElement(new PsiIsAliveCondition.Factory());
        registerElement(new PsiIsBannedCondition.Factory());
        registerElement(new PsiIsBlockingCondition.Factory());
        registerElement(new PsiIsCondition.Factory());

        //expressions
        registerElement(new PsiConsoleSenderExpression.Factory());
        registerElement(new PsiRandomNumberExpression.Factory());

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

        //literals
        registerElement(new PsiBooleanLiteral.Factory());
        registerElement(new PsiNumberLiteral.Factory());
        registerElement(new PsiPlayerLiteral.Factory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerDefaultSections() {
        registerSection(new PsiIf.Factory());
        registerSection(new PsiWhile.Factory());
    }

    /**
     * {@inheritDoc}
     */
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

        PsiBaseSection baseSection = new PsiBaseSection(skript, trigger, CommandContext.class);

        commands.put(command, baseSection);
    }

    @Override
    public void tryRegisterEvent(Skript skript, SkriptFileSection section) {
        String event = section.getText();

        if (registeredEvents.stream().anyMatch(pattern -> pattern.matcher(event).matches())) {
            events.put(event, new PsiBaseSection(skript, section, EventContext.class));
        }
    }
}
