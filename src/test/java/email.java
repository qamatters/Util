import ss.EmailUtility;

import javax.mail.Message;

public class email {
    public static void main(String[] args) {
//        try {
//            EmailUtility emailRead = new EmailUtility(EmailUtility.EmailFolder.INBOX);
//            int num = emailRead.getNumberOfMessages();
//            System.out.println(num);
//            Message[] m = emailRead.getMessagesBySubject("Multiple Attachments",false, 5);
//            for(int i =0; i < m.length; i++) {
//                System.out.println(emailRead.getCountOfAttachmentsFromMessage(m[i]) );
//                emailRead.saveAttachments(m[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String s = "deepak.matpal.niv";
        int index = s.lastIndexOf('.');
        String x = s.substring(0,index);
        System.out.println(x);

    }
}

