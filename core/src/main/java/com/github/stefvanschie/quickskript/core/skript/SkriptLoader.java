package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.TypeGroup;
import com.github.stefvanschie.quickskript.core.psi.*;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.core.psi.util.CachedReflectionMethod;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.exception.IllegalFallbackAnnotationAmountException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrderHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.exception.ParsingAnnotationInvalidValueException;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.registry.*;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Instances of this class contain everything necessary for loading Skript files.
 * This allows addons to be easily loaded, as well as dropping all data after
 * all Skripts have been loaded (in order to save memory).
 * This means that the static modifier should only be used when the data is required
 * to also be present when the Skripts are being ran, not only when they are being loaded.
 *
 * @since 0.1.0
 */
public abstract class SkriptLoader {

    /**
     * A list of all psi element factories.
     */
    @NotNull
    private final Map<Type, Collection<PsiElementFactory>> elements = new HashMap<>();

    /**
     * A collection of generic factories.
     */
    @NotNull
    private final Collection<PsiGenericElementFactory> genericFactories = new HashSet<>();

    /**
     * A cache for a factory and a set of cached methods. This is gradually built up for element factories that are
     * being tested and can be used in the future to avoid having to do lookups.
     */
    @NotNull
    private final Map<PsiGenericElementFactory, Set<CachedReflectionMethod>> elementsCached = new HashMap<>();

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
     * A block data registry for working with block data
     */
    private BlockDataRegistry blockDataRegistry;

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
     * A region registry for working with regions
     */
    private RegionRegistry regionRegistry;

    /**
     * A literal registry fro working with literals
     */
    private LiteralRegistry literalRegistry;

