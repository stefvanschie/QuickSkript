package com.github.stefvanschie.quickskript.core.util;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents different types of values a psi element may return. This is a high-level overview and a psi element is
 * able to, for example, choose an underlying collection for its values itself.
 *
 * @since 0.1.0
 */
public enum Type {

    /**
     * A single biome.
     *
     * @since 0.1.0
     */
    BIOME("biome"),

    /**
     * Multiple biomes.
     *
     * @since 0.1.0
     */
    BIOMES("biomes", BIOME),

    /**
     * A single block data.
     *
     * @since 0.1.0
     */
    BLOCK_DATA("block[ ]data"),

    /**
     * Multiple block datas.
     *
     * @since 0.1.0
     */
    BLOCK_DATAS("block[ ]datas", BLOCK_DATA),

    /**
     * A single boolean.
     *
     * @since 0.1.0
     */
    BOOLEAN("boolean"),

    /**
     * Multiple booleans.
     *
     * @since 0.1.0
     */
    BOOLEANS("booleans", BOOLEAN),

    /**
     * A single cat type.
     *
     * @since 0.1.0
     */
    CAT_TYPE("cat[ ](type|race)"),

    /**
     * Multiple cat types.
     *
     * @since 0.1.0
     */
    CAT_TYPES("cat[ ](type|race)s", CAT_TYPE),

    /**
     * A single click type.
     *
     * @since 0.1.0
     */
    CLICK_TYPE("click[ ]type"),

    /**
     * Multiple click types.
     *
     * @since 0.1.0
     */
    CLICK_TYPES("click[ ]type[s]", CLICK_TYPE),

    /**
     * A single color.
     *
     * @since 0.1.0
     */
    COLOR("colo[u]r"),

    /**
     * Multiple colors.
     *
     * @since 0.1.0
     */
    COLORS("colo[u]rs", COLOR),

    /**
     * A singular command sender.
     *
     * @since 0.1.0
     */
    COMMAND_SENDER("[[command[s]][ ]](sender|executor)"),

    /**
     * Multiple command senders.
     *
     * @since 0.1.0
     */
    COMMAND_SENDERS("[[command[s]][ ]](sender|executor)s", COMMAND_SENDER),

    /**
     * A single damage cause.
     *
     * @since 0.1.0
     */
    DAMAGE_CAUSE("damage[ ]cause"),

    /**
     * Multiple damage causes.
     *
     * @since 0.1.0
     */
    DAMAGE_CAUSES("damage[ ]causes", DAMAGE_CAUSE),

    /**
     * A single date.
     *
     * @since 0.1.0
     */
    DATE("date"),

    /**
     * Multiple dates.
     *
     * @since 0.1.0
     */
    DATES("dates", DATE),

    /**
     * A single direction.
     *
     * @since 0.1.0
     */
    DIRECTION("direction"),

    /**
     * Multiple directions.
     *
     * @since 0.1.0
     */
    DIRECTIONS("directions", DIRECTION),

    /**
     * A single enchantment.
     *
     * @since 0.1.0
     */
    ENCHANTMENT("enchantment"),

    /**
     * Multiple enchantments.
     *
     * @since 0.1.0
     */
    ENCHANTMENTS("enchantments", ENCHANTMENT),

    /**
     * A single entity.
     *
     * @since 0.1.0
     */
    ENTITY("entity"),

    /**
     * A single type of entity.
     *
     * @since 0.1.0
     */
    ENTITY_TYPE("entity[ ]type"),

    /**
     * Multiple types of entities.
     *
     * @since 0.1.0
     */
    ENTITY_TYPES("entity[ ]types", ENTITY_TYPE),

    /**
     * Multiple entities.
     *
     * @since 0.1.0
     */
    ENTITIES("entities", ENTITY),

    /**
     * A single experience point.
     *
     * @since 0.1.0
     */
    EXPERIENCE_POINT("experience[ ][point]"),

    /**
     * Multiple experience points.
     *
     * @since 0.1.0
     */
    EXPERIENCE_POINTS("experience[ ]points", EXPERIENCE_POINT),

    /**
     * A single firework type.
     *
     * @since 0.1.0
     */
    FIREWORK_TYPE("firework[ ]type"),

    /**
     * Multiple firework types.
     *
     * @since 0.1.0
     */
    FIREWORK_TYPES("firework[ ]types", FIREWORK_TYPE),

