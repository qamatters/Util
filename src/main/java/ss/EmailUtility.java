package ss;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SubjectTerm;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for interacting with an Email application
 */
public class EmailUtility {
    private Folder folder;
    private String folderPath;
    Encrypt encrypt = new Encrypt();

    public enum EmailFolder {
        INBOX("INBOX"),
        SPAM("SPAM");
        private String text;
        private EmailFolder(String text){
            this.text = text;
        }
        public String getText() {
            return text;
        }
    }

    /**
     * Uses email.username and email.password properties from the properties file. Reads from Inbox folder of the email application
     * @throws MessagingException
     */
    public EmailUtility() throws MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        this(EmailFolder.INBOX);
    }

    /**
     * Uses username and password in properties file to read from a given folder of the email application
     * @param emailFolder Folder in email application to interact with
     * @throws MessagingException
     */
    public EmailUtility(EmailFolder emailFolder) throws MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("src//main//resources//email.properties")));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        String serverName = getEmailServerFromProperties();
        String Emailusername = getEmailUsernameFromProperties();
        String emailPassword = getEmailPasswordFromProperties();
        store.connect(serverName,Emailusername, emailPassword  );
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
    }

    public static Properties loadPropertiesFile () {
        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("src//main//resources//email.properties")));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return props;
    }

    /**
     * Connects to email server with credentials provided to read from a given folder of the email application
     * @param username Email username (e.g. janedoe@email.com)
     * @param password Email password
     * @param server Email server (e.g. smtp.email.com)
     * @param emailFolder Folder in email application to interact with
     */
    public EmailUtility(String username, String password, String server, EmailFolder emailFolder) throws MessagingException {
        Properties props = System.getProperties();
        try {
            props.load(new FileInputStream(new File("src//main//resources//email.properties")));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect(server, username, password);

        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
    }

public static String generateRandomKey()  {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String mykey =  "TestCaseExecution_" + currentDateTime;
        return mykey;
}

    //************* GET EMAIL PROPERTIES *******************

    public static String getEmailAddressFromProperties(){
        return System.getProperty("email.address");
    }

    public static String getEmailUsernameFromProperties() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Properties Prop = loadPropertiesFile( );
        return Encrypt.decryptedString(String.valueOf(Prop.get("username")), "Username");
    }

    public static String getEmailPasswordFromProperties() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Properties Prop = loadPropertiesFile( );
        return Encrypt.decryptedString((String) Prop.get("password"), "Password");
    }

    public static String getEmailServerFromProperties(){
        Properties Prop = loadPropertiesFile( );
        return (String) Prop.get("server");
    }

    public static String getEmailProtocolFromProperties(){
        return System.getProperty("email.protocol");
    }

    public static int getEmailPortFromProperties(){
        return Integer.parseInt(System.getProperty("email.port"));
    }


    //************* EMAIL ACTIONS *******************

    public void openEmail(Message message) throws Exception{
        message.getContent();
    }

    public int getNumberOfMessages() throws MessagingException {
        return folder.getMessageCount();
    }

    public int getNumberOfUnreadMessages()throws MessagingException {
        return folder.getUnreadMessageCount();
    }

    /**
     * Gets a message by its position in the folder. The earliest message is indexed at 1.
     */
    public Message getMessageByIndex(int index) throws MessagingException {
        return folder.getMessage(index);
    }

    public Message getLatestMessage() throws MessagingException{
        return getMessageByIndex(getNumberOfMessages());
    }

    /**
     * Gets all messages within the folder
     */
    public Message[] getAllMessages() throws MessagingException {
        return folder.getMessages();
    }

    /**
     * @param maxToGet maximum number of messages to get, starting from the latest. For example, enter 100 to get the last 100 messages received.
     */
    public Message[] getMessages(int maxToGet) throws MessagingException {
        Map<String, Integer> indices = getStartAndEndIndices(maxToGet);
        return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
    }

    /**
     * Searches for messages with a specific subject
     * @param subject Subject to search messages for
     * @param unreadOnly Indicate whether to only return matched messages that are unread
     * @param maxToSearch maximum number of messages to search, starting from the latest. For example, enter 100 to search through the last 100 messages.
     */
    public Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch) throws Exception{
        Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);

        Message messages[] = folder.search(
                new SubjectTerm(subject),
                folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));

        if(unreadOnly){
            List<Message> unreadMessages = new ArrayList<Message>();
            for (Message message : messages) {
                if(isMessageUnread(message)) {
                    unreadMessages.add(message);
                }
            }
            messages = unreadMessages.toArray(new Message[]{});
        }

        return messages;
    }

    /**
     * Returns HTML of the email's content
     */
    public String getMessageContent(Message message) throws Exception {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    /**
     * Returns all urls from an email message with the linkText specified
     */
    public List<String> getUrlsFromMessage(Message message, String linkText) throws Exception{
        String html = getMessageContent(message);
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(<a [^>]+>)"+linkText+"</a>").matcher(html);
        while (matcher.find()) {
            String aTag = matcher.group(1);
            allMatches.add(aTag.substring(aTag.indexOf("http"), aTag.indexOf("\">")));
        }
        return allMatches;
    }

    /**
     * Returns the count of all attachments from an email message with the linkText specified
     */
    public int getCountOfAttachmentsFromMessage(Message message)
            {
        int count = 0;
        try {
            Object object = message.getContent();
            if (object instanceof Multipart) {
                Multipart parts = (Multipart) object;
                for (int i = 0; i < parts.getCount(); ++i) {
                    MimeBodyPart part = (MimeBodyPart) parts.getBodyPart(i);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()))
                        ++count;
                }
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void saveAttachments(Message msg) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String currentDir = System.getProperty("user.dir");
        folderPath= currentDir + "/Reports/" + "TestCaseExecution_" + currentDateTime;
        new File(folderPath).mkdirs();
            if (msg.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) msg.getContent();

                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    String disposition = part.getDisposition();

                    if ((disposition != null) &&
                            ((disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                                    (disposition.equalsIgnoreCase(Part.INLINE))))) {
                        MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                        String fileName = mimeBodyPart.getFileName();
                        File fileToSave = new File(folderPath + "/" + fileName);
                        mimeBodyPart.saveFile(fileToSave);
                    }
                }
            }
            validateTextInPDF("ReactJS", folderPath, "Resume-React");
    }

    public void validateTextInPDF(String text, String folderPath, String pdfFileName) throws IOException {
        File actualPDFFile = new File(folderPath +"/" + pdfFileName+ ".pdf" );
        if(actualPDFFile.exists()) {
            FileInputStream Actual_PDF = new FileInputStream(new File(String.valueOf(actualPDFFile)));
            PDDocument Actual_PDF_1;
            Actual_PDF_1 = PDDocument.load(Actual_PDF);
            String pdfContent = new PDFTextStripper().getText(Actual_PDF_1);
            System.out.println(pdfContent);
            if(pdfContent.contains(text)) {
                System.out.println(text + " Exist in the resume" );
            } else {
                System.out.println(text + " does not Exist in the resume" );
            }
        } else
            System.out.println(pdfFileName + " PDF File Does not Exists");

    }

    private Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
        int endIndex = getNumberOfMessages();
        int startIndex = endIndex - max;

        //In event that maxToGet is greater than number of messages that exist
        if(startIndex < 1){
            startIndex = 1;
        }

        Map<String, Integer> indices = new HashMap<String, Integer>();
        indices.put("startIndex", startIndex);
        indices.put("endIndex", endIndex);

        return indices;
    }

    /**
     * Gets text from the end of a line.
     * In this example, the subject of the email is 'Authorization Code'
     * And the line to get the text from begins with 'Authorization code:'
     * Change these items to whatever you need for your email. This is only an example.
     */
    public String getAuthorizationCode() throws Exception {
        Message email = getMessagesBySubject("Authorization Code", true, 5)[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

        String line;
        String prefix = "Authorization code:";

        while ((line = reader.readLine()) != null) {
            if(line.startsWith(prefix)) {
                return line.substring(line.indexOf(":") + 1);
            }
        }
        return null;
    }

    /**
     * Gets one line of text
     * In this example, the subject of the email is 'Authorization Code'
     * And the line preceding the code begins with 'Authorization code:'
     * Change these items to whatever you need for your email. This is only an example.
     */
    public String getVerificationCode() throws Exception {
        Message email = getMessagesBySubject("Authorization Code", true, 5)[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("Authorization code:")) {
                return reader.readLine();
            }
        }
        return null;
    }



    //************* BOOLEAN METHODS *******************

    /**
     * Searches an email message for a specific string
     */
    public boolean isTextInMessage(Message message, String text) throws Exception {
        String content = getMessageContent(message);

        //Some Strings within the email have whitespace and some have break coding. Need to be the same.
        content = content.replace("&nbsp;", " ");
        return content.contains(text);
    }

    public boolean isMessageInFolder(String subject, boolean unreadOnly) throws Exception {
        int messagesFound = getMessagesBySubject(subject, unreadOnly, getNumberOfMessages()).length;
        return messagesFound > 0;
    }

    public boolean isMessageUnread(Message message) throws Exception {
        return !message.isSet(Flags.Flag.SEEN);
    }
}
