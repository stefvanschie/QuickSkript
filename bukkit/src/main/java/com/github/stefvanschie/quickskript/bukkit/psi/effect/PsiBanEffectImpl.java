package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiBanEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An effect for issuing (IP) bans.
 *
 * @since 0.1.0
 */
public class PsiBanEffectImpl extends PsiBanEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param object     the object to issue the (IP) ban for
     * @param reason     the reason for the ban
     * @param ipBan      true if this ban is an IP ban, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiBanEffectImpl(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason, boolean ipBan,
                               int lineNumber) {
        super(object, reason, ipBan, lineNumber);
    }

    @Nullable
    @Contract(value = "_ -> null", pure = true)
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Object object = this.object.execute(context);
        BanList banList = Bukkit.getBanList(ipBan ? BanList.Type.IP : BanList.Type.NAME);
        String reason = this.reason == null ? null : this.reason.execute(context, Text.class).toString();

        if (object instanceof Text) {
            banList.addBan(object.toString(), reason, null, null);
        } else if (object instanceof OfflinePlayer) {
            String name = ((OfflinePlayer) object).getName();

            if (name == null) {
                throw new ExecutionException("Unable to find name of specified player", lineNumber);
            }

            banList.addBan(name, reason, null, null);
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiBanEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiBanEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiBanEffect create(@NotNull PsiElement<?> object, @Nullable PsiElement<?> reason, boolean ipBan,
                                   int lineNumber) {
            return new PsiBanEffectImpl(object, reason, ipBan, lineNumber);
        }
    }
}
