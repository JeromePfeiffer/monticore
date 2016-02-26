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

import de.monticore.ast.ASTNode;
import de.monticore.codegen.mc2cd.MC2CDStereotypes;
import de.monticore.codegen.mc2cd.MCGrammarSymbolTableHelper;
import de.monticore.codegen.mc2cd.TransformationHelper;
import de.monticore.grammar.grammar._ast.*;
import de.monticore.languages.grammar.MCClassRuleSymbol;
import de.monticore.languages.grammar.MCGrammarSymbol;
import de.monticore.languages.grammar.MCRuleSymbol;
import de.monticore.languages.grammar.MCTypeSymbol;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDAttribute;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDClass;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDCompilationUnit;
import de.monticore.umlcd4a.cd4analysis._ast.CD4AnalysisNodeFactory;
import de.monticore.utils.ASTNodes;
import de.monticore.utils.Link;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class InheritedAttributesTranslation implements
    UnaryOperator<Link<ASTMCGrammar, ASTCDCompilationUnit>> {

  @Override
  public Link<ASTMCGrammar, ASTCDCompilationUnit> apply(
      Link<ASTMCGrammar, ASTCDCompilationUnit> rootLink) {

    for (Link<ASTClassProd, ASTCDClass> link : rootLink.getLinks(ASTClassProd.class,
        ASTCDClass.class)) {
      handleInheritedNonTerminals(link);
      handleInheritedAttributeInASTs(link);
    }
    return rootLink;
  }

  private void handleInheritedNonTerminals(Link<ASTClassProd, ASTCDClass> link) {
    for (Entry<String, List<ASTNonTerminal>> entry : getInheritedNonTerminals(link.source())
        .entrySet()) {

      String superGrammarName = MCGrammarSymbolTableHelper.resolveRule(
          link.rootLink().source(), entry.getKey()).get()
          .getGrammarSymbol()
          .getFullName();

      for (ASTNonTerminal nonTerminal : entry.getValue()) {
        ASTCDAttribute cdAttribute = createStereoTypedCDAttribute(
            MC2CDStereotypes.INHERITED.toString(), superGrammarName);
        link.target().getCDAttributes().add(cdAttribute);
        new Link<>(nonTerminal, cdAttribute, link);
      }
    }
  }

  private void handleInheritedAttributeInASTs(Link<ASTClassProd, ASTCDClass> link) {
    List<ASTAttributeInAST> inheritedAttributeInASTs = getAllSuperProds(link.source()).stream()
        .flatMap(astProd -> astProd.getSymbol()
            .filter(MCClassRuleSymbol.class::isInstance)
            .map(MCClassRuleSymbol.class::cast)
            .map(MCClassRuleSymbol::getType)
            .map(MCTypeSymbol::getAttributeInASTs)
            .orElse(Collections.emptyList())
            .stream())
        .collect(Collectors.toList());

    for (ASTAttributeInAST attributeInAST : inheritedAttributeInASTs) {
      String superGrammarName = MCGrammarSymbolTableHelper.getMCGrammarSymbol(attributeInAST)
          .map(MCGrammarSymbol::getFullName)
          .orElse("");

      ASTCDAttribute cdAttribute = createStereoTypedCDAttribute(
          MC2CDStereotypes.INHERITED.toString(), superGrammarName);

      link.target().getCDAttributes().add(cdAttribute);
      new Link<>(attributeInAST, cdAttribute, link);
    }
  }

  private ASTCDAttribute createStereoTypedCDAttribute(String stereotypeName,
      String stereotypeValue) {
    ASTCDAttribute cdAttribute = CD4AnalysisNodeFactory.createASTCDAttribute();
    TransformationHelper.addStereoType(cdAttribute, stereotypeName, stereotypeValue);
    return cdAttribute;
  }

  private Map<String, List<ASTNonTerminal>> getInheritedNonTerminals(ASTProd sourceNode) {
    return getAllSuperProds(sourceNode).stream()
        .collect(Collectors.toMap(ASTProd::getName, astProd ->
            ASTNodes.getSuccessors(astProd, ASTNonTerminal.class)));
  }

  private List<ASTProd> getAllSuperProds(ASTNode astNode) {
    List<ASTProd> directSuperRules = getDirectSuperProds(astNode);
    List<ASTProd> allSuperRules = new ArrayList<>();
    for (ASTProd superRule : directSuperRules) {
      allSuperRules.addAll(getAllSuperProds(superRule));
    }
    allSuperRules.addAll(directSuperRules);
    return allSuperRules;
  }
  
  private List<ASTProd> getDirectSuperProds(ASTNode astNode) {
    if (astNode instanceof ASTClassProd) {
      return resolveRuleReferences(((ASTClassProd) astNode).getSuperRule(), astNode);
    }
    return Collections.emptyList();
  }
  
  private List<ASTProd> resolveRuleReferences(List<ASTRuleReference> ruleReferences,
      ASTNode nodeWithSymbol) {
    List<ASTProd> superRuleNodes = new ArrayList<>();
    for (ASTRuleReference superRule : ruleReferences) {
      Optional<MCRuleSymbol> symbol = MCGrammarSymbolTableHelper.resolveRule(nodeWithSymbol,
          superRule.getName());
      if (symbol.isPresent() && symbol.get().getAstNode().isPresent()) {
        superRuleNodes.add((ASTProd) symbol.get().getAstNode().get());
      }
    }
    return superRuleNodes;
  }
}