    /**
     * A single game mode.
     *
     * @since 0.1.0
     */
    GAME_MODE("game[ ]mode"),

    /**
     * Multiple game modes.
     *
     * @since 0.1.0
     */
    GAME_MODES("game[ ]modes", GAME_MODE),

    /**
     * A single gene.
     *
     * @since 0.1.0
     */
    GENE("[panda] gene"),

    /**
     * Multiple genes.
     *
     * @since 0.1.0
     */
    GENES("[panda] genes", GENE),

    /**
     * A single inventory.
     *
     * @since 0.1.0
     */
    INVENTORY("inventory"),

    /**
     * Multiple inventories.
     *
     * @since 0.1.0
     */
    INVENTORIES("inventories", INVENTORY),

    /**
     * A single inventory action.
     *
     * @since 0.1.0
     */
    INVENTORY_ACTION("inventory[ ]action"),

    /**
     * Multiple inventory actions.
     *
     * @since 0.1.0
     */
    INVENTORY_ACTIONS("inventory[ ]actions", INVENTORY_ACTION),

    /**
     * A single inventory type.
     *
     * @since 0.1.0
     */
    INVENTORY_TYPE("inventory[ ]type"),

    /**
     * Multiple inventory types.
     *
     * @since 0.1.0
     */
    INVENTORY_TYPES("inventory[ ]types", INVENTORY_TYPE),

    /**
     * Multiple item types.
     *
     * @since 0.1.0
     */
    ITEM_TYPES("(item[ ]type[s]|items|materials)"),

    /**
     * A single item.
     *
     * @since 0.1.0
     */
    ITEM("(item|material)"),

    /**
     * A single living entity.
     *
     * @since 0.1.0
     */
    LIVING_ENTITY("living[ ]entity"),

    /**
     * Multiple living entites.
     *
     * @since 0.1.0
     */
    LIVING_ENTITIES("living[ ]entities", LIVING_ENTITY),

    /**
     * A single location.
     *
     * @since 0.1.0
     */
    LOCATION("location"),

    /**
     * Multiple locations.
     *
     * @since 0.1.0
     */
    LOCATIONS("locations", LOCATION),

    /**
     * A single number.
     *
     * @since 0.1.0
     */
    NUMBER("num[ber]"),

    /**
     * Multiple numbers.
     *
     * @since 0.1.0
     */
    NUMBERS("num[ber]s", NUMBER),

    /**
     * A single object.
     *
     * @since 0.1.0
     */
    OBJECT("object"),

    /**
     * Multiple objects.
     *
     * @since 0.1.0
     */
    OBJECTS("objects", OBJECT),

    /**
     * A single offline player.
     *
     * @since 0.1.0
     */
    OFFLINE_PLAYER("offline[ ]player"),

    /**
     * Multiple offline players.
     *
     * @since 0.1.0
     */
    OFFLINE_PLAYERS("offline[ ]players", OFFLINE_PLAYER),

    /**
     * A single player.
     *
     * @since 0.1.0
     */
    PLAYER("player"),

    /**
     * Multiple players.
     *
     * @since 0.1.0
     */
    PLAYERS("players", PLAYER),

    /**
     * A single region.
     *
     * @since 0.1.0
     */
    REGION("region"),

    /**
     * Multiple regions.
     *
     * @since 0.1.0
     */
    REGIONS("regions", REGION),

    /**
     * A single resource pack status.
     *
     * @since 0.1.0
     */
    RESOURCE_PACK_STATUS("resource[ ]pack[ ]state"),

    /**
     * Multiple resource pack statuses.
     *
     * @since 0.1.0
     */
    RESOURCE_PACK_STATUSES("resource[ ]pack[ ]states", RESOURCE_PACK_STATUS),

    /**
     * A single sound category.
     *
     * @since 0.1.0
     */
    SOUND_CATEGORY("sound[ ]category"),

    /**
     * Multiple sound categories.
     *
     * @since 0.1.0
     */
    SOUND_CATEGORIES("sound[ ]categories", SOUND_CATEGORY),

    /**
     * A single spawn reason.
     *
     * @since 0.1.0
     */
    SPAWN_REASON("spawn[ing][ ]reason"),

    /**
     * Multiple spawn reasons.
     *
     * @since 0.1.0
     */
    SPAWN_REASONS("spawn[ing][ ]reasons", SPAWN_REASON),

    /**
     * A single status effect type.
     *
     * @since 0.1.0
     */
    STATUS_EFFECT_TYPE("potion[[ ]effect][ ]type"),

