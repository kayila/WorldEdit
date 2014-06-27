/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.util.command.parametric;

import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;

import java.lang.reflect.Method;

/**
 * A handler for the {@link CommandPermissions} annotation.
 */
public abstract class PermissionsHandler extends AbstractInvokeListener implements InvokeHandler {

    @Override
    public InvokeHandler createInvokeHandler() {
        return this;
    }

    @Override
    public void preProcess(Object object, Method method,
            ParameterData[] parameters, CommandContext context)
            throws CommandException, ParameterException {
        CommandPermissions annotation = method.getAnnotation(CommandPermissions.class);
        if (annotation != null) {
            for (String perm : annotation.value()) {
                if (hasPermission(context, perm)) {
                    return;
                }
            }
            
            throw new CommandPermissionsException();
        }
    }

    @Override
    public void preInvoke(Object object, Method method, ParameterData[] parameters, 
            Object[] args, CommandContext context) throws CommandException {
    }

    @Override
    public void postInvoke(Object object, Method method, ParameterData[] parameters, 
            Object[] args, CommandContext context) throws CommandException {
    }
    
    protected abstract boolean hasPermission(CommandContext context, String permission);

}
