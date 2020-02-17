package presentation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class TableController implements ListSelectionListener {
    private JTable table;

    /**<p>public TableController(JTable table)</p>
     * Controller for user interaction with the table.
     * Make editable only on restricted operations and unable in rest.
     * @param table - the table to be restrictioned by user interaction
     */
    public TableController(JTable table){
        this.table=table;
    }
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) {
            table.setDefaultEditor(Object.class, null);
            table.setSelectionBackground(Color.gray);
        }
    }
}
