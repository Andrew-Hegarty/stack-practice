import java.util.Stack;
import java.util.StringTokenizer;

public class StackLibrary5 {

	public static boolean isOperator(String oper) {
		//return true if oper is ^, *, /, +, -
		//else return false
		
		if (oper.equals("^") || oper.equals("*") || oper.equals("/") || oper.equals("+") || oper.equals("-")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static String evaluateBinaryExpression(String op1, String operator, String op2) {
		//Convert op1, op2 to a number numOp1, numOp2
		//Extended if else for operators ^, *, /, +, - with numOp1 & numOp2
		
		int numOp1 = Integer.parseInt(op1);
		int numOp2 = Integer.parseInt(op2);
		int answer = 0;
		String ans = "";
		
		if (operator.equals("^")) {
			answer = (int) Math.pow(numOp1, numOp2);
		} else if (operator.equals("*")) {
			answer = numOp1 * numOp2;
		} else if (operator.equals("/")) {
			answer = numOp1 / numOp2;
		} else if (operator.equals("+")) {
			answer = numOp1 + numOp2;
		} else {
			answer = numOp1 - numOp2;
		}
		
		ans = Integer.toString(answer);
		return ans;
		
	}
	
	public static int evaluatePostFixExpression(String pf) {
		//Stack
		//StringTokenizer for the postfix expression
		//Loop until no more tokens 
		Stack<String> stack = new Stack<String>();
		StringTokenizer tokenizer = new StringTokenizer(pf);
		String token = "";
		String op2;
		String op1;
		String evaluated = "";
		
		while (tokenizer.hasMoreTokens()) {
			
			token = tokenizer.nextToken();
			
			if (isOperator(token)) {
				
				op2 = stack.pop();
				op1 = stack.pop();
				
				evaluated = evaluateBinaryExpression(op1, token, op2);
				
				stack.push(evaluated);
				
			} else {
				stack.push(token);
			}
			
		}
		
		String answer = stack.pop();
		int ans = Integer.parseInt(answer);
		return ans;
		
	}
	
	public static int getPriority(String token) {
		
		if (token.equals("^")) {
			return 2;
		} else if (token.equals("*") || token.equals("/")) {
			return 1;
		} else {
			return 0;
		}
		
	}
	
	public static boolean isLeftParenthesis(String token) {
		
		if (token.equals("(")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static boolean isRightParenthesis(String token) {
		
		if (token.equals(")")) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static String infixToPostfix(String infix) {
		
		String postfix = "";
		StringTokenizer token = new StringTokenizer(infix);
		Stack<String> stack = new Stack<String>();
		String tokenInfix = "";
		String tokenStack = "";
		
		while (token.hasMoreTokens()) {
			
			tokenInfix = token.nextToken();
			
			if (isOperator(tokenInfix)) {
				
				if (stack.isEmpty() || tokenStack == "(" /*isLeftParenthesis(tokenInfix)*/) {
					stack.push(tokenInfix);
				} else {
					while (!stack.isEmpty() && isOperator(stack.peek()) && getPriority(stack.peek()) >= getPriority(tokenInfix)) {
						tokenStack = stack.pop();
						postfix += tokenStack + " ";
					}
					stack.push(tokenInfix);
				}
				
			} else if (isLeftParenthesis(tokenInfix)) {
				stack.push(tokenInfix);
			} else if (isRightParenthesis(tokenInfix)) {
				while (!isLeftParenthesis(stack.peek())) {
					postfix += stack.pop() + " ";
				}
				stack.pop();
			} else {
				postfix += tokenInfix + " ";
			}
		}
		
		while (!stack.isEmpty()) {
			postfix += stack.pop();
		}
		
		return postfix;
		
	}
 	
	public static void main(String[] args) {
		
		//Instance Variables
		String postFix = "";
		String inFix = "";
		int answer = 0;
		
		System.out.println("\n*** The first part is just evaluating the postfix worth 10 points ***");
		postFix = "8 4 + 10 6 - /";
		answer = evaluatePostFixExpression(postFix);
		System.out.println("The postFix expression is ");
		System.out.println(postFix);
		System.out.println("The answer is " + answer + " it is worth 10 points");
		
		System.out.println("\n*** This part is worth 4 points for a total of 14 ***");
		inFix = "( 32 + 16 / 4 * 2 )";
		postFix = infixToPostfix(inFix);
		System.out.println(inFix);
		System.out.println(postFix);
		answer = evaluatePostFixExpression(postFix);
		System.out.println("The answer is " + answer);
		
		System.out.println("\n*** This part is worth 2 points for a total of 16 ***");
		inFix = "8 + 4 * 2 ^ 3 - 9";
		postFix = infixToPostfix(inFix);
		System.out.println(inFix);
		System.out.println(postFix);
		answer = evaluatePostFixExpression(postFix);
		System.out.println("The answer is " + answer);
		
		System.out.println("\n*** This part is worth 4 points for a total of 20 ***");
		inFix = "( 8 + 4 * 2 ^ 3 - 5 ) / ( 3 - 2 * 5 + 3 ^ 2 + 4 )";
		postFix = infixToPostfix(inFix);
		System.out.println(inFix);
		System.out.println(postFix);
		answer = evaluatePostFixExpression(postFix);
		System.out.println("The answer is " + answer);
		
		System.out.println("End main");
		
	}
	
}
/*
*******************My output*******************
	
*** The first part is just evaluating the postfix worth 10 points ***
The postFix expression is 
8 4 + 10 6 - /
The answer is 3 it is worth 10 points

*** This part is worth 4 points for a total of 14 ***
( 32 + 16 / 4 * 2 )
32 16 4 / 2 * + 
The answer is 40

*** This part is worth 2 points for a total of 16 ***
8 + 4 * 2 ^ 3 - 9
8 4 2 3 ^ * + 9 -
The answer is 31

*** This part is worth 4 points for a total of 20 ***
( 8 + 4 * 2 ^ 3 - 5 ) / ( 3 - 2 * 5 + 3 ^ 2 + 4 )
8 4 2 3 ^ * + 5 - 3 2 5 * - 3 2 ^ + 4 + /
The answer is 5
End main
*/
