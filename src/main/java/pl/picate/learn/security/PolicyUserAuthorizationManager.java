package pl.picate.learn.security;

import java.util.function.Supplier;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.ExpressionAuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.Assert;


public class PolicyUserAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

	private SecurityExpressionHandler<RequestAuthorizationContext> expressionHandler = new DefaultHttpSecurityExpressionHandler();
	private Expression expression;
	
	
	public PolicyUserAuthorizationManager(String expresionString) {
		Assert.hasText(expresionString, "Expression is null, in policy authentization.");
		this.expression = this.expressionHandler.getExpressionParser().parseExpression(expresionString);
	}
	
	@Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		Assert.notNull(expressionHandler, "expressionHandler cannot be null");
		EvaluationContext ctx = this.expressionHandler.createEvaluationContext(authentication, context);
		boolean granted = ExpressionUtils.evaluateAsBoolean(this.expression, ctx);
		return new ExpressionAuthorizationDecision(granted, this.expression);
    }
}
