package com.github.stefvanschie.quickskript.bukkit.integration.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Integrates with Vault.
 *
 * @since 0.1.0
 */
public class VaultIntegration {

    /**
     * Represents the economy used on this server. This is null if the server doesn't have an economy.
     */
    @Nullable
    private Economy economy;

    /**
     * Creates a new vault integration. This should only be done prior to checking tat Vault exists on the server.
     *
     * @since 0.1.0
     */
    public VaultIntegration() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp != null) {
            economy = rsp.getProvider();
        }
    }

    /**
     * Gets the singular name of the currency used on this server. This throw an {@link IllegalStateException} if Vault
     * integration hasn't been enabled as per {@link #isEnabled()}.
     *
     * @return the name of the currency
     * @since 0.1.0
     * @throws IllegalStateException when vault integration is disabled
     */
    @NotNull
    @Contract(pure = true)
    public String getCurrencyName() {
        if (economy == null) {
            throw new IllegalStateException("Vault integration has failed to enable");
        }

        return economy.currencyNamePlural();
    }

    /**
     * Checks if the integration with Vault has been enabled
     *
     * @return true if vault integration is enabled, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isEnabled() {
        return economy != null;
    }
}
