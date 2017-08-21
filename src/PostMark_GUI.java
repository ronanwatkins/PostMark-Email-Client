import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PostMark_GUI extends JPanel implements ActionListener {

    JPanel recipientTextPanel, textPanel, panelForTextFields, panelForRecipientFields, panelForSendButton;
    JLabel recipientLabel, subjectLabel, addMoreRecipients;
    JLabel recipientLabelValid, recipientLabel2Valid, recipientLabel3Valid, recipientLabel4Valid, recipientLabel5Valid;
    JTextField recipientField, subjectField, recipientField2, recipientField3, recipientField4, recipientField5;
    JTextArea messageField;
    JButton SendButton;

    PostMark_Client client;

    JButton openButton, selectUnsubListButton, removeEmailsButton, sendButton2;
    JTextArea emailList, unsubscribeList, messageField2;
    JTextField subjectField2;
    JFileChooser fc;

    File emailListFile, unsubscribeListFile;
    String unsubscribeEmailListText, emailListText;

    public PostMark_GUI() {
        super(new GridLayout(1,1));

        JTabbedPane tab1 = new JTabbedPane();
        JComponent panel1 = makeTab1();
        tab1.addTab("Send Single Email", panel1);
        tab1.setMnemonicAt(0, KeyEvent.VK_1);
        //add(tab1);

        JComponent panel2 = makeTab2();
        tab1.addTab("Send Multiple Emails", panel2);
        tab1.setMnemonicAt(0, KeyEvent.VK_1);
        add(tab1);
    }

    private JComponent makeTab2() {
        //For layout purposes, put the buttons in a separate panel
        JPanel Tab2Panel = new JPanel(); //use FlowLayout
        Tab2Panel.setLayout(null);

        //Create a file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        openButton = new JButton("Select Mailing List");
        openButton.setLocation(50,40);
        openButton.setSize(150,30);
        openButton.addActionListener(this);
        Tab2Panel.add(openButton);

        selectUnsubListButton= new JButton("Select Unsubscribe List");
        selectUnsubListButton.setLocation(270,40);
        selectUnsubListButton.setSize(200,30);
        selectUnsubListButton.addActionListener(this);
        Tab2Panel.add(selectUnsubListButton);

        emailList = new JTextArea();
        Border border = BorderFactory.createLineBorder(Color.gray);
        emailList.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        emailList.setLocation(50,90);
        emailList.setSize(200, 500);
        Tab2Panel.add(emailList);

        unsubscribeList = new JTextArea();
        unsubscribeList.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        unsubscribeList.setLocation(270,90);
        unsubscribeList.setSize(200, 500);
        Tab2Panel.add(unsubscribeList);

        JLabel sendLabel = new JLabel("Subject:");
        sendLabel.setLocation(500, 10);
        sendLabel.setSize(50, 30);
        Tab2Panel.add(sendLabel);

        subjectField2 = new JTextField();
        subjectField2.setLocation(500, 40);
        subjectField2.setSize(200, 30);
        Tab2Panel.add(subjectField2);

        messageField2 = new JTextArea();
        messageField2.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        messageField2.setLocation(500, 90);
        messageField2.setSize(600, 400);
        Tab2Panel.add(messageField2);

        // Button for sending email
        sendButton2 = new JButton("Send");
        sendButton2.setLocation(1020,510);
        sendButton2.setSize(80, 30);
        sendButton2.addActionListener(this);
        Tab2Panel.add(sendButton2);

        add(Tab2Panel);

        return Tab2Panel;
    }

    private JComponent makeTab1() {
        // We create a bottom JPanel to place everything on.
        JPanel Tab1Panel = new JPanel();
      // Tab1Panel.setLayout(new GridLayout(1,1));
        Tab1Panel.setLayout(null);

        // Creation of a Panel to contain the JLabels
        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setLocation(250, 35);
        textPanel.setSize(810, 40);
        Tab1Panel.add(textPanel);

        recipientTextPanel = new JPanel();
        recipientTextPanel.setLayout(null);
        recipientTextPanel.setLocation(10, 35);
        recipientTextPanel.setSize(240, 40);
        Tab1Panel.add(recipientTextPanel);

        // Recipient Label
        recipientLabel = new JLabel("Recipient");
        recipientLabel.setLocation(30, 0);
        recipientLabel.setSize(70, 40);
        recipientLabel.setHorizontalAlignment(4);
        recipientTextPanel.add(recipientLabel);

        recipientLabelValid = new JLabel();
        recipientLabelValid.setLocation(110, 0);
        recipientLabelValid.setSize(70, 40);
        recipientLabelValid.setHorizontalAlignment(4);
        recipientTextPanel.add(recipientLabelValid);

        // Subject Label
        subjectLabel = new JLabel("Subject");
        subjectLabel.setLocation(0, 0);
        subjectLabel.setSize(70, 40);
        subjectLabel.setHorizontalAlignment(4);
        textPanel.add(subjectLabel);

        // Add more recipients Label
        addMoreRecipients = new JLabel("Add More Recipients");
        addMoreRecipients.setLocation(370, 0);
        addMoreRecipients.setSize(170, 40);
        addMoreRecipients.setHorizontalAlignment(4);
        textPanel.add(addMoreRecipients);

        //Add more recipients Panel container
        panelForRecipientFields = new JPanel();
        panelForRecipientFields.setLayout(null);
        panelForRecipientFields.setLocation(670, 80);
        panelForRecipientFields.setSize(400, 200);
        Tab1Panel.add(panelForRecipientFields);

        recipientField2 = new JTextField(8);
        recipientField2.setLocation(0, 0);
        recipientField2.setSize(200, 30);
        panelForRecipientFields.add(recipientField2);

        recipientLabel2Valid = new JLabel();
        recipientLabel2Valid.setLocation(70, -5);
        recipientLabel2Valid.setSize(170, 40);
        recipientLabel2Valid.setHorizontalAlignment(4);
        panelForRecipientFields.add(recipientLabel2Valid);

        recipientField3 = new JTextField(8);
        recipientField3.setLocation(0, 40);
        recipientField3.setSize(200, 30);
        panelForRecipientFields.add(recipientField3);

        recipientLabel3Valid = new JLabel();
        recipientLabel3Valid.setLocation(70, 35);
        recipientLabel3Valid.setSize(170, 40);
        recipientLabel3Valid.setHorizontalAlignment(4);
        panelForRecipientFields.add(recipientLabel3Valid);

        recipientField4 = new JTextField(8);
        recipientField4.setLocation(0, 80);
        recipientField4.setSize(200, 30);
        panelForRecipientFields.add(recipientField4);

        recipientLabel4Valid = new JLabel();
        recipientLabel4Valid.setLocation(70, 75);
        recipientLabel4Valid.setSize(170, 40);
        recipientLabel4Valid.setHorizontalAlignment(4);
        panelForRecipientFields.add(recipientLabel4Valid);

        recipientField5 = new JTextField(8);
        recipientField5.setLocation(0, 120);
        recipientField5.setSize(200, 30);
        panelForRecipientFields.add(recipientField5);

        recipientLabel5Valid = new JLabel();
        recipientLabel5Valid.setLocation(70, 115);
        recipientLabel5Valid.setSize(170, 40);
        recipientLabel5Valid.setHorizontalAlignment(4);
        panelForRecipientFields.add(recipientLabel5Valid);

        // TextFields Panel Container
        panelForTextFields = new JPanel();
        panelForTextFields.setLayout(null);
        panelForTextFields.setLocation(30, 40);
        panelForTextFields.setSize(650, 500);
        Tab1Panel.add(panelForTextFields);

        // recipient Textfield
        recipientField = new JTextField(8);
        recipientField.setLocation(30, 40);
        recipientField.setSize(200, 30);
        panelForTextFields.add(recipientField);

        // subject Textfield
        subjectField = new JTextField(8);
        subjectField.setLocation(250, 40);
        subjectField.setSize(200, 30);
        panelForTextFields.add(subjectField);

        messageField = new JTextArea();
        Border border = BorderFactory.createLineBorder(Color.gray);
        messageField.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        messageField.setLocation(30, 80);
        messageField.setSize(600, 400);
        panelForTextFields.add(messageField);

        panelForSendButton = new JPanel();
        panelForSendButton.setLayout(null);
        panelForSendButton.setLocation(575, 550);
        panelForSendButton.setSize(80, 30);
        Tab1Panel.add(panelForSendButton);

        // Button for sending email
        SendButton = new JButton("Send");
        SendButton.setLocation(0,0);
        SendButton.setSize(80, 30);
        SendButton.addActionListener(this);
        panelForSendButton.add(SendButton);

        Tab1Panel.setOpaque(true);
        return Tab1Panel;
    }
    public void actionPerformed(ActionEvent e) {

        client = new PostMark_Client();
        boolean field1Valid = true;
        boolean field2Valid = true;
        boolean field3Valid = true;
        boolean field4Valid = true;
        boolean field5Valid = true;

        boolean field2hasText = false;
        boolean field3hasText = false;
        boolean field4hasText = false;
        boolean field5hasText = false;

        if (e.getSource() == SendButton) {
            if (checkFormat(recipientField.getText())) {
                recipientLabelValid.setForeground(Color.green);
                recipientLabelValid.setText("Valid");

                field1Valid = true;
            } else {
                recipientLabelValid.setForeground(Color.red);
                recipientLabelValid.setText("Invalid");

                field1Valid = false;
            }

            if (recipientField2.getText().length() > 0) {
                if (checkFormat(recipientField2.getText())) {
                    recipientLabel2Valid.setForeground(Color.green);
                    recipientLabel2Valid.setText("Valid");

                    field2Valid = true;
                    field2hasText = true;
                } else {
                    recipientLabel2Valid.setForeground(Color.red);
                    recipientLabel2Valid.setText("Invalid");

                    field2Valid = false;
                }
            }

            if (recipientField3.getText().length() > 0) {
                if (checkFormat(recipientField3.getText())) {
                    recipientLabel3Valid.setForeground(Color.green);
                    recipientLabel3Valid.setText("Valid");

                    field3Valid = true;
                    field3hasText = true;
                } else {
                    recipientLabel3Valid.setForeground(Color.red);
                    recipientLabel3Valid.setText("Invalid");

                    field3Valid = false;
                }
            }
            if (recipientField4.getText().length() > 0) {
                if (checkFormat(recipientField4.getText())) {
                    recipientLabel4Valid.setForeground(Color.green);
                    recipientLabel4Valid.setText("Valid");

                    field4Valid = true;
                    field4hasText = true;
                } else {
                    recipientLabel4Valid.setForeground(Color.red);
                    recipientLabel4Valid.setText("Invalid");

                    field4Valid = false;
                }
            }

            if (recipientField5.getText().length() > 0) {
                if (checkFormat(recipientField5.getText())) {
                    recipientLabel5Valid.setForeground(Color.green);
                    recipientLabel5Valid.setText("Valid");

                    field5Valid = true;
                    field5hasText = true;
                } else {
                    recipientLabel5Valid.setForeground(Color.red);
                    recipientLabel5Valid.setText("Invalid");

                    field5Valid = false;
                }
            }

            if (field1Valid && field2Valid && field3Valid && field4Valid && field5Valid) {
                System.out.println("all valid");
                send(field2hasText, field3hasText, field4hasText, field5hasText);
            }
        } else if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(PostMark_GUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                emailListFile = fc.getSelectedFile();
                if(emailListFile.getName().contains(".txt")) {
                    try {
                        emailListText = new String(Files.readAllBytes(Paths.get(emailListFile.getName())));
                        emailList.setText(emailListText);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        } else if (e.getSource() == selectUnsubListButton) {
            int returnVal = fc.showOpenDialog(PostMark_GUI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                unsubscribeListFile = fc.getSelectedFile();

                if(unsubscribeListFile.getName().contains(".txt")) {
                    try {
                        unsubscribeEmailListText = new String(Files.readAllBytes(Paths.get(unsubscribeListFile.getName())));
                        unsubscribeList.setText(unsubscribeEmailListText);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        } else if(e.getSource() == sendButton2) {
            String[] unsubEmails = unsubscribeEmailListText.trim().split("\r");
            String[] emails = emailListText.trim().split("\r");

            List<String> list1 = new ArrayList<>();
            for (int i = 0; i < emails.length; i++) {
                list1.add(i, emails[i]);
                System.out.println("emails: "  +emails[i]);
                System.out.println("list1: " + list1.get(i));
            }

            List<String> list2 = new ArrayList<>();
            for (int i = 0; i < unsubEmails.length; i++) {
                list2.add(i, unsubEmails[i]);
                System.out.println("Unsub emails: "  +unsubEmails[i]);
                System.out.println("list2: " + list2.get(i));
            }

            list1.removeAll(list2);

            emailListText = "";
            for(int i=0; i<list1.size(); i++) {
                emailListText += list1.get(i);
            }
            System.out.println("\nemailListText: " + emailListText);
            emailList.setText(emailListText);

            send();
        }
    }

    private void send() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        String fileName = "\\email\\Email " + dateFormatGmt.format(new Date()) + ".txt";
        String fullFilePath = System.getProperty("user.dir") + fileName;
        System.out.println(fullFilePath);
        File file = new File(fullFilePath);

        try {
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String[] emails = emailListText.split("\n");
            for (int i = 0; i < emails.length; i++) {
                System.out.println(i + " " + emails[i]);
                pw.println("recipient" + (i+1) + ": " + emails[i]);
            }

            pw.println("\nsubject: " + subjectField2.getText() + "\n");
            pw.print("message: " + messageField2.getText());
            pw.close();
        } catch (Exception exception) {
            System.out.println("GUI: Failed to open " + fullFilePath);
        }

        client.sendMessage(fullFilePath);
    }

    private void send(boolean field2, boolean field3, boolean field4, boolean field5) {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        String fileName = "\\email\\Email " + dateFormatGmt.format(new Date()) + ".txt";
        String fullFilePath = System.getProperty("user.dir") + fileName;
        System.out.println(fullFilePath);
        File file = new File(fullFilePath);

        try {
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println("recipient1: " + recipientField.getText() + "\n");

            if (field2)
                pw.println("recipient2: " + recipientField2.getText() + "\n");

            if (field3)
                pw.println("recipient3: " + recipientField3.getText() + "\n");

            if (field4)
                pw.println("recipient4: " + recipientField4.getText() + "\n");

           if (field5)
                pw.println("recipient5: " + recipientField5.getText() + "\n");

            pw.println("subject: " + subjectField.getText() + "\n");
            pw.print("message: " + messageField.getText());
            pw.close();
        } catch (Exception exception) {
            System.out.println("GUI: Failed to open " + fullFilePath);
        }

        client.sendMessage(fullFilePath);
    }

    private boolean checkFormat(String email) {
        boolean state = false;

        if (email.contains("@") && email.contains(".") && email.indexOf("@") < email.indexOf(".") && email.indexOf("@") != 0 && email.indexOf(".") != email.length())
            state = true;

        return state;
    }

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("PostMark Email PostMark_GUI");

        PostMark_GUI demo = new PostMark_GUI();
        frame.add(new PostMark_GUI(), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(getScreenWidth() - 100, getScreenHeight() - 100);
        frame.setVisible(true);
    }

    private static int getScreenWidth() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    private static int getScreenHeight() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}