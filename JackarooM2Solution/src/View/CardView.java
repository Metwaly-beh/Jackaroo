package View;

import controller.Controller;
import engine.Game;
import exception.InvalidCardException;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import model.card.Card;

public class CardView {
    private final ImageView view;
    private final Card card;
    private final Game game;
    private final Runnable onSelect;

    public CardView(ImageView view, Card card, Game game, Runnable onSelect) {
        this.view = view;
        this.card = card;
        this.game = game;
        this.onSelect = onSelect;
        setClickHandler();
    }

    private void setClickHandler() {
        view.setOnMouseClicked(e -> {
            if (game == null || card == null) return;
            try {
                game.deselectAll();
                game.selectCard(card);
                onSelect.run();
                Glow glow = new Glow(0.7);
                view.setEffect(glow);
            } catch (InvalidCardException ex) {
                Controller.displayAlert("Invalid Card", ex.getMessage());
            }
        });
    }
}
