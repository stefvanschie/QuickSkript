package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFireResistantCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.paper.util.ItemTypeUtil;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DamageResistant;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.tags.DamageTypeTagKeys;
import org.bukkit.Registry;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Checks if the item types are fire-resistant.
 *
 * @since 0.1.0
 */
public class PsiIsFireResistantConditionImpl extends PsiIsFireResistantCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are fire-resistant
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFireResistantConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.itemTypes.executeMulti(environment, context, ItemType.class).test(itemType -> {
            ItemStack itemStack = ItemTypeUtil.convertToItemStack(itemType);

            if (itemStack == null) {
                return false;
            }

            DamageResistant damageResistant = itemStack.getData(DataComponentTypes.DAMAGE_RESISTANT);

            if (damageResistant == null) {
                return false;
            }

            Registry<DamageType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE);
            Collection<DamageType> damageTypes = registry.getTagValues(DamageTypeTagKeys.IS_FIRE);

            return registry.getTagValues(damageResistant.types()).containsAll(damageTypes);
        });
    }

    /**
     * A factory for constructing {@link PsiIsFireResistantCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFireResistantCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFireResistantCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsFireResistantConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
