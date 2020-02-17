package dataAccessLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractDao<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * <p>public JTable createTable(List &#60;T&#62; objects)</p>
     * Creates a table based on a list of objects and using reflection.
     * @param objects - the list the table is created on
     * @return JTable (the table created)
     */
    public JTable createTable(List<T> objects) {
        ArrayList<String> arrayColumnNames = new ArrayList<String>();
        for (Field field : objects.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            arrayColumnNames.add(field.getName());
        }
        String[] columnNames = new String[arrayColumnNames.size()];
        columnNames=arrayColumnNames.toArray(columnNames);
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        for (T object : objects) {
            ArrayList<Object> oneRowData = new ArrayList<Object>();
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    oneRowData.add(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            Object[] oneRowDataArray = new Object[oneRowData.size()];
            oneRowDataArray=oneRowData.toArray(oneRowDataArray);
            tableModel.addRow(oneRowDataArray);
        }
        table.setDefaultEditor(Object.class, null);
        return table;
    }

    /**
     * <p>public List&#60;T&#62; createObjectList(ResultSet rs)</p>
     *  Create a list of objcets of generic type &#60;T&#62; based on a ResultSet
     * @param rs - the ResultSet given from a query
     * @return List&#60;T&#62; (the list of &#60;T&#62; type objects)
     */
    public List<T> createObjectList(ResultSet rs) {
        List<T> list = new ArrayList<T>();
        try {
            while (rs.next()) {
                Field[] fields = type.getDeclaredFields();
                Class[] argTypes = new Class[fields.length];
                Object[] fieldsData = new Object[fields.length];
                int i = 0;
                for (Field field : fields) {
                    argTypes[i] = field.getType();//get field types
                    Object value = rs.getObject(field.getName());
                    fieldsData[i] = value;
                    i++;
                }
                Constructor desiredConstructor = type.getConstructor((Class<T>[]) argTypes);
                T instanceObject = (T) desiredConstructor.newInstance(fieldsData);
                list.add(instanceObject);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }
    private String createSelectionQuery(String field){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("select * from ");
        stringBuilder.append(type.getSimpleName()+"s");
        stringBuilder.append(" where "+field+"=?");
        return stringBuilder.toString();
    }
    private String createUpdateQuery(Object[] columns,String field){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(type.getSimpleName()+"s ");
        stringBuilder.append("set ");
        for(int i=0;i<columns.length;i++){
            stringBuilder.append(columns[i]);
            stringBuilder.append(" = ");
            stringBuilder.append("?,");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(" where "+field+"=?");
        return stringBuilder.toString();
    }
    private String createDeleteQuery(String field){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("delete from ");
        stringBuilder.append(type.getSimpleName()+"s");
        stringBuilder.append(" where "+field+"=?");
        return stringBuilder.toString();
    }
    private String createInsertQuery(T obj){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("insert into ");
        stringBuilder.append(type.getSimpleName()+"s");
        stringBuilder.append(" values(");
        for(Field f:obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            try {
                if(f.get(obj) instanceof Integer){
                    stringBuilder.append(f.get(obj));
                    stringBuilder.append(",");
                }
                else{
                    stringBuilder.append("\'");
                    stringBuilder.append(f.get(obj));
                    stringBuilder.append("\',");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    private String createProjectionQuery(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("select * from ");
        stringBuilder.append(type.getSimpleName()+"s");
        return stringBuilder.toString();
    }
    public List<T> projectionQuery(){
        String sql=createProjectionQuery();
        ResultSet resultSet=AccessDatabase.executeQuery(sql);
        return createObjectList(resultSet);
    }
    public int add(T obj){
        String sql=createInsertQuery(obj);
        int result=AccessDatabase.executeUpdate(sql);
        return result;
    }
    public int remove(int id){
        String sql=createDeleteQuery("id");
        int rs=AccessDatabase.executeUpdateParameterized(sql,1,id);
        return rs;
    }
    public int update(Object[] values,Object[] columns,String field,Object id){
        String sql=createUpdateQuery(columns,field);
        int rs=AccessDatabase.executeUpdateParameterized(sql,values,id);
        return rs;
    }
    public T getObjectById(Object id){
        String objWithId=createSelectionQuery("id");
        ResultSet rs=AccessDatabase.executeQueryParameterized(objWithId,1,id);
        return createObjectList(rs).get(0);
    }

}
