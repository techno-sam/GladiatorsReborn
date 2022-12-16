package com.slimeist.gladiators_reborn;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.slimeist.gladiators_reborn.entities.RetesEntity;
import com.slimeist.gladiators_reborn.entities.ThrownRetesEntity;
import com.slimeist.gladiators_reborn.entities.TridensEntity;
import com.slimeist.gladiators_reborn.gladiator_types.GladiatorType;
import com.slimeist.gladiators_reborn.items.*;
import com.slimeist.gladiators_reborn.mixin_interfaces.PlayerGladiatorData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static com.slimeist.gladiators_reborn.util.Registration.*;

//Command imports
// getString(ctx, "string")
// word()
// literal("foo")
import static net.minecraft.server.command.CommandManager.literal;
// argument("bar", word())
import static net.minecraft.server.command.CommandManager.argument;
// Import everything in the CommandManager


public class GladiatorsReborn implements ModInitializer {

    public static final String MOD_ID = "gladiators_reborn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item SCUTUM_SHIELD = new ScutumShieldItem(
        new FabricItemSettings().maxDamage(300).group(ItemGroup.COMBAT),
        40, 5, Items.LEATHER);

    public static final ConvenientDyeableArmorItem GALEA_HELMET = new ConvenientDyeableArmorItem(CustomArmorMaterials.SECUTOR_STUFF, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final ConvenientDyeableArmorItem MANICA_CHESTPLATE = new ConvenientDyeableArmorItem(CustomArmorMaterials.SECUTOR_STUFF, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final ConvenientDyeableArmorItem MANICA_WITH_TUNIC = new ConvenientDyeableArmorItem(CustomArmorMaterials.SECUTOR_STUFF, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final ConvenientDyeableArmorItem GREAVES_LEGGINGS = new ConvenientDyeableArmorItem(CustomArmorMaterials.SECUTOR_STUFF, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT));
    public static final SwordItem PUGIO = new SwordItem(ToolMaterials.IRON, 1, -0.4f, new FabricItemSettings().group(ItemGroup.COMBAT)) {
        private static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("42E2E65B-9CBF-400D-8139-8AA2C739F6D6");

        @Override
        public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
            Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getAttributeModifiers(slot);
            if (slot == EquipmentSlot.MAINHAND) {
                ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
                builder.putAll(modifiers);
                builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Attack Range", -1.5D, EntityAttributeModifier.Operation.ADDITION));
                modifiers = builder.build();
            }
            return modifiers;
        }
    };

    public static final TridensItem TRIDENS = new TridensItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxDamage(100).rarity(Rarity.UNCOMMON));
    public static final EntityType<TridensEntity> TRIDENS_ENTITY_TYPE = FabricEntityTypeBuilder.<TridensEntity>create(SpawnGroup.MISC, TridensEntity::new)
        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
        .trackRangeBlocks(4)
        .trackedUpdateRate(10)
        .build();

    public static final RetesItem RETES = new RetesItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxDamage(75).rarity(Rarity.UNCOMMON));
    public static final EntityType<ThrownRetesEntity> THROWN_RETES_ENTITY_TYPE = FabricEntityTypeBuilder.<ThrownRetesEntity>create(SpawnGroup.MISC, ThrownRetesEntity::new)
        .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
        .trackRangeBlocks(4)
        .trackedUpdateRate(10)
        .build();
    public static final EntityType<RetesEntity> RETES_ENTITY_TYPE = FabricEntityTypeBuilder.<RetesEntity>createLiving()
        .entityFactory(RetesEntity::new)
        .dimensions(EntityDimensions.fixed(0.3f, 1.8f))
        .trackRangeBlocks(32)
        .defaultAttributes(RetesEntity::createRetesAttributes)
        .build();


    public static final GladiatorType SAMNITE = new GladiatorType(id("samnite"), "Samnite")
        .addLoadoutEntry(EquipmentSlot.HEAD, () -> GALEA_HELMET.makeStack(Items.LIME_DYE))
        .addLoadoutEntry(EquipmentSlot.CHEST, () -> MANICA_CHESTPLATE.makeStack(Items.LIME_DYE, Items.BLUE_DYE))
        .addLoadoutEntry(EquipmentSlot.LEGS, () -> GREAVES_LEGGINGS.makeStack(Items.LIME_DYE, Items.BLUE_DYE, Items.YELLOW_DYE))
        .addLoadoutEntry(EquipmentSlot.OFFHAND, SCUTUM_SHIELD)
        .addLoadoutEntry(EquipmentSlot.MAINHAND, () -> {
            ItemStack stack = new ItemStack(Items.IRON_SWORD);
            stack.setCustomName(Text.literal("Gladius").setStyle(Style.EMPTY.withItalic(false)));
            stack.addEnchantment(Enchantments.SHARPNESS, 1);
            stack.addHideFlag(ItemStack.TooltipSection.ENCHANTMENTS);
            return stack;
        });

    public static final GladiatorType RETIARIUS = new GladiatorType(id("retiarius"), "Retiarius")
        .addLoadoutEntry(EquipmentSlot.CHEST, () -> MANICA_WITH_TUNIC.makeStack(Items.RED_DYE, Items.BLUE_DYE))
        .addLoadoutEntry(EquipmentSlot.OFFHAND, RETES)
        .addLoadoutEntry(EquipmentSlot.MAINHAND, TRIDENS)
        .addLoadoutEntry(1, PUGIO);


    @Override
    public void onInitialize() {
        registerItem("scutum_shield", SCUTUM_SHIELD);
        registerItem("galea_helmet", GALEA_HELMET);
        registerItem("manica_chestplate", MANICA_CHESTPLATE);
        registerItem("manica_with_tunic", MANICA_WITH_TUNIC);
        registerItem("greaves_leggings", GREAVES_LEGGINGS);
        registerItem("pugio", PUGIO);
        registerItem("tridens", TRIDENS);
        registerItem("retes", RETES);
        registerEntityType("tridens", TRIDENS_ENTITY_TYPE);
        registerEntityType("retes", RETES_ENTITY_TYPE);
        registerEntityType("thrown_retes", THROWN_RETES_ENTITY_TYPE);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("gl_equip")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("targets", EntityArgumentType.players())
                .then(argument("gladiator_type", IdentifierArgumentType.identifier())
                    .suggests(GladiatorType.ALL_TYPES)
                    .executes(context -> {
                        Identifier typeId = IdentifierArgumentType.getIdentifier(context, "gladiator_type");
                        Optional<GladiatorType> gladiatorType = GladiatorType.get(typeId);
                        if (gladiatorType.isPresent()) {
                            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "targets");
                            if (players.size() == 0) {
                                context.getSource().sendError(Text.of("No players found"));
                                return 0;
                            }
                            for (ServerPlayerEntity serverPlayer : players) {
                                ((PlayerGladiatorData) serverPlayer).setGladiatorType(gladiatorType.get());
                            }
                            context.getSource().sendFeedback(Text.of("Equipped " + players.size() + " players with " + gladiatorType.get().getId()), true);
                            return players.size();
                        } else {
                            context.getSource().sendError(Text.of("Unknown gladiator type: " + typeId));
                            return 0;
                        }
                    })))));
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
