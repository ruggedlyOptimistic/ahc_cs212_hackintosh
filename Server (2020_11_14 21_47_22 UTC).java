import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Server {
    private ArrayList<String> hashedPasswords = new ArrayList();

    public static String hash(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(in.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 255) + 256, 16).substring(1)); // base 16 password
            }

            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public Server() {
        this.hashedPasswords.add("bb7208bc9b5d7c04f1236a82a0093a5e33f40423d5ba8d4266f7092c3ba43b62"); // !
        this.hashedPasswords.add("8c1f1046219ddd216a023f792356ddf127fce372a72ec9b4cdac989ee5b0b455"); // 99
        this.hashedPasswords.add("707d62d7bbb3214c8cd08ff82f4d3783b7a21f7632f8172449e78c42331d6a43"); // Xz@
        this.hashedPasswords.add("0df59fd6c41cadd2220fd09fcd4d5979b7f64c572d9625a29489f2a6fdd34ecb");
        this.hashedPasswords.add("4dd71fb509f327b656627e2ecc6ecb556767875997024fc24409b6dd20dab5a0");
        this.hashedPasswords.add("b87f34ee3bce6d315dc44d26b6ccb29b1f54419015d48608d2608ba07bd10c70");
        this.hashedPasswords.add("670534ce798d662518011a66e39c46a6bf141c7a1baa0c71632c2057c0d67a13");
        this.hashedPasswords.add("d1e7352dc979e683b136269f2e74470bf119fd7de8738b445ff7b2df84f2dad4");
        this.hashedPasswords.add("ed31ab21f99bd33139df2d772055d45cf0ffeca4cbde0e9a6db453f85ec240d1");
        this.hashedPasswords.add("88e99b04779775011955500e39e5b771f034c43c450f8861302bd9a938a47d71");

        for(int x = 0; x < this.hashedPasswords.size(); ++x) {
            this.hashedPasswords.set(x, ((String)this.hashedPasswords.get(x)).toLowerCase());
        }

    }

    public void start(int portNumber) {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while(true) {
                while(true) {
                    try {
                        System.out.println("Waiting for password attempts");
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Someone is breaking in!");
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        String inputLine;
                        while((inputLine = in.readLine()) != null) {
                            out.println(this.hashedPasswords.contains(hash(inputLine)) ? "yes" : "no");
                            out.flush();
                        }
                    } catch (IOException var7) {
                        System.out.println(var7.getMessage());
                    }
                }
            }
        } catch (IOException var8) {
            System.out.println(var8.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start(58999);
    }
}