package com.mvplugin.core;

import com.mvplugin.core.minecraft.Difficulty;
import com.mvplugin.core.minecraft.GameMode;
import com.mvplugin.core.minecraft.PortalType;
import com.mvplugin.core.minecraft.WorldEnvironment;
import com.mvplugin.core.minecraft.WorldType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pluginbase.config.field.Field;
import pluginbase.config.field.FieldMap;
import pluginbase.config.field.FieldMapper;
import pluginbase.config.field.PropertyVetoException;
import pluginbase.config.properties.PropertiesWrapper;
import pluginbase.config.properties.PropertyAliases;
import pluginbase.minecraft.BasePlayer;
import pluginbase.minecraft.location.FacingCoordinates;
import pluginbase.minecraft.location.Locations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MultiverseWorld {

    @NotNull
    private final WorldProperties properties;
    @NotNull
    private final WorldLink worldLink;

    MultiverseWorld(@NotNull final WorldProperties worldProperties, @NotNull final WorldLink worldLink) {
        this.properties = worldProperties;
        this.worldLink = worldLink;
        getProperties().linkToWorld(worldLink);
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean equals(final Object obj) {
        return obj instanceof MultiverseWorld && ((MultiverseWorld) obj).getName().equals(getName());
    }

    @NotNull
    WorldProperties getProperties() {
        return properties;
    }

    /**
     * Gets the name of this world.  The name cannot be changed.
     * <p>
     * Note for plugin developers: Usually {@link #getAlias()} is what you want to use instead of this method.
     *
     * @return The name of the world as a String.
     */
    @NotNull
    public String getName() {
        return getProperties().getName();
    }

    /**
     * Gets the UUID for this world.
     *
     * This is what Minecraft uses to keep tracks of worlds.
     *
     * @return The UUID for this world.
     */
    @NotNull
    public UUID getWorldUID() {
        return getProperties().getUUID();
    }

    @NotNull
    public WorldType getWorldType() {
        return getProperties().getType();
    }

    @NotNull
    public String getTime() {
        return getProperties().getTime();
    }

    public void setTime(@NotNull final String timeAsString) {
        getProperties().setTime(timeAsString);
    }

    @NotNull
    public Collection<BasePlayer> getPlayers() {
        return this.worldLink.getPlayers();
    }

    @NotNull
    public WorldEnvironment getEnvironment() {
        return getProperties().getEnvironment();
    }

    public void setEnvironment(@NotNull final WorldEnvironment environment) {
        getProperties().setEnvironment(environment);
}

    @NotNull
    public Difficulty getDifficulty() {
        return getProperties().getDifficulty();
    }

    public void setDifficulty(@NotNull final Difficulty difficulty) {
        // TODO Validate?
        getProperties().setDifficulty(difficulty);
    }

    public long getSeed() {
        return getProperties().getSeed();
    }

    public void setSeed(long seed) {
        getProperties().setSeed(seed);
    }

    public String getGenerator() {
        return getProperties().getGenerator();
    }

    public void setGenerator(@Nullable final String generator) {
        getProperties().setGenerator(generator == null ? "" : generator);
    }

    @NotNull
    public static String[] getAllPropertyNames() {
        return PropertiesWrapper.getAllPropertyNames(WorldProperties.class);
    }

    @Nullable
    public static String getPropertyDescriptionKey(String name) throws NoSuchFieldException {
        String[] path = PropertyAliases.getPropertyName(WorldProperties.class, name);
        if (path == null) {
            path = name.split("\\.");
        }
        Field field = getWorldPropertiesField(path);
        if (field.isImmutable()) {
            throw new NoSuchFieldException();
        }
        return field.getDescription();
    }

    private static Field getWorldPropertiesField(String... name) throws NoSuchFieldException {
        FieldMap fieldMap = FieldMapper.getFieldMap(WorldProperties.class);
        Field field = null;
        for (String s : name) {
            field = fieldMap.getField(s);
            if (field != null) {
                fieldMap = field;
            } else {
                break;
            }
        }
        if (field == null) {
            throw new NoSuchFieldException("No such property");
        }
        return field;
    }

    @NotNull
    public String getAlias() {
        String alias = getProperties().getAlias();
        return alias.isEmpty() ? getName() : alias;
    }

    public void setAlias(@Nullable final String alias) {
        getProperties().setAlias(alias == null ? "" : alias);
    }

    public boolean isPVPEnabled() {
        return getProperties().isPVPEnabled();
    }

    public void setPVP(final boolean pvp) {
        getProperties().setPVPEnabled(pvp);
    }

    public boolean isHidden() {
        return getProperties().isHidden();
    }

    public void setHidden(final boolean hidden) {
        getProperties().setHidden(hidden);
    }

    public boolean isFormatChatEnabled() {
        return getProperties().isFormattingChat();
    }

    public void setFormatChat(final boolean formatChat) {
        getProperties().setFormattingChat(formatChat);
    }

    public boolean isWeatherEnabled() {
        return getProperties().isAllowWeather();
    }

    public void setEnableWeather(final boolean enableWeather) {
        getProperties().setAllowWeather(enableWeather);
    }


    public boolean isKeepSpawnInMemoryEnabled() {
        return getProperties().isKeepSpawnInMemory();
    }

    public void setKeepSpawnInMemory(final boolean keepSpawnInMemory) {
        getProperties().setKeepSpawnInMemory(keepSpawnInMemory);
    }

    @NotNull
    public FacingCoordinates getSpawnLocation() {
        return getProperties().getSpawnLocation();
    }

    public void setSpawnLocation(@Nullable final FacingCoordinates spawnLocation) {
        getProperties().setSpawnLocation(spawnLocation != null ? spawnLocation : Locations.NULL_FACING);
    }

    public boolean isHungerEnabled() {
        return getProperties().isHunger();
    }

    public void setHunger(final boolean hungerEnabled) {
        getProperties().setHunger(hungerEnabled);
    }

    @NotNull
    public GameMode getGameMode() {
        return getProperties().getGameMode();
    }

    public void setGameMode(@NotNull final GameMode gameMode) {
        // Todo validate?
        getProperties().setGameMode(gameMode);
    }

    public double getPrice() {
        return getProperties().getEntryFee().getAmount();
    }

    public void setPrice(final double price) {
        getProperties().getEntryFee().setAmount(price);
    }

    public int getCurrency() {
        return getProperties().getEntryFee().getCurrency();
    }

    public void setCurrency(final int item) {
        getProperties().getEntryFee().setCurrency(item);
    }

    @NotNull
    public String getRespawnToWorld() {
        return getProperties().getRespawnWorld();
    }

    public void setRespawnToWorld(@Nullable final String respawnWorld) {
        getProperties().setRespawnWorld(respawnWorld == null ? "" : respawnWorld);
    }

    public boolean isAutoHealEnabled() {
        return getProperties().isAutoHeal();
    }

    public void setAutoHeal(final boolean heal) {
        getProperties().setAutoHeal(heal);
    }

    public boolean isAdjustSpawnEnabled() {
        return getProperties().isAdjustingSpawn();
    }

    public void setAdjustSpawn(final boolean adjust) {
        getProperties().setAdjustingSpawn(adjust);
    }

    public boolean isAutoLoadEnabled() {
        return getProperties().isAutoLoad();
    }

    public void setAutoLoad(final boolean autoLoad) {
        getProperties().setAutoLoad(autoLoad);
    }

    public boolean isBedRespawnEnabled() {
        return getProperties().isBedRespawn();
    }

    public void setBedRespawn(final boolean bedRespawn) {
        getProperties().setBedRespawn(bedRespawn);
    }

    public void setPlayerLimit(final int limit) {
        getProperties().setPlayerLimit(limit);
    }

    public int getPlayerLimit() {
        return getProperties().getPlayerLimit();
    }

    @NotNull
    public List<String> getWorldBlacklist() {
        return getProperties().getWorldBlackList();
    }

    public long getTicksPerAnimalSpawn() {
        return getProperties().getSpawning().getAnimalTicks();
    }

    public void setTicksPerAnimalSpawn(final long ticks) {
        getProperties().getSpawning().setAnimalTicks(ticks);
    }

    public long getTicksPerMonsterSpawn() {
        return getProperties().getSpawning().getMonsterTicks();
    }

    public void setTicksPerMonsterSpawn(final long ticks) {
        getProperties().getSpawning().setMonsterTicks(ticks);
    }

    public int getAnimalSpawnLimit() {
        return getProperties().getSpawning().getAnimalLimit();
    }

    public void setAnimalSpawnLimit(final int limit) {
        getProperties().getSpawning().setAnimalLimit(limit);
    }

    public int getMonsterSpawnLimit() {
        return getProperties().getSpawning().getMonsterLimit();
    }

    public void setMonsterSpawnLimit(final int limit) {
        getProperties().getSpawning().setMonsterLimit(limit);
    }

    public int getAmbientSpawnLimit() {
        return getProperties().getSpawning().getAmbientLimit();
    }

    public void setAmbientSpawnLimit(final int limit) {
        getProperties().getSpawning().setAmbientLimit(limit);
    }

    public int getWaterAnimalSpawnLimit() {
        return getProperties().getSpawning().getWaterLimit();
    }

    public void setWaterAnimalSpawnLimit(final int limit) {
        getProperties().getSpawning().setWaterLimit(limit);
    }

    public boolean isPreventingSpawnsList() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPreventingSpawnsList(final boolean prevent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, SpawnException> getSpawnExceptions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addOrUpdateSpawnException(@NotNull final SpawnException spawnException) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeSpawnException(@NotNull final String creatureType) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getScale(@NotNull PortalType portalType) {
        return getProperties().getConnectedWorld(portalType).getScale();
    }

    public void setScale(@NotNull PortalType portalType, double scale) {
        getProperties().getConnectedWorld(portalType).setScale(scale);
    }

    public boolean getPortalForm(@NotNull PortalType portalType) {
        return getProperties().getConnectedWorld(portalType).getPortalForm();
    }

    public void setPortalForm(@NotNull PortalType portalType, boolean portalForm) {
        getProperties().getConnectedWorld(portalType).setPortalForm(portalForm);
    }

    @Nullable
    public Object getProperty(@NotNull String name) throws NoSuchFieldException {
        return getProperties().getProperty(name);
    }

    public void setProperty(@NotNull String name, @NotNull String value) throws IllegalAccessException, NoSuchFieldException, PropertyVetoException, IllegalArgumentException {
        getProperties().setProperty(name, value);
    }

    public void addProperty(@NotNull String name, @NotNull String value) throws IllegalAccessException, NoSuchFieldException, PropertyVetoException, IllegalArgumentException {
        getProperties().addProperty(name, value);
    }

    public void removeProperty(@NotNull String name, @NotNull String value) throws IllegalAccessException, NoSuchFieldException, PropertyVetoException, IllegalArgumentException {
        getProperties().removeProperty(name, value);
    }

    public void clearProperty(@NotNull String name, @Nullable String value) throws IllegalAccessException, NoSuchFieldException, PropertyVetoException, IllegalArgumentException {
        getProperties().clearProperty(name, value);
    }

    @Nullable
    public Object getPropertyUnchecked(@NotNull String name) {
        return getProperties().getPropertyUnchecked(name);
    }

    public boolean setPropertyUnchecked(@NotNull String name, @NotNull String value) {
        return getProperties().setPropertyUnchecked(name, value);
    }

    public boolean addPropertyUnchecked(@NotNull String name, @NotNull String value) {
        return getProperties().addPropertyUnchecked(name, value);
    }

    public boolean removePropertyUnchecked(@NotNull String name, @NotNull String value) {
        return getProperties().removePropertyUnchecked(name, value);
    }

    public boolean clearPropertyUnchecked(@NotNull String name, @Nullable String value) {
        return getProperties().clearPropertyUnchecked(name, value);
    }
}
