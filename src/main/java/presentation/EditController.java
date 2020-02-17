package presentation;

import businessLayer.Manipulation;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class EditController extends MouseAdapter {
    MainFrame frame;
    Manipulation man;

    /**
     * Controller for edit button from TablePanels.
     * @param frame
     * @param man
     */
    public EditController(MainFrame frame, Manipulation man){
        this.frame=frame;
        this.man=man;
    }
    public void mouseClicked(MouseEvent e) {
        final int i=man.getTable().getSelectedRow();
        final int j=man.getTable().getSelectedColumn();
        if(i==-1)
            return;
        JTable table=man.getTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        final Object oldId=model.getValueAt(i,0);
        table.setDefaultEditor(Object.class , new DefaultCellEditor(new JTextField()));
        table.setSelectionBackground(Color.red);
        table.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e) {
                man.edit(i, j, oldId);
                man.getTable() .setDefaultEditor(Object.class, null);
                man.getTable().setSelectionBackground(Color.gray);
            }

            public void editingCanceled(ChangeEvent e) {
               // System.out.println("The user canceled editing successfully.");
            }
        });
    }
}
