package martins.developer.world;

import martins.developer.world.jpa.web.HomeBackingBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static junit.framework.Assert.fail;

@RunWith(Arquillian.class)
public class FilterTest {
    public static final String WEBAPP_SRC = "src/main/webapp";
    @ArquillianResource
    URL deploymentURL;
    private Screen screen;

    @Before
    public void before() throws URISyntaxException, IOException {
        screen = new Screen();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(deploymentURL.toURI());
        } else {
            fail();
        }
    }

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "html5-sikuli-webapp.war")
                .addClasses(HomeBackingBean.class)
                .addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "resources/css/style.css"), "resources/css/style.css")
                .addAsWebResource(new File(WEBAPP_SRC, "resources/images/rom.jpg"), "resources/images/rom.jpg")
                .addAsWebResource(new File(WEBAPP_SRC, "resources/js/html5Sikuli.js"), "resources/js/html5Sikuli.js")
                .addAsWebResource(new File(WEBAPP_SRC, "resources/js/jquery-2.0.3.js"), "resources/js/jquery-2.0.3.js")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
    }

    @Test
    @RunAsClient
    public void testGrayScale() throws FindFailed {
        screen.wait(getPattern("originalImage.png", 0.6f), 100);
        screen.find(getFullPath("btnUndo_disabled.png"));
        screen.click(getFullPath("btnGrayscale.png"));
        screen.find(getPattern("grayscaleImage.png", 0.9f));
        screen.click(getFullPath("btnUndo_enabled.png"));
        screen.click(getPattern("originalImage.png", 0.9f));
    }

    private Pattern getPattern(String path, float similarity) {
        Pattern p = new Pattern(getFullPath(path));
        return p.similar(similarity);
    }

    private String getFullPath(String path) {
        return "src/test/resources/img/" + path;
    }
}
