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
package com.flowpowered.network.protocol;

import com.flowpowered.network.Codec;
import com.flowpowered.network.Codec.CodecRegistration;
import com.flowpowered.network.Message;
import com.flowpowered.network.exception.UnknownPacketException;

import io.netty.buffer.ByteBuf;

public interface Protocol {

    /**
     * @return the name of this Protocol
     */
    String getName();

    /**
     * Read a packet header from the buffer. If a codec is not known, throw a {@link UnknownPacketException}
     *
     * @param buf The buffer to read from
     * @return The correct codec; null will throw an UnsupportedOperationException
     * @throws UnknownPacketException when the opcode does not have an associated codec
     */
    Codec<?> readHeader(ByteBuf buf) throws UnknownPacketException;

    /**
     * Gets the {@link Codec} associated with encoding this {@link Message}.
     *
     * @param <M> the message type
     * @param message the message
     * @return the codec to encode with
     */
    <M extends Message> CodecRegistration getCodecRegistration(Class<M> message);

    /**
     * Writes a packet header to a new buffer.
     *
     * @param header the buffer which to write the header to
     * @param codec The codec the message was written with
     * @param data The data from the encoded message
     * @return the header ByteBuf to use 
     */
    ByteBuf writeHeader(ByteBuf header, CodecRegistration codec, ByteBuf data);
}