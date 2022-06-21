/*
 * This file is part of Flow Network, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Flow Powered
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

import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A {@code AbstractProtocol} stores to what port the protocol should be bound to.
 */
public abstract class AbstractProtocol implements Protocol {
    private final String name;
    private final Logger logger;

    public AbstractProtocol(String name) {
        this(name, LoggerFactory.getLogger("Protocol." + name));
    }

    /**
     * @param name The name of the protocol
     * @param logger The logger to log output to
     */
    public AbstractProtocol(String name, Logger logger) {
        this.name = name;
        this.logger = logger;
    }

    /**
     * Gets the name of the AbstractProtocol
     *
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the logger for this protocol.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Allows applying a wrapper to messages with dynamically allocated id's, in case this protocol needs to provide special treatment for them.
     *
     * @param dynamicMessage The message with a dynamically-allocated codec
     * @param <T> The type of the message
     * @return The new message
     * @throws IOException if the message cannot be wrapped
     */
    public <T extends Message> Message getWrappedMessage(T dynamicMessage) throws IOException {
        return dynamicMessage;
    }

    public abstract <M extends Message> MessageHandler<?, M> getMessageHandle(Class<M> message);
}