package com.slimeist.gladiators_reborn.gladiator_types;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.slimeist.gladiators_reborn.GladiatorsReborn;
import com.slimeist.gladiators_reborn.mixin_interfaces.PlayerGladiatorData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Supplier;

public class GladiatorType {
    public static final SuggestionProvider<ServerCommandSource> ALL_TYPES = SuggestionProviders.register(
        GladiatorsReborn.id("all_gl_types"),
        (context, builder) -> CommandSource.suggestIdentifiers(getAllIds(), builder));
    private static final Map<Identifier, GladiatorType> ALL = new HashMap<>();

    public static Optional<GladiatorType> get(Identifier id) {
        return Optional.ofNullable(ALL.get(id));
    }

    public static List<Identifier> getAllIds() {
        return new ArrayList<>(ALL.keySet());
    }

    public static boolean matchesAny(PlayerEntity player, GladiatorType... type) {
        for (GladiatorType testType : type) {
            if (((PlayerGladiatorData) player).getGladiatorType() == testType)
                return true;
        }
        return false;
    }

    public static final GladiatorType NONE = new GladiatorType(GladiatorsReborn.id("none"), "None");

    private final List<LoadoutEntry> loadout = new ArrayList<>();
    private final Identifier id;
    public final String displayName;

    public GladiatorType(Identifier id, String displayName) {
        ALL.put(id, this);
        this.id = id;
        this.displayName = displayName;
    }

    public Identifier getId() {
        return id;
    }

    public GladiatorType addLoadoutEntry(int slot, ItemStack stack) {
        loadout.add(new LoadoutEntry(slot, () -> stack));
        return this;
    }

    public GladiatorType addLoadoutEntry(EquipmentSlot slot, ItemStack stack) {
        loadout.add(new LoadoutEntry(slot, () -> stack));
        return this;
    }

    public GladiatorType addLoadoutEntry(int slot, Supplier<ItemStack> stack) {
        loadout.add(new LoadoutEntry(slot, stack));
        return this;
    }

    public GladiatorType addLoadoutEntry(EquipmentSlot slot, Supplier<ItemStack> stack) {
        loadout.add(new LoadoutEntry(slot, stack));
        return this;
    }

    public GladiatorType addLoadoutEntry(int slot, Item item) {
        loadout.add(new LoadoutEntry(slot, () -> new ItemStack(item)));
        return this;
    }

    public GladiatorType addLoadoutEntry(EquipmentSlot slot, Item item) {
        loadout.add(new LoadoutEntry(slot, () -> new ItemStack(item)));
        return this;
    }

    public void applyLoadout(PlayerEntity player) {
        for (LoadoutEntry entry : loadout) {
            entry.apply(player);
        }
    }

    private final static class LoadoutEntry {
        private final Object slot;
        private final Supplier<ItemStack> itemStack;

        public LoadoutEntry(EquipmentSlot slot, Supplier<ItemStack> itemStack) {
            this.slot = slot;
            this.itemStack = itemStack;
        }

        public LoadoutEntry(int slot, Supplier<ItemStack> itemStack) {
            this.slot = slot;
            this.itemStack = itemStack;
        }

        public ItemStack getItemStack() {
            return itemStack.get().copy();
        }

        public void apply(PlayerEntity player) {
            if (slot instanceof EquipmentSlot equipmentSlot) {
                player.equipStack(equipmentSlot, getItemStack());
            } else if (slot instanceof Integer intSlot) {
                player.getInventory().setStack(intSlot, getItemStack());
            }
        }
    }
}
