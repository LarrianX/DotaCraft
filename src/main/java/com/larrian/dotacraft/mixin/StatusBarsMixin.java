package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.attribute.DotaAttributeInstance;
import com.larrian.dotacraft.attribute.DotaAttribute;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.hero.Skill;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.larrian.dotacraft.ModRegistries;
import com.larrian.dotacraft.hero.SkillInstance;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Set;

import static com.larrian.dotacraft.DotaCraft.TICKS_PER_SECOND;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;

@Mixin(InGameHud.class)
public class StatusBarsMixin {
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
    private void drawHealthBar(DrawContext context, HeroComponent component, AttributesComponent attributes, MinecraftClient client) {
        int health = (int) component.getHealth();
        int maxHealth = (int) attributes.getAttribute(ModAttributes.MAX_HEALTH).get();
        double regenHealth = attributes.getAttribute(ModAttributes.REGENERATION_HEALTH).get();
        int x = context.getScaledWindowWidth() / 2 - 91;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(health, maxHealth);

        // Filled part
        context.drawTexture(ICONS, x, y, 1, 0, pixels + 1, 9);
        // Unfilled part
        context.drawTexture(ICONS, x + pixels, y, 1 + pixels, 9, MAX_PIXELS - pixels + 1, 9);

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
    private void drawManaBar(DrawContext context, HeroComponent component, AttributesComponent attributes, MinecraftClient client) {
        int mana = (int) component.getMana();
        int maxMana = (int) attributes.getAttribute(ModAttributes.MAX_MANA).get();
        double regenMana = attributes.getAttribute(ModAttributes.REGENERATION_MANA).get();
        int x = context.getScaledWindowWidth() / 2 + 1;
        int y = context.getScaledWindowHeight() - 39;
        int pixels = calculatePixels(mana, maxMana);

        // Filled part
        context.drawTexture(ICONS, x, y, 1, 18, pixels + 1, 9);
        // Unfilled part
        context.drawTexture(ICONS, x + pixels, y, 1 + pixels, 27, MAX_PIXELS - pixels + 1, 9);

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
     * Draws the right side texts: level, blocked slots, and attributes.
     */
    @Unique
    private void drawTexts(DrawContext context, HeroComponent component, AttributesComponent attributes, MinecraftClient client) {
//        int level = component.getLevel();
        Set<Integer> blockedSlots = component.getBlocked();
//        int x = context.getScaledWindowWidth() / 2 - 102;
//        int y = context.getScaledWindowHeight() - 39;
        // Draw level
//        context.drawTextWithShadow(client.textRenderer, String.valueOf(level), x, y, 16777215);

        // Draw blocked slots and attributes on the right side
        int x = context.getScaledWindowWidth() / 2 + 110;
        int y = context.getScaledWindowHeight() - 150;

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
    private void drawSkills(DrawContext context, HeroComponent component, AbstractTeam team, MinecraftClient client) {
        int x = context.getScaledWindowWidth() / 2 - 320;
        int y = context.getScaledWindowHeight() - 150;

        // Draw hero name
        if (team != null) {
            context.drawTextWithShadow(client.textRenderer, (capitalize(component.getHero().getCustomId()) + " (" + team.getName().toLowerCase() + ")"), x, y, 16777215);
            y += 10;
        }

        // Assuming hero.getSkills() returns a Map<Skill.Type, Skill>
        for (var type : Skill.Type.values()) {
            Skill skill = component.getHero().getType().getSkill(type);
            SkillInstance skillInstance = component.getSkillInstance(type);

            String skillInfo = "Active: " + (skillInstance.isActive() ? "true" : "false") + ", level: " + skillInstance.getLevel();

            int mana = (int) skill.getMana(skillInstance.getLevel());
            if (mana != 0)
                skillInfo += ", mana: " + mana;
            int cooldown = skill.getCooldowns()[skillInstance.getLevel() - 1];
            if (cooldown != 0)
                skillInfo += ", cooldown: " + (int)((double) skillInstance.getCooldown() / TICKS_PER_SECOND + 0.9) + "/" + cooldown;
            drawTextPair(context, client, "Skill " + skill.getClass().getSimpleName() + ":", skillInfo, x, y, 110);
            y += 10;
        }

        // Additional hero information
        String info = component.getHero().getAdditionalInfo();
        if (!info.isEmpty()) {
            context.drawTextWithShadow(client.textRenderer, info, x, y, 16777215);
            y += 10;
        }
    }

    @Redirect(
            method = "renderExperienceBar",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;experienceLevel:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int redirectExperienceLevel(ClientPlayerEntity player) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);
        if (component.isHero())
            return component.getLevel();
        else
            return player.experienceLevel;
    }

    @Redirect(
            method = "renderExperienceBar",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;experienceProgress:F",
                    opcode = Opcodes.GETFIELD
            )
    )
    private float redirectExperienceProgress(ClientPlayerEntity player) {
        if (player.getComponent(HERO_COMPONENT).isHero())
            return 1f;
        else
            return player.experienceProgress;
    }

    @Redirect(
            method = "renderExperienceBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getNextLevelExperience()I"
            )
    )
    private int redirectGetNextLevelExperience(ClientPlayerEntity player) {
        HeroComponent component = player.getComponent(HERO_COMPONENT);
        
        if (component.isHero())
            if (component.getLevel() >= 30) {
                return 112 + (component.getLevel() - 30) * 9;
            } else {
                return component.getLevel() >= 15 ? 37 + (component.getLevel() - 15) * 5 : 7 + component.getLevel() * 2;
            }
        else
            return player.getNextLevelExperience();
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
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            if (component.isHero()) {
                AttributesComponent attributes = player.getComponent(ATTRIBUTES_COMPONENT);
                MinecraftClient client = MinecraftClient.getInstance();

                drawHealthBar(context, component, attributes, client);
                drawManaBar(context, component, attributes, client);
                drawSkills(context, component, player.getScoreboardTeam(), client);
                drawTexts(context, component, attributes, client);
            }
        }
    }
}