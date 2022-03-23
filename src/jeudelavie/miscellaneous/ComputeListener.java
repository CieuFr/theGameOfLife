package jeudelavie.miscellaneous;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import jeudelavie.controleur.BoardController;

import java.util.Arrays;

public class ComputeListener implements ChangeListener<Worker.State> {
    private ComputeService computeService;
    private BoardController boardController;

    public ComputeListener(ComputeService computeService, BoardController boardController) {
        this.computeService = computeService;
        this.boardController = boardController;
    }


    @Override
    public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
        switch (newState){
            case SUCCEEDED:
                System.out.println("SUCCEEDED");

                System.out.println(Arrays.deepToString(computeService.getValue()));
                boardController.getBoardModel().setBoard(computeService.getValue());
                computeService.reset();
                break;
            case CANCELLED:
                break;
            case FAILED:
                break;
            case READY:
                System.out.println("READY");

                break;
            case RUNNING:
                break;
            case SCHEDULED:
                break;
        }
    }
}
