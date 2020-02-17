package presentation;

import businessLayer.Manipulation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RemoveController extends MouseAdapter {
    MainFrame frame;
    Manipulation man;

    /**<p>public RemoveController(MainFrame frame, Manipulation man)</p>
     * Controller for removing an item from table and database schema.
     * @param frame
     * @param man
     */
    public RemoveController(MainFrame frame, Manipulation man){
        this.frame=frame;
        this.man=man;
    }
    public void mouseClicked(MouseEvent e) {
        int i=man.getTable().getSelectedRow();
        man.remove(i);
    }
}
