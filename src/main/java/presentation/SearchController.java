package presentation;

import businessLayer.Manipulation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchController extends MouseAdapter {
    private JFrame frame;
    private Manipulation man;

    /**<p>public SearchController(JFrame frame, Manipulation man)</p>
     * Controller for search by Id .
     * Opens a popup for user input of the id to be searched.
     * @param frame - frame that contains the table
     * @param man - contains the logic
     */
    public SearchController(JFrame frame, Manipulation man){
        this.man=man;
        this.frame=frame;
    }
    public void mouseClicked(MouseEvent e) {
        new SearchPopUp(man);
    }
}
