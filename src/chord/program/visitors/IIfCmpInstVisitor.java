package chord.program.visitors;

import joeq.Compiler.Quad.Quad;

public interface IIfCmpInstVisitor extends IMethodVisitor {
	/**
	 * Visits all conditional branch statements in all methods
	 * in the program.
	 * 
	 * @param	q	A conditional branch statement.
	 */
	public void visitIfCmpInst(Quad q);

}
