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

package de.monticore.symboltable.mocks.languages.scandentity;

import static com.google.common.base.Preconditions.checkArgument;

import de.monticore.symboltable.Symbol;
import de.monticore.symboltable.mocks.languages.entity.ActionSymbol;
import de.monticore.symboltable.mocks.languages.entity.ActionSymbolKind;
import de.monticore.symboltable.mocks.languages.statechart.StateChartKind;
import de.monticore.symboltable.mocks.languages.statechart.StateChartSymbol;
import de.monticore.symboltable.resolving.TransitiveAdaptedResolvingFilter;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @version $Revision$,
 *          $Date$
 *
 */
public class Sc2ActionTransitiveResolvingFilter extends TransitiveAdaptedResolvingFilter<ActionSymbol> {

  /**
   * Constructor for de.monticore.symboltable.mocks.languages.scandentity.Sc2ActionResolver
   */
  public Sc2ActionTransitiveResolvingFilter() {
    super(StateChartKind.KIND, ActionSymbol.class, ActionSymbolKind.KIND);
  }
  

  /**
   * @see de.monticore.symboltable.resolving.CommonAdaptedResolvingFilter#createAdapter(de.monticore.symboltable.Symbol)
   */
  @Override
  protected ActionSymbol createAdapter(Symbol s) {
    checkArgument(s instanceof StateChartSymbol);

    return new Sc2ActionAdapter((StateChartSymbol) s);
  }
}
