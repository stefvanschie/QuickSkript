package com.github.stefvanschie.quickskript.bukkit.psi.literal;

import com.github.stefvanschie.quickskript.bukkit.integration.money.VaultIntegration;
import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiMoneyLiteral;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Money;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a certain amount of money.
 *
 * @since 0.1.0
 */
public class PsiMoneyLiteralImpl extends PsiMoneyLiteral {

    /**
     * Creates a new element with the given line number
     *
     * @param money the money this literal represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiMoneyLiteralImpl(@NotNull Money money, int lineNumber) {
        super(money, lineNumber);
    }

    /**
     * A factory for creating {@link PsiMoneyLiteralImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMoneyLiteral.Factory {

        @Nullable
        @Contract(pure = true)
        @Fallback
        @Override
        public PsiMoneyLiteral parse(@NotNull String text, int lineNumber) {
            VaultIntegration vaultIntegration = QuickSkript.getInstance().getVaultIntegration();

            if (vaultIntegration == null) {
                return null;
            }

            String currencyName = vaultIntegration.getCurrencyName();

            if (currencyName.isEmpty()) {
                return null;
            }

            System.out.println("Currency: " + currencyName);

            int length = currencyName.length();

            if (text.startsWith(currencyName)) {
                text = text.substring(length).trim();
            } else if (text.endsWith(currencyName)) {
                text = text.substring(0, text.length() - length).trim();
            } else {
                return null;
            }

            return create(new Money(Double.parseDouble(text)), lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiMoneyLiteral create(@NotNull Money money, int lineNumber) {
            return new PsiMoneyLiteralImpl(money, lineNumber);
        }
    }
}
