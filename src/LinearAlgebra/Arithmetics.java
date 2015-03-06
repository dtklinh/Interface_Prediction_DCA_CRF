/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LinearAlgebra;

/**
 *
 * @author t.dang
 */
public interface Arithmetics<T> {
     public T zero();
    public T create(long l);
    public T create(double d);
    public T add( T a, T b );
    public T subtract( T a, T b);
    public T multiply (T a, T b);
    public T parseString( String str );
    public String toString( T a );
}
