package presentation;

import businessLayer.Manipulation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddController extends MouseAdapter {
    MainFrame frame;
    Manipulation man;

    /**<p>public AddController(MainFrame frame, Manipulation man)</p>
     * Controller for add an item operation.
     * Sets what has to be executed on add button click.
     * @param frame
     * @param man
     */
    public AddController(MainFrame frame, Manipulation man){
        this.frame=frame;
        this.man=man;
    }
    public void mouseClicked(MouseEvent e) {
        new AddPopUp(frame,man);
    }
}
