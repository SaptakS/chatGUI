package chat_server;

import java.io.*;
import java.net.*;
import java.security.Key;
import java.util.*;
import java.util.logging.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.*;
import org.json.simple.parser.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class server_frame extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;
   ArrayList<String> users;
   PrintWriter thisWriter;
   private static final String ALGO = "AES";
   private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

   public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, show = "Show", connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ta_chat.append("Received from client: " + message + "\n");
                    data = message.split(":");
                    
                    if (data.length == 5 && data[4].equals("Register"))
                    {
                        register(data[0], data[1], data[2]);
                    }
                    else if (data.length == 3 && data[2].equals("Login"))
                    {
                        login(data[0], data[1]);
                    } 
                    else 
                    {
                        message = decrypt(message);
                        data = message.split(":");
                        for (String token:data) 
                        {
                            ta_chat.append(token + "\n");
                        }



                        if (data[2].equals(connect)) 
                        {
                            tellEveryone((data[0] + ":" + data[1] + "to the Server:" + show));
                            userAdd(data[0]);
                        } 
                        /*else if (data.length == 5 && data[4].equals("Register"))
                        {
                            register(data[0], data[1], data[2]);
                        }
                        else if (data[2].equals("Login"))
                        {
                            login(data[0], data[1]);
                        }*/                    
                        else if (data[2].equals(disconnect)) 
                        {
                            tellEveryone((data[0] + ":has disconnected from the current server." + ":" + show));
                            userRemove(data[0]);
                        } 
                        else if (data[2].equals(chat)) 
                        {
                            tellEveryone(message);
                        } 
                        else 
                        {
                            ta_chat.append("No Conditions were met. \n");
                        }
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }

    public server_frame() 
    {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Server's frame");
        setName("server"); // NOI18N
        setResizable(false);

        ta_chat.setEditable(false);
        ta_chat.setBackground(new java.awt.Color(204, 255, 255));
        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Terminator Two", 0, 13)); // NOI18N
        ta_chat.setForeground(new java.awt.Color(0, 102, 102));
        ta_chat.setLineWrap(true);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setBackground(new java.awt.Color(0, 51, 51));
        b_start.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b_start.setForeground(new java.awt.Color(255, 255, 255));
        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setBackground(new java.awt.Color(204, 0, 0));
        b_end.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b_end.setForeground(new java.awt.Color(255, 255, 255));
        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_users.setBackground(new java.awt.Color(0, 51, 51));
        b_users.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b_users.setForeground(new java.awt.Color(255, 255, 255));
        b_users.setText("Online Users");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        b_clear.setBackground(new java.awt.Color(0, 51, 51));
        b_clear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b_clear.setForeground(new java.awt.Color(255, 255, 255));
        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Made by SaptakS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addGap(78, 78, 78)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_start)
                            .addComponent(b_users))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_clear)
                            .addComponent(b_end)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try 
        {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");
        
        ta_chat.setText("");
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Server started...\n");
    }//GEN-LAST:event_b_startActionPerformed

    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        ta_chat.append("\n Online users : \n");
        for (String current_user : users)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        
    }//GEN-LAST:event_b_usersActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new server_frame().setVisible(true);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                                thisWriter = writer;
				clientOutputStreams.add(writer);
                                //System.out.println(writer);
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				ta_chat.append("Receives a new connection from a client. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("ERROR!!Cannot making a connection. \n");
            }
        }
    }
    
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before " + name + " added. \n");
        users.add(name);
        ta_chat.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
 
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    public void login(String user, String pass) {
        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject data;
        JSONObject details;
        String message = "";
        try {
            
            FileReader fr = new FileReader("database.txt");
            if(fr.ready()){
                obj = parser.parse(fr);
                data = (JSONObject)obj;
                if(data.get(user) != null) {
                    details = (JSONObject)(data.get(user));                
                    if(pass.equals(details.get("pass"))){
                        message = "Success:1";
                    } else {
                        message = "Wrong Username/Password!! Try Again";                    
                    }
                } else {
                    message = "User is not registered";
                }
            }
            
            fr.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
            //message = "Error in Database";
        }
        
         try 
            {
                PrintWriter writer = (PrintWriter) thisWriter;
		writer.println(message);
		ta_chat.append("Sending Data: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("Error telling user about login. \n");
            }
    }
    
    public void register(String user, String name, String pass) {
        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject data;
        String message;
        try{
            
            FileReader fr = new FileReader("database.txt");
            if(fr.ready()){
                obj = parser.parse(fr);
                data = (JSONObject)obj;
            } else {
                data = new JSONObject();
            }
            JSONObject details = new JSONObject();            
            details.put("name", name);
            details.put("pass", pass);
            data.put(user,details);
            
            try{
                FileWriter fw = new FileWriter("database.txt");
                fw.write(data.toJSONString());
                fw.close();
                message = "Registered Successfully:1";
                //jOptionPane1.showMessageDialog(this, "Registered Successfully.");
                //log.newLogin();
                //dispose();
            } catch(Exception e){
                System.out.println(e);
                message = "Error!!";
                //jOptionPane1.showMessageDialog(this, e);
            }
        } catch(Exception e){
            System.out.println(e);
            message = "Error!!";
            //jOptionPane1.showMessageDialog(this, e);
        }
        
        try 
            {
                PrintWriter writer = (PrintWriter) thisWriter;
		writer.println(message);
		ta_chat.append("Sending Data: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("Error telling user about registration. \n");
            }
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(encrypt(message));
		ta_chat.append("Sending Message to clients: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ta_chat.append("Error telling everyone. \n");
            }
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
