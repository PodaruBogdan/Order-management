package businessLayer;

import javax.swing.*;
import java.util.List;

public interface Manipulation<T> {

     /**<p>List&#60;T&#60; getList()</p>
      * Get a list of objects corresponding to a field
      * @return List&#60;T&#62; - the list of T type objects
      */
     List<T> getList();

     /**<p> JTable getTable()</p>
      * Gets a the JTable of a corresponding field.
      * @return JTable - the JTable field
      */
     JTable getTable();

     /**<p>void add(T object)</p>
      * Add an object of type T to a class instance.
      * @param object - the object to be added
      */
     void add(T object);


      /**<p>void remove(int index)</p>
      * Remove an object of type T from a class instance.
      * @param index - the index of the object to be removed
      */
     void remove(int index);

     /**<p>void edit(int rowIndex,int columnIndex,Object oldId)</p>
      * Make changes to a table-structured variable of a class.
      * @param rowIndex - the row index
      * @param columnIndex - the column index
      * @param oldId - what was before the changes
      */
     void edit(int rowIndex,int columnIndex,Object oldId);

     /**<p>void update(T object,String field,Object value)</p>
      * Update an object's field with a value.
      * @param object - object to be updated
      * @param field - the object's field to get updated
      * @param value - the update value
      */
     void update(T object,String field,Object value);

     /** <p>void findId(int id)</p>
      * Finds the id of an object
      * @param id - the id to be found
      */
     void findId(int id);
}
