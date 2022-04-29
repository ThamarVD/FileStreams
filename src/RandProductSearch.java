import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductSearch extends JFrame{
    JPanel mainPanel;
    RandomAccessFile randFile;
    public RandProductSearch(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        try {
            randFile = new RandomAccessFile(new File("products.txt"), "r");
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
        setTitle("Product Search");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    JPanel userPanel;
    JTextField searchFld;
    JButton searchBtn;
    JTextArea searchTxt;
    JScrollPane searchPane;
    private void createUserPanel(){
        userPanel = new JPanel();
        searchFld = new JTextField(25);
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> search());
        searchTxt = new JTextArea(35, 35);
        searchPane = new JScrollPane(searchTxt);
        userPanel.add(searchFld);
        userPanel.add(searchBtn);
        userPanel.add(searchPane);
    }

    private void search(){
        File selectedFile;
        String rec = "";
        ArrayList<String[]> products = new ArrayList<String[]>();

        try
        {
            File workingDirectory = new File("products.txt");
            Path file = workingDirectory.toPath();
            InputStream in =
                    new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));

            while(reader.ready())
            {
                rec = reader.readLine();
                products.add(new String[]{
                        rec.substring(0,35).trim(),
                        rec.substring(35, 41).trim(),
                        rec.substring(41, 47).trim(),
                        rec.substring(47).trim()
                });
            }
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        searchTxt.setText("");
        searchTxt.append(String.format("%15s %10s %10s %25s\n", "Name", "ID", "Price", "Description"));
        searchTxt.append("=======================================================\n");
        for(String[] product : products){
            if(product[0].toLowerCase(Locale.ROOT).contains(searchFld.getText().toLowerCase(Locale.ROOT))){
                ArrayList<String> pAtt = new ArrayList<String>();
                for (String attributes : product) {
                    pAtt.add(attributes);
                }
                searchTxt.append(String.format("%15s %10s %10s %25s\n", pAtt.get(0), pAtt.get(1), pAtt.get(2), pAtt.get(3)));
            }
        }
    }
}