    /**
     * A visual effect registry fro working with visual effect
     */
    private VisualEffectRegistry visualEffectRegistry;

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
            regionRegistry = new RegionRegistry();
            literalRegistry = new LiteralRegistry();
        }), CompletableFuture.runAsync(() -> {
            blockDataRegistry = new BlockDataRegistry();
            itemTypeRegistry = new ItemTypeRegistry();
            visualEffectRegistry = new VisualEffectRegistry();
        })).join();

        registerDefaultElements();
        registerDefaultSections();
        registerDefaultConverters();
        registerDefaultEvents();
    }


    /**
     * Parses text into psi elements.
     * Returns null if no element was found.
     *
     * @param input the text to be parsed
     * @param inputTypes the input types to parse for or null if all types are allowed
     * @param lineNumber the line number of the element which will potentially be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiElement<?> tryParseElement(@NotNull String input, @NotNull Type @Nullable [] inputTypes, int lineNumber) {
        input = input.trim();

        for (PsiGenericElementFactory factory : getFactories(inputTypes)) {
            Set<CachedReflectionMethod> methods = elementsCached.get(factory);

            if (methods == null) {
                methods = new HashSet<>();
                List<Method> newMethods = new ArrayList<>(Arrays.asList(factory.getClass().getMethods()));

                for (Method method : newMethods) {
                    Pattern pattern = method.getAnnotation(Pattern.class);

                    if (pattern == null) {
                        continue;
                    }

                    Class<?> searching = factory.getClass();
                    Field field = null;

                    do {
                        try {
                            field = searching.getDeclaredField(pattern.value());
                        } catch (NoSuchFieldException ignore) {}

                        searching = searching.getSuperclass();
                    } while (searching != null);

                    if (field == null) {
                        throw new IllegalStateException("Unable to find field '" + pattern.value() + "' associated with Pattern annotation");
                    }

                    field.setAccessible(true);

                    Class<?> type = field.getType();

                    SkriptPattern[] skriptPatterns;

                    try {
                        if (type == SkriptPattern.class) {
                            skriptPatterns = new SkriptPattern[]{
                                (SkriptPattern) field.get(factory)
                            };
                        } else if (type == SkriptPattern[].class) {
                            skriptPatterns = (SkriptPattern[]) field.get(factory);
                        } else {
                            continue;
                        }
                    } catch (IllegalAccessException exception) {
                        exception.printStackTrace();
                        continue;
                    }

                    methods.add(new CachedReflectionMethod(method, skriptPatterns));
                }

                elementsCached.put(factory, methods);
            }

            for (CachedReflectionMethod cachedReflectionMethod : methods) {
                Method method = cachedReflectionMethod.getMethod();
                SkriptPattern[] skriptPatterns = cachedReflectionMethod.getPatterns();

                try {
                    for (int skriptPatternIndex = 0; skriptPatternIndex < skriptPatterns.length; skriptPatternIndex++) {
                        PatternTypeOrderHolder holder = cachedReflectionMethod.getPatternTypeOrderHolder();
                        PatternTypeOrder patternTypeOrder = null;

                        if (holder != null) {
                            int amount = 0;
                            PatternTypeOrder order = null;

                            for (PatternTypeOrder typeOrder : holder.value()) {
                                for (int patternIndex : typeOrder.patterns()) {
                                    if (patternIndex != skriptPatternIndex) {
                                        continue;
                                    }

                                    amount++;
                                    order = typeOrder;
                                }
                            }

                            if (amount > 1) {
                                throw new ParsingAnnotationInvalidValueException(
                                    "Multiple PatternMetadata on the same method specify the same pattern"
                                );
                            }

                            patternTypeOrder = order;
                        }

                        SkriptPattern skriptPattern = skriptPatterns[skriptPatternIndex];
                        List<TypeGroup> typeGroups = skriptPattern.getGroups(TypeGroup.class);
                        int typeGroupAmount = typeGroups.size();

                        if (method.getParameterCount() < typeGroupAmount + 1) {
                            throw new IllegalStateException("Method '" + method.getName() + "' has "
                                + method.getParameterCount() + " parameters, but we expected at least " +
                                (typeGroupAmount + 1) + " parameters");
                        }

                        results:
                        for (SkriptMatchResult result : skriptPattern.match(input)) {
                            if (result.hasUnmatchedParts()) {
                                continue;
                            }

                            Collection<Pair<SkriptPatternGroup, String>> matchedGroups = result.getMatchedGroups();

                            List<TypeGroup> groups = new ArrayList<>();
                            List<String> matchedTypeTexts = new ArrayList<>();

                            for (Pair<SkriptPatternGroup, String> matchedGroup : matchedGroups) {
                                SkriptPatternGroup group = matchedGroup.getX();

                                if (group instanceof TypeGroup) {
                                    groups.add((TypeGroup) group);
                                    matchedTypeTexts.add(matchedGroup.getY());
                                }
                            }

                            Object[] elements = new Object[typeGroupAmount];

                            for (int i = 0; i < elements.length && i < groups.size(); i++) {
                                TypeGroup typeGroup = groups.get(i);
                                int elementIndex = -1;

                                //find the position of the matched group in the original pattern
                                for (int index = 0; index < typeGroups.size(); index++) {
                                    //reference comparison is intentional
                                    if (typeGroups.get(index) == typeGroup) {
                                        elementIndex = index;
                                        break;
                                    }
                                }

                                if (elementIndex == -1) {
                                    throw new ParseException("Unable to find matched group in pattern", lineNumber);
                                }

                                if (patternTypeOrder != null && !Arrays.equals(patternTypeOrder.typeOrder(), new int[]{})) {
                                    elementIndex = patternTypeOrder.typeOrder()[i];

                                    if (elements[elementIndex] != null) {
                                        throw new ParsingAnnotationInvalidValueException(
                                            "Type order of PatternMetadata contains duplicate number '" + elementIndex + "'"
                                        );
                                    }
                                }

                                String matchedTypeText = matchedTypeTexts.get(i);

                                if (typeGroup.getConstraint() == TypeGroup.Constraint.LITERAL) {
                                    elements[elementIndex] = matchedTypeText;
                                } else {
                                    Type[] types = typeGroup.getTypes();

                                    elements[elementIndex] = tryParseElement(matchedTypeText, types, lineNumber);
                                }

                                //recursive retry
                                if (elements[elementIndex] == null) {
                                    continue results;
                                }
                            }

                            method.setAccessible(true);

                            Class<?>[] parameterTypes = cachedReflectionMethod.getParameterTypes();
                            List<Object> parameters = new ArrayList<>(Arrays.asList(elements));

                            //allow an optional SkriptLoader in front
                            if (parameterTypes[parameters.size() - elements.length] == SkriptLoader.class) {
                                parameters.add(parameters.size() - elements.length, this);
                            }
                            //allow an optional SkriptMatchResult in front
                            if (parameterTypes[parameters.size() - elements.length] == SkriptMatchResult.class) {
                                parameters.add(parameters.size() - elements.length, result);
                            }

                            if (!(factory instanceof PsiElementFactory)) {
                                parameters.add(parameters.size() - elements.length, inputTypes);
                            }

                            parameters.add(lineNumber);

                            Object[] parameterArray = parameters.toArray(Object[]::new);
                            PsiElement<?> element = (PsiElement<?>) method.invoke(factory, parameterArray);

                            if (element == null) {
                                continue;
                            }

                            for (Object child : elements) {
                                if (child instanceof PsiElement<?>) {
                                    ((PsiElement<?>) child).setParent(element);
                                }
                            }

                            return element;
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                }
            }

            Set<Method> fallbackMethods = new HashSet<>();

            for (Method method : factory.getClass().getMethods()) {
                if (method.getAnnotation(Fallback.class) != null) {
                    fallbackMethods.add(method);
                }
            }

            int size = fallbackMethods.size();

            if (size > 1) {
                throw new IllegalFallbackAnnotationAmountException(
                    "Illegal amount of fallback annotations detected. Maximum is 1, but there were '" + size + "'."
                );
            }

            if (size == 1) {
                //will only loop once
                for (Method method : fallbackMethods) {
                    try {
                        List<Object> parameters = new ArrayList<>();

                        if (method.getParameterTypes()[0] == SkriptLoader.class) {
                            parameters.add(this);
                        }

                        if (!(factory instanceof PsiElementFactory)) {
                            parameters.add(inputTypes);
                        }

                        parameters.add(input);
                        parameters.add(lineNumber);

                        Object result = method.invoke(factory, parameters.toArray(Object[]::new));

                        if (result != null) {
                            return (PsiElement<?>) result;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }

    /**
     * Parses text into psi elements.
     * Returns null if no element was found.
     *
     * @param input the text to be parsed
     * @param type the type to parse for
     * @param lineNumber the line number of the element which will potentially be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiElement<?> tryParseElement(@NotNull String input, @NotNull Type type, int lineNumber) {
        return tryParseElement(input, new Type[] {type}, lineNumber);
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
        return tryParseElement(input, (Type[]) null, lineNumber);
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
     * Gets the factories for the given types.
     *
     * @param types the types
     * @return the factories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Collection<? extends PsiGenericElementFactory> getFactories(@NotNull Type @Nullable [] types) {
        //use a list to respect the order in which the elements are registered
        Collection<PsiGenericElementFactory> factories = new ArrayList<>();

        if (types == null) {
            for (Collection<PsiElementFactory> factoryCollection : this.elements.values()) {
                factories.addAll(factoryCollection);
            }
        } else {
            Collection<Type> allTypes = new HashSet<>();

            for (Type type : types) {
                allTypes.add(type);
                allTypes.addAll(Set.of(type.getSubtypes()));
            }

            for (Type type : allTypes) {
                factories.addAll(elements.getOrDefault(type, Collections.emptySet()));

                if (type.getSingular() != null) {
                    factories.addAll(elements.getOrDefault(type.getSingular(), Collections.emptySet()));
                }
            }
        }

        factories.addAll(this.genericFactories);

        return factories;
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
        Type type = factory.getType();

        if (type == null) {
            type = Type.OBJECT;
        }

        if (!elements.containsKey(type)) {
            elements.put(type, new ArrayList<>());
        }

        elements.get(type).add(factory);
    }

    /**
     * Registers the specified factory.
     *
     * @param factory the element factory to register
     * @since 0.1.0
     */
    protected void registerElement(@NotNull PsiGenericElementFactory factory) {
        this.genericFactories.add(factory);
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
            throw new IllegalArgumentException("A PsiConverter with the same name has already been registered.");
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
     * Gets the block data registry attached to this skript loader
     *
     * @return the block data registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public BlockDataRegistry getBlockDataRegistry() {
        return blockDataRegistry;
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
     * Gets the region registry attached to this skript loader
     *
     * @return the region registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public RegionRegistry getRegionRegistry() {
        return regionRegistry;
    }

    /**
     * Gets the literal registry attached to this skript loader
     *
     * @return the literal registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public LiteralRegistry getLiteralRegistry() {
        return literalRegistry;
    }

    /**
     * Gets the visual effect registry attached to this skript loader
     *
     * @return the visual effect registry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public VisualEffectRegistry getVisualEffectRegistry() {
        return visualEffectRegistry;
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
    public abstract void tryRegisterCommand(Skript skript, SkriptFileSection section);

    /**
     * Tries to register the event in the given section. If this is not an event, or if it could not be registered
     * for some reason, this will do nothing.
     *
     * @param skript the skript this command belongs to
     * @param section the section to parse
     * @since 0.1.0
     */
    public abstract void tryRegisterEvent(Skript skript, SkriptFileSection section);
}
