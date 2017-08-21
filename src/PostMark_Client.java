import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import org.json.*;

public class PostMark_Client {
    String subject, messageBody;
    Vector emails;

    public void sendMessage(String fullFilePath) {
        int recipientNum = 0;
        System.out.println("sending email");
        try {
            FileReader fr = new FileReader(fullFilePath);
            BufferedReader br = new BufferedReader(fr);

            String temp = "";
            emails = new Vector();
            do {

                temp = br.readLine();

                System.out.println("temp: " + temp);

                if (temp.startsWith("recipient")) {
                    emails.add(temp.substring(temp.indexOf(":")+1).trim());
                    recipientNum++;
                }

                if (temp.startsWith("subject: ")) {
                    subject = temp.substring(8).trim();
                }
                if (temp.startsWith("message: ")) {
                    messageBody = temp.substring(8).trim();
                }
            } while (br.readLine() != null);

            System.out.println(subject);
            System.out.println(messageBody);

            for (int i = 0; i < recipientNum; i++) {
                System.out.println("sending: " + i);
                sendEmail(i);
            }

        } catch (Exception ee) {
            System.out.println("Client: Failed to open " + fullFilePath);
        }
    }

    private void sendEmail(int recipientNumber) {
        try {
            Socket cs = new Socket("api.postmarkapp.com", 80);
            OutputStream os = cs.getOutputStream();
            PrintWriter pw = new PrintWriter(os);

            String fileName = "\\config\\config.json";
            String fullFilePath = System.getProperty("user.dir") + fileName;
            String config = "";
            try {
                config = new String(Files.readAllBytes(Paths.get(fullFilePath)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject obj = new JSONObject(config);
            String email = obj.getString("email");
            String token = obj.getString("token");

            String content = "{\"From\": \"" + email + "\", \"To\": \"" + (String)emails.elementAt(recipientNumber) + "\", \"Subject\": \"" + subject + "\", \"TextBody\": \"" + messageBody + "\"}";
            System.out.println(email);
            System.out.println(token);
            System.out.println(content);

            pw.println("POST /email HTTP/1.1");
            pw.println("Accept: application/json");
            pw.println("Host: localhost");
            pw.println("X-Postmark-Server-Token: " + token + "");
            pw.println("Content-Type: application/json");
            pw.println("Content-Length: " + content.getBytes().length + "\n");
            pw.print(content);
            System.out.println(content.getBytes().length  );
            System.out.println(content);
            pw.flush();

            pw.close();
            System.out.println("SENT");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
