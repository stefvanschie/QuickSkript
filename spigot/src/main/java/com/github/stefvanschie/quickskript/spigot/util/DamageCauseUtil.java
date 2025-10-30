package com.github.stefvanschie.quickskript.spigot.util;

import com.github.stefvanschie.quickskript.core.util.literal.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for damage causes.
 *
 * @since 0.1.0
 */
public class DamageCauseUtil {

    /**
     * A mapping between QuickSkript damage causes and Bukkit damage causes.
     */
    @NotNull
    private static final Map<? super @NotNull DamageCause,
        EntityDamageEvent.@NotNull DamageCause> MAPPINGS = new HashMap<>();

    /**
     * Private constructor to prevent instantiation.
     *
     * @since 0.1.0
     */
    private DamageCauseUtil() {}

    @NotNull
    @Contract(pure = true)
    public static EntityDamageEvent.DamageCause convert(@NotNull DamageCause damageCause) {
        EntityDamageEvent.DamageCause bukkitDamageCause = MAPPINGS.get(damageCause);

        if (bukkitDamageCause == null) {
            throw new IllegalArgumentException("Unknown damage cause");
        }

        return bukkitDamageCause;
    }

    static {
        MAPPINGS.put(DamageCause.ATTACK, EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        MAPPINGS.put(DamageCause.BLOCK_EXPLOSION, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        MAPPINGS.put(DamageCause.BURNING, EntityDamageEvent.DamageCause.FIRE_TICK);
        MAPPINGS.put(DamageCause.CONTACT, EntityDamageEvent.DamageCause.CONTACT);
        MAPPINGS.put(DamageCause.CRAMMING, EntityDamageEvent.DamageCause.CRAMMING);
        MAPPINGS.put(DamageCause.DRAGON_BREATH, EntityDamageEvent.DamageCause.DRAGON_BREATH);
        MAPPINGS.put(DamageCause.DROWNING, EntityDamageEvent.DamageCause.DROWNING);
        MAPPINGS.put(DamageCause.DRYOUT, EntityDamageEvent.DamageCause.DRYOUT);
        MAPPINGS.put(DamageCause.ENTITY_EXPLOSION, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
        MAPPINGS.put(DamageCause.FALL, EntityDamageEvent.DamageCause.FALL);
        MAPPINGS.put(DamageCause.FALLING_BLOCK, EntityDamageEvent.DamageCause.FALLING_BLOCK);
        MAPPINGS.put(DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE);
        MAPPINGS.put(DamageCause.HITTING_WALL_WHILE_FLYING, EntityDamageEvent.DamageCause.FLY_INTO_WALL);
        MAPPINGS.put(DamageCause.LAVA, EntityDamageEvent.DamageCause.LAVA);
        MAPPINGS.put(DamageCause.LIGHTNING, EntityDamageEvent.DamageCause.LIGHTNING);
        MAPPINGS.put(DamageCause.MAGMA, EntityDamageEvent.DamageCause.HOT_FLOOR);
        MAPPINGS.put(DamageCause.MELTING, EntityDamageEvent.DamageCause.MELTING);
        MAPPINGS.put(DamageCause.POISON, EntityDamageEvent.DamageCause.POISON);
        MAPPINGS.put(DamageCause.POTION, EntityDamageEvent.DamageCause.MAGIC);
        MAPPINGS.put(DamageCause.PROJECTILE, EntityDamageEvent.DamageCause.PROJECTILE);
        MAPPINGS.put(DamageCause.STARVATION, EntityDamageEvent.DamageCause.STARVATION);
        MAPPINGS.put(DamageCause.SUFFOCATION, EntityDamageEvent.DamageCause.SUFFOCATION);
        MAPPINGS.put(DamageCause.SUICIDE, EntityDamageEvent.DamageCause.SUICIDE);
        MAPPINGS.put(DamageCause.SWEEP_ATTACK, EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK);
        MAPPINGS.put(DamageCause.THORNS, EntityDamageEvent.DamageCause.THORNS);
        MAPPINGS.put(DamageCause.UNKNOWN, EntityDamageEvent.DamageCause.CUSTOM);
        MAPPINGS.put(DamageCause.VOID, EntityDamageEvent.DamageCause.VOID);
        MAPPINGS.put(DamageCause.WITHER, EntityDamageEvent.DamageCause.WITHER);
    }
}
