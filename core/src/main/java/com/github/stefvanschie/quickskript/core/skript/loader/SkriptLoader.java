package com.github.stefvanschie.quickskript.core.skript.loader;

import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.*;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.util.registry.BiomeRegistry;
import com.github.stefvanschie.quickskript.core.util.registry.EntityTypeRegistry;
import com.github.stefvanschie.quickskript.core.util.registry.InventoryTypeRegistry;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Instances of this class contain everything necessary for loading Skript files.
 * This allows addons to be easily loaded, as well as dropping all data after
 * all Skripts have been loaded (in order to save memory).
 * This means that the references to this class should only be kept when absolutely necessary.
 *
 * @since 0.1.0
 */
public abstract class SkriptLoader {

    /**
     * A list of all psi element factories.
     */
    @NotNull
    private final List<RegisteredPsiElementFactory> elements = new ArrayList<>();

    /**
     * A list of all psi section factories.
     */
    @NotNull
    private final List<PsiSectionFactory<?>> sections = new ArrayList<>();

    /**
     * A map indexing converters by their name.
     */
    @NotNull
    private final Map<String, PsiConverter<?>> converters = new HashMap<>();

    /**
     * A biome registry for working with biomes
     */
    private BiomeRegistry biomeRegistry;

    /**
     * An entity type registry for working with entity types
     */
    private EntityTypeRegistry entityTypeRegistry;

    /**
     * An inventory type registry for working with inventory types
     */
    private InventoryTypeRegistry inventoryTypeRegistry;

    /**
     * An item type registry for working with item types
     */
    private ItemTypeRegistry itemTypeRegistry;

    /**
     * Create a new instance, initializing it with all default (non-addon) data.
     *
     * @since 0.1.0
     */
    protected SkriptLoader() {
        CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
            biomeRegistry = new BiomeRegistry();
            entityTypeRegistry = new EntityTypeRegistry();
            inventoryTypeRegistry = new InventoryTypeRegistry();

            registerDefaultElements();
            registerDefaultSections();
            registerDefaultConverters();
            registerDefaultEvents();
        }), CompletableFuture.runAsync(() ->
            itemTypeRegistry = new ItemTypeRegistry()
        )).join(); //TODO can't we do more stuff concurrently?
    }


    /**
     * Parses text into psi elements.
     * Returns null if no element was found.
     *
     * @param input the text to be parsed
     * @param lineNumber the line number of the element which will potentially be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiElement<?> tryParseElement(@NotNull String input, int lineNumber) {
        input = input.trim();

        for (RegisteredPsiElementFactory factory : elements) {
            PsiElement<?> parsed = factory.tryParse(this, input, lineNumber);
            if (parsed != null) {
                return parsed;
            }
        }
        return null;
    }

    /**
     * Parses text into psi elements.
     * Throws a {@link ParseException} if no element was found.
     *
     * @param input the text to be parsed
     * @param lineNumber the line number of the element that will be parsed
     * @return the parsed psi element
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiElement<?> forceParseElement(@NotNull String input, int lineNumber) {
        PsiElement<?> result = tryParseElement(input, lineNumber);

        if (result != null) {
            return result;
        }

        throw new ParseException("Unable to find an expression named: " + input, lineNumber);
    }


    /**
     * Parses a file section into a psi section.
     * Throws a {@link ParseException} if the parsing wasn't successful.
     *
     * @param input the text to be parsed
     * @param elementsSupplier the action which parses the contained elements on demand
     * @param lineNumber the line number of the section that will be parsed
     * @return the parsed psi section, or null if none were found
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiSection forceParseSection(@NotNull String input,
                                        @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber) {
        for (PsiSectionFactory<?> factory : sections) {
            PsiSection result = factory.tryParse(this, input, elementsSupplier, lineNumber);

            if (result != null) {
                return result;
            }
        }

        //fall back to an 'if' statement which doesn't start with 'if'
        PsiElement<?> condition = forceParseElement(input, lineNumber);
        //try to parse the condition before all elements inside the section
        return new PsiIf(elementsSupplier.get(), condition, lineNumber);
    }

    /**
     * Returns a converter based on the specified name.
     * Returns null if no converter was found.
     *
     * @param name the name of the converter
     * @return the converter
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    private PsiConverter<?> tryGetConverter(@NotNull String name) {
        return converters.get(name);
    }

    /**
     * Returns a converter based on the specified name.
     * Throws a {@link ParseException} if no converter was found.
     *
     * @param name the name of the converter
     * @param lineNumber the line number of the element that tried to retrieve a converter
     * @return the converter
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiConverter<?> forceGetConverter(@NotNull String name, int lineNumber) {
        PsiConverter<?> result = tryGetConverter(name);

        if (result != null) {
            return result;
        }

        throw new ParseException("Unable to find a converter named: " + name, lineNumber);
    }

    /**
     * Registers the specified factory.
     *
     * @param factory the element factory to register
     * @since 0.1.0
     */
    protected void registerElement(@NotNull PsiElementFactory factory) {
        elements.add(new RegisteredPsiElementFactory(factory));
    }

    /**
     * Registers the specified factory.
     *
     * @param factory the section factory to register
     * @since 0.1.0
     */
    protected void registerSection(@NotNull PsiSectionFactory<?> factory) {
        sections.add(factory);
    }

    /**
     * Registers the specified converter.
     *
     * @param name the name of the converter
     * @param converter the converter to register
     * @since 0.1.0
     */
    protected void registerConverter(@NotNull String name, @NotNull PsiConverter<?> converter) {
        if (converters.put(name, converter) != null) {
            throw new IllegalArgumentException("A PsiConverter with the same name has already been registered: " + name);
        }
    }

    /**
     * Gets the biome registry attached to this skript loader
     *
     * @return the biome registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public BiomeRegistry getBiomeRegistry() {
        return biomeRegistry;
    }

    /**
     * Gets the entity type registry attached to this skript loader
     *
     * @return the entity type registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public EntityTypeRegistry getEntityTypeRegistry() {
        return entityTypeRegistry;
    }

    /**
     * Gets the inventory type registry attached to this skript loader
     *
     * @return the inventory type registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public InventoryTypeRegistry getInventoryTypeRegistry() {
        return inventoryTypeRegistry;
    }

    /**
     * Gets the item type registry attached to this skript loader
     *
     * @return the item type registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public ItemTypeRegistry getItemTypeRegistry() {
        return itemTypeRegistry;
    }

    /**
     * Register the default elements to be used by this parser.
     *
     * @since 0.1.0
     */
    protected abstract void registerDefaultElements();

    /**
     * Register the default sections to be used by this parser.
     *
     * @since 0.1.0
     */
    protected abstract void registerDefaultSections();

    /**
     * Register the default converters to be used by this parser.
     *
     * @since 0.1.0
     */
    protected abstract void registerDefaultConverters();

    /**
     * Register the default events to be used by this parser.
     *
     * @since 0.1.0
     */
    protected abstract void registerDefaultEvents();

    /**
     * Tries to register the command in the given section. If this is not a command, or if it could not be registered
     * for some reason, this will do nothing.
     *
     * @param skript the skript this command belongs to
     * @param section the section to parse
     * @since 0.1.0
     */
    public abstract void tryRegisterCommand(@NotNull Skript skript, @NotNull SkriptFileSection section);

    /**
     * Tries to register the event in the given section. If this is not an event, or if it could not be registered
     * for some reason, this will do nothing.
     *
     * @param skript the skript this command belongs to
     * @param section the section to parse
     * @since 0.1.0
     */
    public abstract void tryRegisterEvent(@NotNull Skript skript, @NotNull SkriptFileSection section);
}
