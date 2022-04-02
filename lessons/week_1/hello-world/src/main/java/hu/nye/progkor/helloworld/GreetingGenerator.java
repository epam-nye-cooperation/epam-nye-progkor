package hu.nye.progkor.helloworld;

import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.PseudoText;

public class GreetingGenerator {

    public String generateGreeting() {
        IRender render = new Render();
        IContextBuilder builder = render.newBuilder();
        builder.width(120).height(20);
        builder.element(new PseudoText("Hello, World!"));
        ICanvas canvas = render.render(builder.build());
        return canvas.getText();
    }

}
