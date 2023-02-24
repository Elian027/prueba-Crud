import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class crud {
    private JPanel panel1;
    private JTextField codtxt;
    private JTextField nomtxt;
    private JComboBox gencom;
    private JTextField celtxt;
    private JButton crearButton;
    private JButton buscarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;

    private JLabel codest;
    private JLabel nomest;
    private JLabel genest;
    private JLabel celest;
    private JLabel edadest;
    private JLabel emailest;

    private JComboBox edadcom;
    private JTextField emailtxt;
    PreparedStatement ps;

    public void limpiar() {
        codtxt.setText("");
        nomtxt.setText("");
        celtxt.setText("");
        emailtxt.setText("");
    }
    public static Connection getConecction() {
        Connection cn = null;
        String base = "prueba"; //Nombre de la BD
        String url = "jdbc:mysql://localhost:3306/" + base; //Direccion, puerto y nombre BD
        String user = "root"; //Usuario
        String pass = "dariel17"; //Contraseña
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return cn;
    }

    public crud() {
        //Cargar los datos al combo box
        try {
            Connection ct;
            ct = getConecction();
            Statement carGen = ct.createStatement();
            ResultSet gen = carGen.executeQuery("Select * from genero");
            while (gen.next()) {
                gencom.addItem(gen.getString("gnr"));
            }
            Statement carEdad = ct.createStatement();
            ResultSet eda = carEdad.executeQuery("Select * from edad");
            while (eda.next()) {
                edadcom.addItem(eda.getString("ed"));
            }
            ct.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection cn;
                try {
                    cn = getConecction();
                    ps = cn.prepareStatement("INSERT INTO estudiantes (CodEst, NomEst, CelEst, GenEst, EmailEst, EdadEst) values (?,?,?,?,?,?)");
                    ps.setString(1, codtxt.getText());
                    ps.setString(2, nomtxt.getText());
                    ps.setString(3, celtxt.getText());
                    ps.setString(4, gencom.getSelectedItem().toString());
                    ps.setString(5, emailtxt.getText());
                    ps.setString(6, edadcom.getSelectedItem().toString());
                    System.out.println(ps);
                    int res = ps.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null,"Estudiante agregado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null,"No sa agregó al estudiante");
                    }
                    cn.close();
                    limpiar();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection cx;
                try {
                    cx = getConecction();
                    String qr = "select * from estudiantes where CodEst = "+codtxt.getText()+";";
                    Statement s = cx.createStatement();
                    ResultSet rs = s.executeQuery(qr);
                    System.out.println(rs);
                    while(rs.next()) {
                        nomtxt.setText(rs.getString(2));
                        celtxt.setText(rs.getString(3));
                        gencom.setSelectedItem(rs.getString(4));
                        emailtxt.setText(rs.getString(5));
                        edadcom.setSelectedItem(rs.getString(6));
                    }
                    cx.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection ct;
                try {
                    String qr = "Update estudiantes set NomEst = ?, CelEst = ?, GenEst = ?, EmailEst = ?, EdadEst = ? where CodEst = "+codtxt.getText();
                    ct = getConecction();
                    ps = ct.prepareStatement(qr);
                    ps.setString(1, nomtxt.getText());
                    ps.setString(2, celtxt.getText());
                    ps.setString(3, gencom.getSelectedItem().toString());
                    ps.setString(4, emailtxt.getText());
                    ps.setString(5, edadcom.getSelectedItem().toString());
                    ps.executeUpdate();
                    System.out.println(ps);
                    int res = ps.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null,"Estudiante actualizado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null,"No se pudo actualizar");
                    }
                    ct.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection ct;
                try {
                    String qr = "delete from estudiantes where CodEst ="+codtxt.getText();
                    ct = getConecction();
                    ps = ct.prepareStatement(qr);
                    ps.executeUpdate();
                    int res = ps.executeUpdate();
                    if (res<0) {
                        JOptionPane.showMessageDialog(null,"No se pudo eliminar al estudiante");
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Estudiante eliminado correctamente");
                    }
                    ct.close();
                    limpiar();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("Elian Moreira CRUD");
        jf.setContentPane(new crud().panel1);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
    }
}
