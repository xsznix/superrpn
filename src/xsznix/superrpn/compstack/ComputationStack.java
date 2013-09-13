package xsznix.superrpn.compstack;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

import org.apfloat.Apfloat;


import xsznix.superrpn.err.OperationException;
import xsznix.superrpn.util.Constant;

/**
 * The main computation stack for SuperRPN. It stores an array of
 * <code>ComputationStackItem</code>s in a stack that can also be accessed all
 * at once for display in the GUI. It also contains two stack-independent
 * memory registers.
 * @author Xuming Zeng
 *
 */
public class ComputationStack {
	
	/** The computation stack. */
	private ComputationStackItem[] items;
	
	/** The top/height of the stack. */
	private int stackTop;
	
	/** Memory register 1 */
	private Apfloat mem1;
	
	/** Memory register 2 */
	private Apfloat mem2;
	
	/** Implements undo/redo functionality. */
	private UndoTracker undoTracker;
	
	public ComputationStack() {
		items = new ComputationStackItem[Constant.INIT_COMPSTACK_LENGTH];
		stackTop = 0;
		mem1 = Apfloat.ZERO;
		mem2 = Apfloat.ZERO;
		undoTracker = new UndoTracker(this);
	}
	
	/**
	 * Reallocates the array containing this stack
	 * @param len the length of the new array
	 * @return <code>true</code> if the operation was successful
	 */
	private boolean reallocate(int len) {
		// do not reallocate if length is unreasonable
		if ((stackTop > len) || (len < Constant.INIT_COMPSTACK_LENGTH))
			return false;
		
		// copy the array
		ComputationStackItem[] newItems = new ComputationStackItem[len];
		System.arraycopy(items, 0, newItems, 0, stackTop);
		
		// re-reference
		items = newItems;
		
		return true;
	}
	
	/* METHODS */
	
	/**
	 * Tests if this stack is empty.
	 * @return <code>true</code> if the computation stack is empty
	 */
	public boolean empty() { return stackTop == 0; }
	
	/**
	 * Check how many items are in the stack
	 * @return the number of items in the stack
	 */
	public int height() { return stackTop; }
	
	/**
	 * Looks at the object at the top of this stack without removing it from
	 * the stack.
	 * @return the object at the top of this stack
	 */
	public ComputationStackItem peek() { return items[stackTop - 1]; }
	
	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function, tracking the undo operation.
	 * @return the object at the top of this stack
	 */
	public ComputationStackItem pop() {
		return pop(true);
	}
	
	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function
	 * @param track if <code>true</code>, this operation will be tracked in the
	 * undo history.
	 * @return the object at the top of this stack
	 */
	public ComputationStackItem pop(boolean track) {
		// get the value of the object at the top of the stack
		ComputationStackItem ret = items[stackTop - 1];
		
		// remove the object from the stack
		items[stackTop - 1] = null;
		stackTop--;
		
		// reallocate if the stack is too large
		if (stackTop * 4 < items.length)
			// length: 1.5 * stackTop
			reallocate(stackTop + stackTop / 2);
		
		// track undo
		if (track)
			undoTracker.addStackOperation(UndoTracker.POP, ret);
		
		// return the value
		return ret;
	}
	
	/**
	 * Pushes an item onto the top of this stack, tracking it in the undo
	 * history.
	 * @param it the item to be pushed onto this stack
	 */
	public void push(ComputationStackItem it) {
		push(it, true);
	}
	
	/**
	 * Pushes an item onto the top of this stack.
	 * @param it the item to be pushed onto this stack
	 * @param track if <code>true</code>, this operation will be tracked in the
	 * undo history.
	 */
	public void push(ComputationStackItem it, boolean track) {
		// reallocate if the stack is too small
		if (stackTop + stackTop / 2 > items.length)
			reallocate(stackTop + stackTop * 2 + 2);
		
		// push the object onto the stack
		items[stackTop++] = it;
		
		// track undo
		if (track)
			undoTracker.addStackOperation(UndoTracker.PUSH, it);
	}
	
	/**
	 * Pushes an item onto the top of this stack.
	 * @param val the value of the item to be pushed onto this stack
	 */
	public void push(Apfloat val) {
		push(new ComputationStackItem(val));
	}
	
	/**
	 * Pushes an item onto the top of this stack.
	 * @param val the value of the item to be pushed onto this stack
	 */
	public void push(String val) {
		push(new ComputationStackItem(val));
	}
	
	/**
	 * Gets a copy of the computation stack items in the computation stack.
	 * @return a copy of the items in this stack
	 */
	public ComputationStackItem[] getItems() {
		// make a copy of the array
		ComputationStackItem[] ret = new ComputationStackItem[stackTop];
		System.arraycopy(items, 0, ret, 0, stackTop);
		
		// return the array
		return ret;
	}
	
	// Memory register get/set
	
	public Apfloat getMem1() { return mem1; }
	public Apfloat getMem2() { return mem2; }
	
	public void setMem1(Apfloat val) {
		setMem1(val, true);
	}
	public void setMem1(Apfloat val, boolean track) {
		mem1 = val.precision(Constant.PRECISION);
		if (track)
			undoTracker.addMemregOperation(UndoTracker.MEM1, mem1);
	}
	public void setMem2(Apfloat val) {
		setMem2(val, true);
	}
	public void setMem2(Apfloat val, boolean track) {
		mem2 = val.precision(Constant.PRECISION);
		if (track)
			undoTracker.addMemregOperation(UndoTracker.MEM2, mem2);
	}
	
