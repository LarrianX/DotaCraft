package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.hero.DotaHero;
import com.larrian.dotacraft.hero.Skill;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.ModRegistries;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Set;

import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;

@Mixin(InGameHud.class)
public class BarsMixin {
    @Unique
    private static final Identifier ICONS = new Identifier("dotacraft:textures/icons.png");
    @Unique
    private static final int MAX_PIXELS = 88;
    @Unique
    private static final DecimalFormat OUTPUT_FORMAT = new DecimalFormat("00");
    @Unique
    private static final DecimalFormat ATTRIBUTES_FORMAT = new DecimalFormat("#.##########");

    /**
     * Calculate the number of pixels to fill for a given value.
     */
    @Unique
    private int calculatePixels(int value, int maxValue) {
        if (maxValue == 0) {
            return 0;
        }
        return (int) Math.min(MAX_PIXELS * ((double) value / maxValue), MAX_PIXELS);
    }

    /**
     * Capitalizes the first character of the given string.
     */
    @Unique
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char first = Character.toUpperCase(str.charAt(0));
        return first + str.substring(1);
    }

    /**
     * Corrects the position of the number in the bar.
     */
    @Unique
    private int correctPosition(int value) {
        int correctLen = (3 - String.valueOf(value).length()) * 6;
        return (MAX_PIXELS / 2) - (3 * 6) + correctLen;
    }

    /**
     * Draws the health bar.
     */
    @Unique
    private void drawHealthBar(DrawContext context, int health, int maxHealth, double regenHealth, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(health, maxHealth);

        // Left border
        context.drawTexture(ICONS, x, y, 0, 0, 1, 9);
        x += 1;
        // Filled part
        context.drawTexture(ICONS, x, y, 1, 0, pixels, 9);
        x += pixels;
        // Unfilled part
        context.drawTexture(ICONS, x, y, 1 + pixels, 9, MAX_PIXELS - pixels, 9);
        x += MAX_PIXELS - pixels;
        // Right border
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 0, 1, 9);

        drawHealthText(context, health, maxHealth, regenHealth, client);
    }

    /**
     * Draws the health text on the health bar.
     */
    @Unique
    private void drawHealthText(DrawContext context, int health, int maxHealth, double regenHealth, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 91 + correctPosition(health);
        int y = context.getScaledWindowHeight() - 39;
        String text = health + "/" + OUTPUT_FORMAT.format(maxHealth);
        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        // Draw regen health text if player exists
        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2 - 91;
            text = "+" + Math.round(regenHealth);
            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    /**
     * Draws the mana bar.
     */
    @Unique
    private void drawManaBar(DrawContext context, int mana, int maxMana, double regenMana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + 1;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(mana, maxMana);

        // Left border
        context.drawTexture(ICONS, x, y, 0, 18, 1, 9);
        x += 1;
        // Filled part
        context.drawTexture(ICONS, x, y, 1, 18, pixels, 9);
        x += pixels;
        // Unfilled part
        context.drawTexture(ICONS, x, y, 1 + pixels, 27, MAX_PIXELS - pixels, 9);
        x += MAX_PIXELS - pixels;
        // Right border
        context.drawTexture(ICONS, x, y, MAX_PIXELS + 1, 18, 1, 9);

        drawManaText(context, mana, maxMana, regenMana, client);
    }

    /**
     * Draws the mana text on the mana bar.
     */
    @Unique
    private void drawManaText(DrawContext context, int mana, int maxMana, double regenMana, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 + correctPosition(mana);
        int y = context.getScaledWindowHeight() - 39;
        String text = mana + "/" + OUTPUT_FORMAT.format(maxMana);
        context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);

        // Draw regen mana text if player exists
        if (client.player != null) {
            x = context.getScaledWindowWidth() / 2;
            text = "+" + Math.round(regenMana);
            context.drawTextWithShadow(client.textRenderer, text, x, y, 16777215);
        }
    }

    /**
     * Draws a key-value text pair.
     */
    @Unique
    private void drawTextPair(DrawContext context, MinecraftClient client, String key, String value, int x, int y, int diff) {
        context.drawTextWithShadow(client.textRenderer, key, x, y, 16777215);
        context.drawTextWithShadow(client.textRenderer, value, x + diff, y, 16777215);
    }

    /**
     * Draws the right side texts: level, blocked slots and attributes.
     * Note: Hero name has been moved to drawSkills.
     */
    @Unique
    private void drawTexts(DrawContext context, int level, Set<Integer> blockedSlots, AttributesComponent attributes, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 102;
        int y = context.getScaledWindowHeight() - 39;
        // Draw level
        context.drawTextWithShadow(client.textRenderer, String.valueOf(level), x, y, 16777215);

        // Draw blocked slots and attributes on the right side
        x = context.getScaledWindowWidth() / 2 + 110;
        y = context.getScaledWindowHeight() - 150;

        // Draw blocked slots
        for (Integer slot : blockedSlots) {
            context.drawTextWithShadow(client.textRenderer, "Blocked slot: " + slot, x, y, 16777215);
            y += 10;
        }

        // Draw attributes for debugging
        for (DotaAttribute type : ModRegistries.ATTRIBUTES) {
            DotaAttributeInstance attribute = attributes.getAttribute(type);
            String value = ATTRIBUTES_FORMAT.format(attribute.getBase()) + " -> " + ATTRIBUTES_FORMAT.format(attribute.get());
            drawTextPair(context, client, type.getCustomId().toLowerCase() + ":", value, x, y, 120);
            y += 10;
        }
    }

    /**
     * Draws the left side texts: hero name and skills information for debugging.
     */
    @Unique
    private void drawSkills(DrawContext context, AbstractTeam team, EnumMap<Skill.Type, Integer> skillCooldowns, int level, DotaHero hero, MinecraftClient client) {
        // Starting position on the left side of the screen
        int x = context.getScaledWindowWidth() / 2 - 320;
        int y = context.getScaledWindowHeight() - 150;

        // Draw hero name
        if (team != null) {
            context.drawTextWithShadow(client.textRenderer, (capitalize(hero.getCustomId()) + " (" + team.getName().toLowerCase() + ")"), x, y, 16777215);
            y += 10;
        }

        // Assuming hero.getSkills() returns a Map<Skill.Type, Skill>
        for (var type : Skill.Type.values()) {
            Skill skill = hero.getType().getSkill(type);
            // Display skill type, mana cost and cooldown at level 1 (for example)
            String skillInfo = "Mana: " + (int) skill.getMana(level)
                    + ", Cooldown: " + (int)(skillCooldowns.get(type) / 20F + 0.9) + "/" + skill.getCooldown(level) / 20;
            drawTextPair(context, client, "Skill " + skill.getClass().getSimpleName() + ":", skillInfo, x, y, 100);
            y += 10;
        }
    }

    /**
     * Injects custom rendering for status bars and debug information.
     */
    @Inject(method = "renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"),
            cancellable = true)
    private void onRenderStatusBars(DrawContext context, CallbackInfo ci, @Local PlayerEntity player) {
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            if (component.isHero()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V",
            at = @At(value = "HEAD"))
    private void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            if (component.isHero()) {
                AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);

                int health = (int) component.getHealth();
                int mana = (int) component.getMana();
                int maxHealth = (int) attributes.getAttribute(ModAttributes.MAX_HEALTH).get();
                int maxMana = (int) attributes.getAttribute(ModAttributes.MAX_MANA).get();
                double regenHealth = attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).get();
                double regenMana = attributes.getAttribute(ModAttributes.REGENERATION_MANA).get();
                int level = component.getLevel();
                DotaHero hero = component.getHero();
                AbstractTeam team = player.getScoreboardTeam();
                Set<Integer> blockedSlots = component.getBlocked();
                MinecraftClient client = MinecraftClient.getInstance();

                drawHealthBar(context, health, maxHealth, regenHealth, client);
                drawManaBar(context, mana, maxMana, regenMana, client);
                drawSkills(context, team, component.getSkillCooldowns(), level, hero, client);
                drawTexts(context, level, blockedSlots, attributes, client);
            }
        }
    }


}
