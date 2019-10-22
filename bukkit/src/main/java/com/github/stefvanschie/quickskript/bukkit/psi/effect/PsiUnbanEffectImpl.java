package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiUnbanEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An effect for undoing (IP) bans.
 *
 * @since 0.1.0
 */
public class PsiUnbanEffectImpl extends PsiUnbanEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param object     the object to undo the (IP) ban for
     * @param ipBan      true if this ban is an IP ban, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiUnbanEffectImpl(@NotNull PsiElement<?> object, boolean ipBan, int lineNumber) {
        super(object, ipBan, lineNumber);
    }

    @Nullable
    @Contract(value = "_ -> null", pure = true)
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Object object = this.object.execute(context);
        BanList banList = Bukkit.getBanList(ipBan ? BanList.Type.IP : BanList.Type.NAME);

        if (object instanceof Text) {
            banList.pardon(object.toString());
        } else if (object instanceof OfflinePlayer) {
            String name = ((OfflinePlayer) object).getName();

            if (name == null) {
                throw new ExecutionException("Unable to find name of specified player", lineNumber);
            }

            banList.pardon(name);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiUnbanEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiUnbanEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiUnbanEffect create(@NotNull PsiElement<?> object, boolean ipBan, int lineNumber) {
            return new PsiUnbanEffectImpl(object, ipBan, lineNumber);
        }
    }
}
