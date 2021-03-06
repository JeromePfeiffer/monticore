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

package de.monticore.codegen.mc2cd.transl;

import java.util.function.UnaryOperator;

import com.google.common.base.Preconditions;

import de.monticore.codegen.mc2cd.TransformationHelper;
import de.monticore.grammar.grammar._ast.ASTAbstractProd;
import de.monticore.grammar.grammar._ast.ASTClassProd;
import de.monticore.grammar.grammar._ast.ASTGenericType;
import de.monticore.grammar.grammar._ast.ASTMCGrammar;
import de.monticore.grammar.grammar._ast.ASTRuleReference;
import de.monticore.languages.grammar.MCGrammarSymbol;
import de.monticore.languages.grammar.MCRuleSymbol;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDClass;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDCompilationUnit;
import de.monticore.utils.Link;

/**
 * Checks if the source rules were implementing interface rules and sets the
 * implemented interfaces of the target nodes accordingly.
 * 
 * @author Sebastian Oberhoff
 */
public class ImplementsTranslation implements
    UnaryOperator<Link<ASTMCGrammar, ASTCDCompilationUnit>> {
  
  @Override
  public Link<ASTMCGrammar, ASTCDCompilationUnit> apply(
      Link<ASTMCGrammar, ASTCDCompilationUnit> rootLink) {
    
    for (Link<ASTClassProd, ASTCDClass> link : rootLink.getLinks(
        ASTClassProd.class,
        ASTCDClass.class)) {
      translateClassProd(link.source(), link.target(), rootLink.source());
    }
    
    for (Link<ASTAbstractProd, ASTCDClass> link : rootLink.getLinks(
        ASTAbstractProd.class,
        ASTCDClass.class)) {
      translateAbstractProd(link.source(), link.target(), rootLink.source());
    }
    
    return rootLink;
  }
  
  private void translateClassProd(ASTClassProd classProd,
      ASTCDClass cdClass, ASTMCGrammar astGrammar) {
    // translates "implements"
    for (ASTRuleReference ruleReference : classProd.getSuperInterfaceRule()) {
      MCGrammarSymbol grammarSymbol = (MCGrammarSymbol) astGrammar.getSymbol().get();
      MCRuleSymbol ruleSymbol = grammarSymbol.getRuleWithInherited(ruleReference.getName());
      Preconditions.checkState(ruleSymbol != null
          && ruleSymbol.getGrammarSymbol() != null);
      cdClass.getInterfaces().add(
          TransformationHelper.createSimpleReference(TransformationHelper
              .getAstPackageName(
              ruleSymbol.getGrammarSymbol())
              + "AST"
              + ruleReference.getName()));
    }
    
    // translates "astimplements"
    String qualifiedRuleName;
    for (ASTGenericType typeReference : classProd.getASTSuperInterface()) {
      qualifiedRuleName = TransformationHelper
          .getQualifiedTypeNameAndMarkIfExternal(
              typeReference, astGrammar, cdClass);
      cdClass.getInterfaces().add(
          TransformationHelper.createSimpleReference(qualifiedRuleName));
    }
  }
  
  private void translateAbstractProd(ASTAbstractProd abstractProd,
      ASTCDClass cdClass, ASTMCGrammar astGrammar) {
    // translates "implements"
    for (ASTRuleReference ruleReference : abstractProd
        .getSuperInterfaceRule()) {
      MCGrammarSymbol grammarSymbol = (MCGrammarSymbol) astGrammar.getSymbol().get();
      MCRuleSymbol ruleSymbol = grammarSymbol.getRuleWithInherited(ruleReference.getName());
      Preconditions.checkState(ruleSymbol != null
          && ruleSymbol.getGrammarSymbol() != null);
      cdClass.getInterfaces().add(
          TransformationHelper.createSimpleReference(TransformationHelper
              .getAstPackageName(
              ruleSymbol.getGrammarSymbol())
              + "AST"
              + ruleReference.getName()));
    }
    
    // translates "astimplements"
    String qualifiedRuleName;
    for (ASTGenericType typeReference : abstractProd.getASTSuperInterface()) {
      qualifiedRuleName = TransformationHelper
          .getQualifiedTypeNameAndMarkIfExternal(
              typeReference, astGrammar, cdClass);
      cdClass.getInterfaces().add(
          TransformationHelper.createSimpleReference(qualifiedRuleName));
    }
  }
  
}
