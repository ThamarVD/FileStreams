import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.RandomAccess;

public class RandProductMaker extends JFrame {
    JPanel mainPanel;
    RandomAccessFile randFile;
    int productCounter = 0;
    public RandProductMaker(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        try {
            randFile = new RandomAccessFile(new File("products.txt"), "rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        createUserPanel();
        mainPanel.add(userPanel);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    randFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        add(mainPanel);
        setTitle("Product Creator");
        setSize(550, 130);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    JPanel userPanel;
    JLabel nameLbl;
    JTextField nameFld;
    JLabel idLbl;
    JTextField idFld;
    JLabel costLbl;
    JTextField costFld;
    JLabel descLbl;
    JTextField descFld;
    JButton submitBtn;
    JTextField counter;
    private void createUserPanel(){
        userPanel = new JPanel();
        nameLbl = new JLabel("Name");
        nameFld = new JTextField(25);
        userPanel.add(nameLbl);
        userPanel.add(nameFld);
        idLbl = new JLabel("ID");
        idFld = new JTextField(6);
        userPanel.add(idLbl);
        userPanel.add(idFld);
        costLbl = new JLabel("Cost");
        costFld = new JTextField(6);
        userPanel.add(costLbl);
        userPanel.add(costFld);
        descLbl = new JLabel("Description");
        descFld = new JTextField(35);
        userPanel.add(descLbl);
        userPanel.add(descFld);
        nameFld.addActionListener(actionEvent -> trimFields());
        idFld.addActionListener(actionEvent -> trimFields());
        costFld.addActionListener(actionEvent -> trimFields());
        descFld.addActionListener(actionEvent -> trimFields());
        JPanel tempPanel = new JPanel();
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> submit());
        counter = new JTextField(3);
        counter.setEditable(false);
        tempPanel.add(submitBtn);
        tempPanel.add(counter);
        userPanel.add(tempPanel);
    }

    private void trimFields(){
        if(nameFld.getText().length() > 35)
            nameFld.setText(nameFld.getText().substring(0, 35));
        if(idFld.getText().length() > 6)
            idFld.setText(idFld.getText().substring(0, 6));
        if(descFld.getText().length() > 75)
            descFld.setText(descFld.getText().substring(0, 75));
        if(costFld.getText().length() > 6)
            costFld.setText(costFld.getText().replaceAll("[^\\d.]", "").substring(0, 6));
    }

    private void submit(){
        trimFields();
        try {
            randFile.write(String.format("%35s%6s%6s%75s\n",nameFld.getText(), idFld.getText(), costFld.getText(), descFld.getText()).getBytes(StandardCharsets.UTF_8));
            productCounter++;
            counter.setText(String.valueOf(productCounter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}