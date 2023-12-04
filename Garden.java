package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Garden extends Application {

    private Pane root;
    private Circle circle;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        Scene scene = new Scene(root, 600, 400, Color.LIGHTBLUE);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                createCircle(event.getX(), event.getY());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                createRectangle(event.getX(), event.getY());
            }
        });

        scene.setOnMouseDragged(event -> {
            if (circle != null && event.isPrimaryButtonDown()) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        });
    }

    private void createCircle(double x, double y) {
        circle = new Circle(x, y, 20, Color.BLACK); // Default color of circle set to black
        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
        });
        root.getChildren().add(circle);

        circle.setOnMouseEntered(event -> {
            if (circle.getFill() != null) {
                Rectangle rectangle = findIntersectingRectangle(circle);
                if (rectangle != null) {
                    circle.setFill(rectangle.getStroke());
                }
            }
        });

        circle.setOnMouseExited(event -> {
            if (circle.getFill() != null) {
                circle.setFill(Color.BLACK); // Set circle color to black when mouse exits
            }
        });
    }

    private void createRectangle(double x, double y) {
        double rectangleWidth = 120;
        double rectangleHeight = 80;

        Rectangle rectangle = new Rectangle(x - rectangleWidth / 2, y - rectangleHeight / 2, rectangleWidth, rectangleHeight);
        rectangle.setStroke(Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
        rectangle.setFill(null);
        rectangle.setStrokeWidth(3);
        root.getChildren().add(rectangle);
    }

    private Rectangle findIntersectingRectangle(Circle circle) {
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) node;
                if (circle.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                    return rectangle;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
