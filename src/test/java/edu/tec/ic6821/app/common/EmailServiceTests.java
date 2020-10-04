package edu.tec.ic6821.app.common;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import edu.tec.ic6821.app.common.EmailService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.mail.host = 127.0.0.1",
        "spring.mail.port = 3025"
})
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    private GreenMail mailServer;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Before
    public void setUp() throws Exception {
        mailServer = new GreenMail(new ServerSetup(Integer.parseInt(port), host, "smtp"));
        mailServer.start();
    }

    @Test
    public void shouldSendSampleMail() throws Exception {
        String to = "someone@somemail.com";
        String name = "Fulanito";

        emailService.sendSampleEmail(to, name);

        String expectedContent = "<h1>!Hola <span>Fulanito</span>!</h1>";

        MimeMessage[] receivedMessages = mailServer.getReceivedMessages();
        Assert.assertEquals(1, receivedMessages.length);
        System.out.println("receivedMessages[0] = " + receivedMessages[0].getContent());
        Assert.assertTrue(receivedMessages[0].getContent().toString().contains(expectedContent));
    }

    @After
    public void tearDown() throws Exception {
        mailServer.stop();
    }
}
