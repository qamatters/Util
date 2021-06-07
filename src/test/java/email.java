import ss.EmailUtility;

import java.sql.SQLOutput;

public class email {



    public static void main(String[] args) {

        try {
            EmailUtility  emailRead = new EmailUtility("testqamatters@gmail.com", "", "smtp.gmail.com", EmailUtility.EmailFolder.INBOX);
            int num = emailRead.getNumberOfMessages();
            System.out.println(num);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
