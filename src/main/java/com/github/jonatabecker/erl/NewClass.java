package com.github.jonatabecker.erl;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author JonataBecker
 */
public class NewClass {

    private OtpSelf client;
    private OtpPeer server;
    private OtpConnection connection;

    public NewClass() throws IOException, UnknownHostException, OtpAuthException {
        client = new OtpSelf("client", "batman");
        server = new OtpPeer("server@jonatabecker-670Z5E");
        connection = client.connect(server);

    }

    public void run() throws Exception {
        connection.sendRPC("translator", "translate", withArgs("friend", "Spanish"));
        while (true) {
            OtpErlangObject response = connection.receiveMsg(100).getMsg();
            System.out.println(response.toString());
        }
    }

    private OtpErlangObject[] withArgs(String word, String language) {
        return new OtpErlangObject[]{
            new OtpErlangAtom(word),
            new OtpErlangAtom(language)
        };
    }

    public static void main(String[] args) throws Exception {
        NewClass newClass = new NewClass();

        newClass.run();

    }

}
