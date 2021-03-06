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

package mc.feature.visitor.inheritance.delegator;

import mc.feature.visitor.inheritance.a._ast.ASTXA;
import mc.feature.visitor.inheritance.b._ast.ASTXB;
import mc.feature.visitor.inheritance.c._ast.ASTXC;
import mc.feature.visitor.inheritance.c._ast.ASTYC;
import mc.feature.visitor.inheritance.c._visitor.CVisitor;

/**
 * Simple Visitor for C that keeps track of the actual methods called using the
 * StringBuilder given in the constructor.
 *
 * @author Robert Heim
 */
public class SimpleCVisitor implements CVisitor {
  final private StringBuilder run;
  
  public SimpleCVisitor(StringBuilder run) {
    this.run = run;
  }
  
  @Override
  public void handle(ASTXA node) {
    run.append("SimpleCVisitor.hA");
    CVisitor.super.handle(node);
  }
  
  @Override
  public void traverse(ASTXA node) {
    run.append("SimpleCVisitor.tXA");
    CVisitor.super.traverse(node);
  }
  
  @Override
  public void visit(ASTXA node) {
    run.append("SimpleCVisitor.vXA");
  }
  
  @Override
  public void endVisit(ASTXA node) {
    run.append("SimpleCVisitor.eXA");
  }
  
  @Override
  public void handle(ASTXB node) {
    run.append("SimpleCVisitor.hXB");
    CVisitor.super.handle(node);
  }
  
  @Override
  public void traverse(ASTXB node) {
    run.append("SimpleCVisitor.tXB");
    CVisitor.super.traverse(node);
  }
  
  @Override
  public void visit(ASTXB node) {
    run.append("SimpleCVisitor.vXB");
  }
  
  @Override
  public void endVisit(ASTXB node) {
    run.append("SimpleCVisitor.eXB");
  }
  
  @Override
  public void handle(ASTXC node) {
    run.append("SimpleCVisitor.hXC");
    CVisitor.super.handle(node);
  }
  
  @Override
  public void traverse(ASTXC node) {
    run.append("SimpleCVisitor.tXC");
    CVisitor.super.traverse(node);
  }
  
  @Override
  public void visit(ASTXC node) {
    run.append("SimpleCVisitor.vXC");
  }
  
  @Override
  public void endVisit(ASTXC node) {
    run.append("SimpleCVisitor.eXC");
  }
  
  @Override
  public void handle(ASTYC node) {
    run.append("SimpleCVisitor.hYC");
    CVisitor.super.handle(node);
  }
  
  @Override
  public void traverse(ASTYC node) {
    run.append("SimpleCVisitor.tYC");
    CVisitor.super.traverse(node);
  }
  
  @Override
  public void visit(ASTYC node) {
    run.append("SimpleCVisitor.vYC");
  }
  
  @Override
  public void endVisit(ASTYC node) {
    run.append("SimpleCVisitor.eYC");
  }
  
  // realthis pattern
  private CVisitor realThis;
  
  @Override
  public void setRealThis(CVisitor realThis) {
    this.realThis = realThis;
  }
  
  @Override
  public CVisitor getRealThis() {
    return realThis;
  }
}