    /**
     * Multiple status effect types.
     *
     * @since 0.1.0
     */
    STATUS_EFFECT_TYPES("potion[[ ]effect][ ]types", STATUS_EFFECT_TYPE),

    /**
     * A single text.
     *
     * @since 0.1.0
     */
    TEXT("(text|string)"),

    /**
     * Multiple texts.
     *
     * @since 0.1.0
     */
    TEXTS("(text|string)s", TEXT),

    /**
     * A single teleport cause.
     *
     * @since 0.1.0
     */
    TELEPORT_CAUSE("teleport[ ](cause|reason|type)"),

    /**
     * Multiple teleport causes.
     *
     * @since 0.1.0
     */
    TELEPORT_CAUSES("teleport[ ](cause|reason|type)s", TELEPORT_CAUSE),

    /**
     * A single time.
     *
     * @since 0.1.0
     */
    TIME("time"),

    /**
     * Multiple times.
     *
     * @since 0.1.0
     */
    TIMES("times", TIME),

    /**
     * A single time period.
     *
     * @since 0.1.0
     */
    TIME_PERIOD("(time[ ]period|duration)"),

    /**
     * Multiple time periods.
     *
     * @since 0.1.0
     */
    TIME_PERIODS("(time[ ]period|duration)s", TIME_PERIOD),

    /**
     * A single time span.
     *
     * @since 0.1.0
     */
    TIME_SPAN("time[ ]span"),

    /**
     * Multiple time spans.
     *
     * @since 0.1.0
     */
    TIME_SPANS("time[ ]spans", TIME_SPAN),

    /**
     * A single tree type.
     *
     * @since 0.1.0
     */
    TREE_TYPE("(tree[ ]type|tree)"),

    /**
     * Multiple tree types.
     *
     * @since 0.1.0
     */
    TREE_TYPES("(tree[ ]type|tree)s", TREE_TYPE),

    /**
     * A single type.
     *
     * @since 0.1.0
     */
    TYPE("type"),

    /**
     * Multiple types.
     *
     * @since 0.1.0
     */
    TYPES("types", TYPE),

    /**
     * A single visual effect.
     *
     * @since 0.1.0
     */
    VISUAL_EFFECT("visual effect"),

    /**
     * A single weather type.
     *
     * @since 0.1.0
     */
    WEATHER_TYPE("weather([ ]type|[ condition])"),

    /**
     * Multiple weather types.
     *
     * @since 0.1.0
     */
    WEATHER_TYPES("weather([ ]types|[ conditions])", WEATHER_TYPE),

    /**
     * A single world.
     *
     * @since 0.1.0
     */
    WORLD("world"),

    /**
     * Multiple worlds.
     *
     * @since 0.1.0
     */
    WORLDS("worlds", WORLD);

    /**
     * The names of the type.
     */
    @NotNull
    private final Collection<? extends String> names;

    /**
     * The singular variant of this type. This is null if this type is singular or if this type doesn't have a singular
     * form.
     */
    @Nullable
    private final Type singular;

    /**
     * The entries of this enum, indexed by name.
     */
    @NotNull
    private static final Map<String, Type> ENTRIES = new HashMap<>();

    /**
     * A type with a given pattern for its names.
     *
     * @param pattern the pattern for the names
     * @since 0.1.0
     */
    Type(@NotNull String pattern) {
        this.names = SkriptPattern.parse(pattern).unrollFully();
        this.singular = null;
    }

    /**
     * A type with a given pattern for its names.
     *
     * @param pattern the pattern for the names
     * @since 0.1.0
     */
    Type(@NotNull String pattern, @NotNull Type singular) {
        this.names = SkriptPattern.parse(pattern).unrollFully();
        this.singular = singular;
    }

    /**
     * Gets the names of this type. The returned collection of names is unmodifiable.
     *
     * @return the names
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends String> getNames() {
        return Collections.unmodifiableCollection(names);
    }

    /**
     * Gets the singular variant of this type. This returns null if this type is singular or doesn't have a singular
     * form.
     *
     * @return the singular type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Type getSingular() {
        return singular;
    }

    /**
     * Gets the type by the given name or null if no such type exists.
     *
     * @param name the name of the type
     * @return the type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static Type byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (Type type : Type.values()) {
            for (String name : type.getNames()) {
                ENTRIES.put(name, type);
            }
        }
    }
}
