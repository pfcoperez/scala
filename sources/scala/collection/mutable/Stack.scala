/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003, LAMP/EPFL                  **
**  __\ \/ /__/ __ |/ /__/ __ |                                         **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
** $Id$
\*                                                                      */

package scala.collection.mutable;


/** A stack implements a data structure which allows to store and retrieve
 *  objects in a last-in-first-out (LIFO) fashion.
 *
 *  @author  Matthias Zenger
 *  @version 1.1, 03/05/2004
 */
[serializable]
class Stack[A] extends MutableList[A] with Cloneable {

    /** Checks if the stack is empty.
     *
     *  @return true, iff there is no element on the stack
     */
    def isEmpty: Boolean = (first == null);

    /** Pushes a single element on top of the stack.
     *
     *  @param  elem        the element to push onto the stack
     */
    def +=(elem: A): Unit = prependElem(elem);

    /** Pushes all elements provided by an <code>Iterable</code> object
     *  on top of the stack. The elements are pushed in the order they
     *  are given out by the iterator.
     *
     *  @param  iter        an iterable object
     */
    def ++=(iter: Iterable[A]): Unit = this ++= iter.elements;

    /** Pushes all elements provided by an iterator
     *  on top of the stack. The elements are pushed in the order they
     *  are given out by the iterator.
     *
     *  @param  iter        an iterator
     */
    def ++=(it: Iterator[A]): Unit = it foreach { e => prependElem(e) };

    /** Pushes a sequence of elements on top of the stack. The first element
     *  is pushed first, etc.
     *
     *  @param  elems       a sequence of elements
     */
    def push(elems: A*): Unit = (this ++= elems);

    /** Returns the top element of the stack. This method will not remove
     *  the element from the stack. An error is signaled if there is no
     *  element on the stack.
     *
     *  @return the top element
     */
    def top: A = if (first == null) error("stack empty"); else first.elem;

    /** Removes the top element from the stack.
     */
    def pop: A =
        if (first != null) {
            val res = first.elem;
            first = first.next;
            res
        } else
            error("stack empty");

    /**
     * Removes all elements from the stack. After this operation completed,
     * the stack will be empty.
     */
    def clear: Unit = reset;

    /** Returns an iterator over all elements on the stack. This iterator
     *  is stable with respect to state changes in the stack object; i.e.
     *  such changes will not be reflected in the iterator. The iterator
     *  issues elements in the order they were inserted into the stack
     *  (FIFO order).
     *
     *  @return an iterator over all stack elements.
     */
    override def elements: Iterator[A] = toList.elements;

    /** Creates a list of all stack elements in FIFO order.
     *
     *  @return the created list.
     */
    override def toList: List[A] = super[MutableList].toList.reverse;

    /** Checks if two stacks are structurally identical.
     *
     *  @return true, iff both stacks contain the same sequence of elements.
     */
    override def equals(that: Any): Boolean =
        that.isInstanceOf[Stack[A]] &&
        { val other = that.asInstanceOf[Stack[A]];
          elements.zip(other.elements).forall {
            case Pair(thiselem, thatelem) => thiselem == thatelem;
        }};

    /** The hashCode method always yields an error, since it is not
     *  safe to use mutable stacks as keys in hash tables.
     *
     *  @return never.
     */
    override def hashCode(): Int = error("unsuitable as hash key");

    /** Returns a textual representation of a stack as a string.
     *
     *  @return the string representation of this stack.
     */
    override def toString(): String = toList.mkString("Stack(", ", ", ")");

    /** This method clones the stack.
     *
     *  @return  a stack with the same elements.
     */
    override def clone(): Stack[A] = {
        val res = new Stack[A];
        res ++= this;
        res
    }
}
