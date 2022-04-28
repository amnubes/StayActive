package amcode.consolui.controller;

import amcode.application.common.enums.Display;
import amcode.application.common.interfaces.Controller;
import amcode.application.common.interfaces.Displayable;
import amcode.application.common.models.DisplayScreen;
import amcode.consolui.common.mapping.OnExerciseViewMapping;
import amcode.consolui.common.services.CurrentUserService;
import amcode.consolui.model.ExerciseViewModel;
import amcode.consolui.model.NotificationViewModel;
import amcode.consolui.view.OnExerciseView;
import amcode.consolui.view.form.input.InputField;
import amcode.domain.entity.*;
import amcode.domain.enums.Level;
import amcode.domain.services.alertexercise.AlertExercise;
import amcode.domain.services.alertexercise.AlertExerciseCreatorA;
import amcode.domain.services.intervalexerciselevel.ExerciseLevelCreatorB;
import amcode.domain.services.intervalexerciselevel.IntervalExerciseLevel;

import java.util.HashMap;
import java.util.List;

public class NotificationController implements Controller<NotificationViewModel> {
    @Override
    public DisplayScreen execute(HashMap<String, InputField> inputField, NotificationViewModel model) {
        Displayable displayable;
        Display display;

        // get alert
        User loggedInUser = CurrentUserService.getLoggedInUser();
        int chosenAlertIndex = (int) inputField.get("chosenAlertIndex").getValue();
        Alert alert = loggedInUser.getAlertList().get(chosenAlertIndex);

        // get interval
        Interval interval = alert.getInterval();

        // get last notification
        int size = interval.getNotificationList().size();
        Notification notification = interval.getNotificationList().get(size - 1);

        // set notification to accepted
        notification.setAccepted(true);

        // get exercise level
        List<Level> levelList = new IntervalExerciseLevel(new ExerciseLevelCreatorB(loggedInUser)).
                getExerciseDifficulty(interval);

        // get exercise
        Exercise exercise = new AlertExercise(new AlertExerciseCreatorA()).getExerciseOnNotification(alert, levelList);

        // map exercise to viewModel
        ExerciseViewModel exerciseViewModel = new OnExerciseViewMapping().mapFrom(exercise);

        // show display
        displayable = new OnExerciseView(exerciseViewModel, "Todo exericse");
        display = Display.MAIN;

        return new DisplayScreen(displayable, display);
    }
}
