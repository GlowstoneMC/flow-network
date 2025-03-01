/*
 * This file is part of Flow Network, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013-2022 Glowstone <https://glowstone.net/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.flowpowered.network.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.exception.UnknownPacketException;
import com.flowpowered.network.protocol.Protocol;

/**
 * A {@link ReplayingDecoder} which decodes {@link ByteBuf}s into {@link Message}s.
 */
public class MessageDecoder extends ReplayingDecoder<ByteBuf> {
    private final MessageHandler messageHandler;

    public MessageDecoder(final MessageHandler handler) {
        this.messageHandler = handler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        Protocol protocol = messageHandler.getSession().getProtocol();
        Codec<?> codec;
        try {
            codec = protocol.readHeader(buf);
        } catch (UnknownPacketException e) {
            // We want to catch this and read the length if possible
            int length = e.getLength();
            if (length != -1 && length != 0) {
                buf.readBytes(length);
            }
            throw e;
        }

        if (codec == null) {
            throw new UnsupportedOperationException("Protocol#readHeader cannot return null!");
        }
        Message decoded = codec.decode(buf);
        out.add(decoded);
    }
}