	// undo tracker operations
	
	/** Undoes the most recent operation on this stack. 
	 * @throws OperationException if something goes wrong */
	public void undo() throws OperationException { undoTracker.undo(); }
	
	/** Redoes the most recently undone operation on this stack. 
	 * @throws OperationException if something goes wrong */
	public void redo() throws OperationException { undoTracker.redo(); }
	
	/** Marks the end of a single complete operation. */
	public void addMarker() { undoTracker.addMarker(); }
	
	/**
	 * Tracks undo operations.
	 * @author Xuming Zeng
	 */
	private class UndoTracker {
		
		// types of operations
		public static final int PUSH = 0;
		public static final int POP = 1;
		public static final int MEM1 = 2;
		public static final int MEM2 = 3;
		
		/**
		 * The computation stack this undo tracker tracks and modifies.
		 */
		private ComputationStack stack;
		
		/** Undo history */
		private Stack<LinkedList<UndoOperation>> opHistory;
		/** Redo history */
		private Stack<LinkedList<UndoOperation>> opFuture;
		
		public UndoTracker(ComputationStack s) {
			stack = s;
			
			// init stacks
			opHistory = new Stack<LinkedList<UndoOperation>>();
			opHistory.push(new LinkedList<UndoOperation>());
			
			opFuture = new Stack<LinkedList<UndoOperation>>();
			opFuture.push(new LinkedList<UndoOperation>());
		}
		
		public void addStackOperation(int type, ComputationStackItem val) {
			StackOperation op = new StackOperation();
			op.type = type;
			op.value = val;
			
			opHistory.peek().add(op);
		}
		
		public void addMemregOperation(int type, Apfloat val) {
			MemregOperation op = new MemregOperation();
			op.type = type;
			op.value = val;
			
			opHistory.peek().add(op);
		}
		
		/**
		 * Marks the end of a single complete operation.
		 */
		public void addMarker() {
			if ((opHistory.empty()) || (opHistory.peek().size() != 0)) {
				opHistory.push(new LinkedList<UndoOperation>());
				opFuture = new Stack<LinkedList<UndoOperation>>();
				opFuture.push(new LinkedList<UndoOperation>());
			}
		}
		
		/**
		 * Undoes the most recent operation on this stack.
		 * @throws OperationException if something goes wrong
		 */
		public void undo() throws OperationException {
			// the topmost item on the stack should be empty
			if (opHistory.peek().size() > 0)
				throw new OperationException(
						"The operation was not properly marked.");
			opHistory.pop();
			
			// nothing to undo if history is empty
			if (opHistory.empty()) {
				opHistory.push(new LinkedList<UndoOperation>());
				return;
			}
			
			// revert operations
			LinkedList<UndoOperation> opList = opHistory.pop();
			ListIterator<UndoOperation> it = opList.listIterator(opList.size());
			UndoOperation op;
			while (it.hasPrevious()) {
				op = it.previous();
				
				switch (op.type) {
				case POP:
					stack.push(((StackOperation) op).value, false);
					break;
				case PUSH:
					stack.pop(false);
					break;
				case MEM1:
					Apfloat mem1Val = stack.getMem1();
					stack.setMem1(((MemregOperation) op).value, false);
					((MemregOperation) op).value = mem1Val;
					break;
				case MEM2:
					Apfloat mem2Val = stack.getMem2();
					stack.setMem2(((MemregOperation) op).value, false);
					((MemregOperation) op).value = mem2Val;
					break;
				}
			}
			
			// add to redo stack
			opFuture.pop();
			opFuture.push(opList);
			opFuture.push(new LinkedList<UndoOperation>());
			
			// mark end of operation
			opHistory.push(new LinkedList<UndoOperation>());
		}
		
		/**
		 * Redoes the most recent operation on this stack.
		 * @throws OperationException when something goes wrong
		 */
		public void redo() throws OperationException {
			// the topmost item on the stack should be empty
			if (opFuture.peek().size() > 0)
				throw new OperationException(
						"The operation was not properly marked.");
			opFuture.pop();
			
			// nothing to redo if future is empty
			if (opFuture.empty()) {
				opFuture.push(new LinkedList<UndoOperation>());
				return;
			}
			
			// revert operations
			LinkedList<UndoOperation> opList = opFuture.pop();
			ListIterator<UndoOperation> it = opList.listIterator(0);
			UndoOperation op;
			while (it.hasNext()) {
				op = it.next();
				
				switch (op.type) {
				case POP:
					stack.pop(false);
					break;
				case PUSH:
					stack.push(((StackOperation) op).value, false);
					break;
				case MEM1:
					Apfloat mem1Val = stack.getMem1();
					stack.setMem1(((MemregOperation) op).value, false);
					((MemregOperation) op).value = mem1Val;
					break;
				case MEM2:
					Apfloat mem2Val = stack.getMem2();
					stack.setMem2(((MemregOperation) op).value, false);
					((MemregOperation) op).value = mem2Val;
					break;
				}
			}
			
			// add to undo stack
			opHistory.pop();
			opHistory.push(opList);
			opHistory.push(new LinkedList<UndoOperation>());
			
			// mark end of operation
			opFuture.push(new LinkedList<UndoOperation>());
		}
		
		private class UndoOperation {
			public int type;
		}
		
		private class StackOperation extends UndoOperation {
			public ComputationStackItem value;
		}
		
		private class MemregOperation extends UndoOperation {
			public Apfloat value;
		}
		
	}

}
