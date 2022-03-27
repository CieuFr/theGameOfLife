package jeudelavie.miscellaneous;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import jeudelavie.controleur.BoardController;
import jeudelavie.controleur.FrameController;

public class ComputeService extends ScheduledService<int[][]> {
    private final BoardController boardController;
    private final FrameController frameController;

    public ComputeService(BoardController boardController, FrameController frameController) {
        super();
        this.boardController = boardController;
        this.frameController = frameController;

        this.setOnSucceeded(workerStateEvent -> {
            System.out.println("success");
            System.out.println(workerStateEvent);
        });
    }

    @Override
    protected Task<int[][]> createTask() {
        return new Task<>() {
            @Override
            protected int[][] call() throws Exception {
                System.out.println("computing");
                return boardController.computeNextGeneration(frameController.getFrameModel());
            }
        };
    }
}
