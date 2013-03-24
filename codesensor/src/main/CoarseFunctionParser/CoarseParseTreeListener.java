package main.CoarseFunctionParser;

import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import main.CommonParser;
import main.codeitems.declarations.IdentifierDecl;
import main.codeitems.declarations.IdentifierDeclBuilder;
import main.codeitems.functionContent.CoarseFunctionContentBuilder;
import antlr.CoarseFunctionGrammarBaseListener;
import antlr.CoarseFunctionGrammarParser;

import antlr.CoarseFunctionGrammarParser.Init_declarator_listContext;
import antlr.CoarseFunctionGrammarParser.Type_nameContext;


public class CoarseParseTreeListener extends CoarseFunctionGrammarBaseListener
{
	CommonParser p;
	
	
	public CoarseParseTreeListener(CoarseFunctionParser aP)
	{
		p = aP;
	}
	
	// Duplication
	@Override
	public void enterCoarse_content(CoarseFunctionGrammarParser.Coarse_contentContext ctx)
	{
		CoarseFunctionContentBuilder builder = new CoarseFunctionContentBuilder();
		builder.createNew(ctx);
		p.itemStack.push(builder);
	}
	
	@Override
	public void exitCoarse_content(CoarseFunctionGrammarParser.Coarse_contentContext ctx)
	{
		CoarseFunctionContentBuilder builder = (CoarseFunctionContentBuilder) p.itemStack.pop();
		p.processor.processItem(builder.getItem(), p.itemStack);
	}

	@Override public void enterDeclByType(CoarseFunctionGrammarParser.DeclByTypeContext ctx)
	{
		Init_declarator_listContext decl_list = ctx.init_declarator_list();
		Type_nameContext typeName = ctx.type_name();
		emitDeclarations(decl_list, typeName);
	}
	
	/// Duplication end
	
			
	private void emitDeclarations(ParserRuleContext decl_list,
			  ParserRuleContext typeName)
	{
		IdentifierDeclBuilder builder = new IdentifierDeclBuilder();
		List<IdentifierDecl> declarations = builder.getDeclarations(decl_list, typeName);

		CoarseFunctionContentBuilder contentBuilder = (CoarseFunctionContentBuilder) p.itemStack.peek();
		
		Iterator<IdentifierDecl> it = declarations.iterator();
		while(it.hasNext()){
			IdentifierDecl decl = it.next();
			contentBuilder.addLocalDecl(decl);
		}		
	}
	// Duplication End
	
	
	@Override
	public void enterFuncCall(CoarseFunctionGrammarParser.FuncCallContext ctx)
	{
		
	}
	
	@Override
	public void enterFunction_argument_list(CoarseFunctionGrammarParser.Function_argument_listContext ctx)
	{
		
	}
	
	@Override
	public void enterFunction_argument(CoarseFunctionGrammarParser.Function_argumentContext ctx)
	{
		
	}

	@Override
	public void enterFieldOnly(CoarseFunctionGrammarParser.FieldOnlyContext ctx)
	{
		
	}
	
	@Override
	public void enterPrimary_expression(CoarseFunctionGrammarParser.Primary_expressionContext ctx)
	{
		
	}
	
}