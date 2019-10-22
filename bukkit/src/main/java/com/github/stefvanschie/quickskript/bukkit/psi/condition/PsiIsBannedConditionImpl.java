package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBannedCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether an offline player or ip is (ip) banned. This cannot be pre computed, since a ban may be set/revoked
 * during game play.
 *
 * @since 0.1.0
 */
public class PsiIsBannedConditionImpl extends PsiIsBannedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param object     the object to check the ban for
     * @param positive   false if the execution result needs to be inverted
     * @param ipBan      true if we're checking ip bans, false otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBannedConditionImpl(@NotNull PsiElement<?> object, boolean positive, boolean ipBan, int lineNumber) {
        super(object, positive, ipBan, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        Object object = this.object.execute(context);
        BanList banList = Bukkit.getBanList(ipBan ? BanList.Type.IP : BanList.Type.NAME);

        if (object instanceof OfflinePlayer) {
            String name = ((OfflinePlayer) object).getName();

            if (name == null) {
                throw new ExecutionException("Unable to lookup name of player", lineNumber);
            }

            return positive == banList.isBanned(name);
        }

        if (object instanceof Text) {
            return positive == banList.isBanned(((Text) object).toString());
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiIsBannedConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBannedCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBannedCondition create(@NotNull PsiElement<?> object, boolean positive, boolean ipBan,
                                           int lineNumber) {
            return new PsiIsBannedConditionImpl(object, positive, ipBan, lineNumber);
        }
    }
}
