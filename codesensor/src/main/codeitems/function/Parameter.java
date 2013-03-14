package main.codeitems.function;

import java.util.Stack;

import main.codeitems.CodeItem;
import main.codeitems.Name;


import org.antlr.v4.runtime.ParserRuleContext;

import antlr.CodeSensorParser.Parameter_declContext;
import antlr.CodeSensorParser.Parameter_idContext;
import antlr.CodeSensorParser.Parameter_nameContext;

public class Parameter extends CodeItem
{
	public ParameterType type;
	public Name name;
	
	public Parameter()
	{
		nodeTypeName = "PARAMETER";
	}
	
	@Override
	public void initializeFromContext(ParserRuleContext ctx)
	{
		Parameter_declContext paramCtx = (Parameter_declContext) ctx;
		Parameter_nameContext paramName = getNameOfParameter(paramCtx);
		
		type = new ParameterType();
		name = new Name();
		
		type.initializeFromContext(ctx);
		name.initializeFromContext(paramName);
		
		super.initializeFromContext(ctx);
	}
	
	private Parameter_nameContext getNameOfParameter(Parameter_declContext param_ctx)
	{
		Parameter_idContext parameter_id = param_ctx.parameter_id();
		
		while(parameter_id.parameter_name() == null){
			parameter_id = parameter_id.parameter_id();
		}
		return parameter_id.parameter_name();
	}

	
}