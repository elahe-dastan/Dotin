import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JLabel registeration;
    private JLabel lblFirstName;
    private JTextField txtFirstName;
    private JLabel lblLastName;
    private JTextField txtLastName;
    private JLabel lblNationalCode;
    private JTextField txtNationalCode;
    private JLabel lblAddress;
    private JTextArea txtAddress;
    private JButton btnRegister;
    private JLabel registrationMessage;
    private JLabel search;
    private JLabel lblNationalCode_search;
    private JTextField txtNationalCode_search;
    private JPanel informationPanel;
    private JLabel lblFirstName_search;
    private JLabel lblFirstName_result;
    private JLabel lblLastName_search;
    private JLabel lblLastName_result;
    private JLabel lblAddress_search;
    private JLabel lblAddress_result;
    private JButton btnSearch;
    private JLabel searchFailMessage;

    public GUI(){
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        registeration = new JLabel("Registration Field");
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(registeration, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        lblFirstName = new JLabel("First Name");
        contentPanel.add(lblFirstName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        txtFirstName = new JTextField(15);
        contentPanel.add(txtFirstName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        lblLastName = new JLabel("Last Name");
        contentPanel.add(lblLastName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 2;
        txtLastName = new JTextField();
        contentPanel.add(txtLastName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        lblNationalCode = new JLabel("National Code");
        contentPanel.add(lblNationalCode, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 3;
        txtNationalCode = new JTextField();
        contentPanel.add(txtNationalCode, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        lblAddress = new JLabel("Address");
        contentPanel.add(lblAddress, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 4;
        txtAddress = new JTextArea();
        contentPanel.add(txtAddress, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 5;
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new RegisterationButtonHandler());
        contentPanel.add(btnRegister, c);
        registrationMessage = new JLabel("");
        c.gridy = 6;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(registrationMessage, c);
        search = new JLabel("Search Part");
        c.gridy = 7;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(search, c);
        c.gridx = 0;
        c.gridy = 8;
        lblNationalCode_search = new JLabel("national code");
        contentPanel.add(lblNationalCode_search, c);
        c.gridx = 1;
        c.gridy = 8;
        txtNationalCode_search = new JTextField();
        contentPanel.add(txtNationalCode_search, c);
        informationPanel = new JPanel(new GridLayout(3, 2));
        lblFirstName_search = new JLabel("First Name");
        informationPanel.add(lblFirstName_search);
        lblFirstName_result = new JLabel();
        informationPanel.add(lblFirstName_result);
        lblLastName_search = new JLabel("Last Name");
        informationPanel.add(lblLastName_search);
        lblLastName_result = new JLabel();
        informationPanel.add(lblLastName_result);
        lblAddress_search = new JLabel("Address");
        informationPanel.add(lblAddress_search);
        lblAddress_result = new JLabel();
        informationPanel.add(lblAddress_result);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 9;
        informationPanel.setVisible(false);
        contentPanel.add(informationPanel, c);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new SearchButtonHandler());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 10;
        contentPanel.add(btnSearch, c);
        searchFailMessage = new JLabel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 11;
        contentPanel.add(searchFailMessage, c);
        setContentPane(contentPanel);
    }

    private class RegisterationButtonHandler implements ActionListener {

        Person person = new Person();

        public void actionPerformed(ActionEvent e) {
            registrationMessage.setText("");
            if (checkNationalCode(registrationMessage, txtNationalCode.getText())) {
                Person test = search(txtNationalCode.getText());
                if (test == null) {
                    if (txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtNationalCode.getText().equals("") || txtAddress.getText().equals("")) {
                        registrationMessage.setText("None of the above fields can be empty");
                    } else {
                        person.setFirstName(txtFirstName.getText());
                        person.setLastName(txtLastName.getText());
                        person.setNationalCode(txtNationalCode.getText());
                        person.setAddress(txtAddress.getText());

                        try {
                            EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("JEE");
                            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
                            entityManager.getTransaction().begin();
                            entityManager.merge(person);
                            entityManager.getTransaction().commit();
                            ENTITY_MANAGER_FACTORY.close();

                            registrationMessage.setText("Registration was Successful");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            registrationMessage.setText("Registration failed");
                        }
                    }
                } else {
                    registrationMessage.setText("This national code already exists");
                    informationPanel.setVisible(false);
                }
            }
        }
    }

    private class SearchButtonHandler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            informationPanel.setVisible(false);
            searchFailMessage.setText("");
            if (txtNationalCode_search.getText().equals("")){
                searchFailMessage.setText("Enter a national code first");
            }
            else {
                if (checkNationalCode(searchFailMessage, txtNationalCode_search.getText())){
                    Person person = search(txtNationalCode_search.getText());
                    if (person == null) {
                        searchFailMessage.setText("This national code doesn't exist");
                    } else {
                        lblFirstName_result.setText(person.getFirstName());
                        lblLastName_result.setText(person.getLastName());
                        lblAddress_result.setText(person.getAddress());
                        informationPanel.setVisible(true);
                    }
                }
            }
        }
    }

    private Person search(String nationalCode){
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("JEE");
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT p FROM Person p WHERE p.nationalCode = :nationalCode";
        TypedQuery<Person> tq = entityManager.createQuery(query, Person.class);
        tq.setParameter("nationalCode", nationalCode);
        Person person = null;

        try{
            person = tq.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return person;
    }

    private boolean checkNationalCode(JLabel label, String code){
        try {
            Long.parseLong(code);
            if (code.length() != 10) {
                label.setText("The number of the national code digits should be 10 (Do not use -) ");
                return false;
            }
        }
        catch (Exception ex){
            label.setText("The national code is not valid (Don't use -)");
            return false;
        }
        return true;
    }
}
