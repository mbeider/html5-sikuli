package martins.developer.world;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.Assert.fail;

public class FilterTest {
    private Screen screen;

    @BeforeClass
    public static void beforeClass() throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080/html5-sikuli-webapp/"));
        } else {
            fail();
        }
    }

    @Before
    public void before() {
        screen = new Screen();
    }

    @Test
    public void testGrayScale() throws FindFailed {
        screen.wait(getFullPath("originalImage.png"));
        screen.find(getFullPath("btnUndo_disabled.png"));
        screen.click(getFullPath("btnGrayscale.png"));
        screen.find(getPattern("grayscaleImage.png", 1.0f));
        screen.click(getFullPath("btnUndo_enabled.png"));
        screen.click(getPattern("originalImage.png", 1.0f));
    }

    private Pattern getPattern(String path, float similarity) {
        Pattern p = new Pattern(getFullPath(path));
        return p.similar(similarity);
    }

    private String getFullPath(String path) {
        return "src/test/resources/img/" + path;
    }
}
