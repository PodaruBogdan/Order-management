package businessLayer;

import dataAccessLayer.ClientDao;
import model.Client;
import presentation.TableController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClientManipulation implements Manipulation<Client>{
    private List<Client> clientList;
    private JTable table;
    private ClientDao clientDao;
    public ClientManipulation(ClientDao clientDao)
    {
        this.clientDao=clientDao;
        List<Client> clients=clientDao.projectionQuery();
        clientList=clients;
        JTable table= clientDao.createTable(clients);
        this.table=table;
        this.table.getSelectionModel().addListSelectionListener(new TableController(this.table));
    }
    public JTable getTable(){
        return table;
    }
    public List<Client> getList(){
        return clientList;
    }

    public void edit(int rowIndex,int columnIndex,Object oldId) {
        String field="id";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] values={model.getValueAt(rowIndex,columnIndex)};
        Object[] columns={table.getTableHeader().getColumnModel().getColumn(columnIndex).getHeaderValue()};
        Client clientToEdit=clientDao.getObjectById(oldId);
        int result=clientDao.update(values,columns,field,clientToEdit.getId());
        if(result!=-1) {
            Client editedClient=clientDao.getObjectById(model.getValueAt(rowIndex,0));
            Client aux=null;
            for(Client c:clientList){
                if(c.getId()==clientToEdit.getId()){
                    aux=c;
                    break;
                }
            }
            clientList.set(clientList.indexOf(aux),editedClient);
        }
        else
            JOptionPane.showMessageDialog(null,"Cannot update this row ! There is a dependency in Orders table!");
    }

    public void update(Client object, String field, Object value) {

    }

    public void findId(int id) {
        boolean found=false;
        for(Client c:clientList){
            if(c.getId()==id){
                found=true;
                JOptionPane.showMessageDialog(null,c.toString());
                break;
            }
        }
        if(found==false)
            JOptionPane.showMessageDialog(null,"No client with that id exists!");
    }

    public void remove(int index) {
        if(index==-1){
            JOptionPane.showMessageDialog(null,"Nothing was selected..");
            return;
        }
        int op=JOptionPane.showConfirmDialog(null, "Confirm removal?", "Remove item", JOptionPane.YES_NO_OPTION);
        if(op==0){
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object id=null;
            id=model.getValueAt(index,0);
            Client rmvClient=clientDao.getObjectById(id);
            int result=clientDao.remove(rmvClient.getId());
            if(result!=-1) {
                clientList.remove(rmvClient);
                model.removeRow(index);
            }
            else
                JOptionPane.showMessageDialog(null,"Cannot delete this row ! There is a dependency in Orders table!");

        }
    }

    public void add(Client object) {
        for(Client c:clientList){
            if(c.getId()==object.getId()){
                JOptionPane.showMessageDialog(null,"ID already exists!");
                return;
            }
        }
        clientList.add(object);
        clientDao.add(object);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{object.getId(),object.getName()});
    }

    /**<p>public String[] getNames()</p>
     * Get the names of the clientList which is a list of Client object.
     * Each name is accessed through the corresponding getter.
     * @return String[] - array of String with asked names
     */
    public String[] getNames(){
        String[] names=new String[clientList.size()];
        for(int i=0;i<clientList.size();i++)
        {
            names[i]=clientList.get(i).getName();
        }
        return names;
    }
}
