package com.livehereandnow.ages.cs;

import java.io.*;
import java.net.*;

public class AgesAdmin {

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java -jar AgesClient.jar <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket agesSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(agesSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(agesSocket.getInputStream()));) {
            BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            boolean isAuto=false;
                
            while ((fromServer = in.readLine()) != null) {
//                System.out.print("server: " + fromServer + " ");
                System.out.println( fromServer + " ");
                
                if (fromServer.equals("Bye")) {
                    agesSocket.close();
                    break;
                }

                // 5/5/2014, by Mark
                //  not to rely on user's input
                //  automatically send simple request to server 
                //  in order to get updated status
                //admin is ready
                //http://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html
                if (fromServer.equals("admin is ready")) {
                    isAuto=true;
                    System.out.println(" ... auto mode");
//                    for (i
                }
                if (isAuto){
                        Thread.sleep(3000); // 2 sec to update
                        out.println(" .");
//                        System.out.println(".");
                        continue;
//                    }
                }

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                }

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName + " port=" + portNumber);
            System.exit(1);
        }
    }
}
