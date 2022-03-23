package jeudelavie.miscellaneous;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;

public class ComputeService extends Service<int[][]> {
    private BoardController boardController;
    private FrameController frameController;

    public ComputeService(BoardController boardController, FrameController frameController) {
        super();
        this.boardController = boardController;
        this.frameController = frameController;
        this.setOnSucceeded(workerStateEvent -> {
            System.out.println("success");
            System.out.println(workerStateEvent);

            //this.restart();
        });
        //this.stateProperty().addListener(new ComputeListener(this, boardController));
    }

    @Override
    protected Task<int[][]> createTask() {
        return new Task<int[][]>() {
            @Override
            protected int[][] call() throws Exception {
                System.out.println("computing");
                return boardController.computeNextGeneration(frameController.getFrameModel());
            }
        };
    }
}
