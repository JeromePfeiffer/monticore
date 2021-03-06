/*
 * ******************************************************************************
 * MontiCore Language Workbench
 * Copyright (c) 2015, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

/**
 * 
 */
package de.monticore.ast;

/**
 * interface for all lists, in addition to ASTNode it allows for the distinction
 * between the state "empty" and "null"
 * 
 * @author groen
 * 
 *  
 * @deprecated Remove this class after MC-Release 4.2.1. It is no longer necessary.
 */
@Deprecated
public interface ASTList extends ASTNode {
  
  boolean is_Existent();
  
  void set_Existent(boolean existent);
}
