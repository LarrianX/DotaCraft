package com.larrian.dotacraft.event.network;

import com.larrian.dotacraft.hero.Skill;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;

public class SkillPacket {
    private final Skill.Type type;

    public SkillPacket(Skill.Type type) {
        this.type = type;
    }

    public SkillPacket(PacketByteBuf buf) {
        this.type = Skill.Type.values()[buf.readByte()];
    }

    public PacketByteBuf toPacketByteBuf() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeByte(type.ordinal());
        return buf;
    }

    public Skill.Type getType() {
        return type;
    }
}