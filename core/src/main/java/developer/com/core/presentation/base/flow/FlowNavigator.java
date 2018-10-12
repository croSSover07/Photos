package developer.com.core.presentation.base.flow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.LinkedList;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Navigator implementation for launch fragments and activities.<br>
 * Feature {@link BackTo} works only for fragments.<br>
 * Recommendation: most useful for Single-Activity application.
 */
public class FlowNavigator implements Navigator {

    private final Activity activity;
    private final FragmentManager fragmentManager;
    private final int containerId;
    private LinkedList<String> localStackCopy;

    public FlowNavigator(FragmentActivity activity, int containerId) {
        this(activity, activity.getSupportFragmentManager(), containerId);
    }

    public FlowNavigator(FragmentActivity activity, FragmentManager fragmentManager, int containerId) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void applyCommands(Command[] commands) {
        fragmentManager.executePendingTransactions();

        //copy stack before apply commands
        copyStackToLocal();

        for (Command command : commands) {
            applyCommand(command);
        }
    }

    private void copyStackToLocal() {
        localStackCopy = new LinkedList<>();

        final int stackSize = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).getName());
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected void applyCommand(Command command) {
        if (command instanceof Forward) {
            activityForward((Forward) command);

        } else if (command instanceof Replace) {
            activityReplace((Replace) command);

        } else if (command instanceof BackTo) {
            backTo((BackTo) command);

        } else if (command instanceof Back) {
            fragmentBack();
        }
    }


    protected void activityForward(Forward command) {
        final Screen screen = command.getScreen();
        final Intent intent = createActivityIntent(screen);

        // Start activity
        if (intent != null) {
            Bundle options = createStartActivityOptions(command, intent);
            checkAndStartActivity(screen, intent, options);

        } else {
            fragmentForward(command);
        }
    }

    protected void fragmentForward(Forward command) {
        final Screen screen = command.getScreen();
        final Fragment fragment = createFragment(screen);
        if (!fragmentCreated(fragment, screen)) return;

        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        setupFragmentTransaction(
                command, fragmentManager.findFragmentById(containerId),
                fragment, transaction);

        transaction
                .replace(containerId, fragment)
                .addToBackStack(screen.getScreenKey())
                .commit();

        localStackCopy.add(screen.getScreenKey());
    }

    protected void fragmentBack() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();

        } else {
            activityBack();
        }
    }

    protected void activityBack() {
        activity.finish();
    }

    protected void activityReplace(Replace command) {
        final Screen screen = command.getScreen();
        final Intent intent = createActivityIntent(screen);

        // Replace activity
        if (intent != null) {
            final Bundle options = createStartActivityOptions(command, intent);
            checkAndStartActivity(screen, intent, options);
            activity.finish();
        } else {
            fragmentReplace(command);
        }
    }

    protected void fragmentReplace(Replace command) {
        final Screen screen = command.getScreen();
        final Fragment fragment = createFragment(screen);
        if (!fragmentCreated(fragment, screen)) return;

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();

            setupFragmentTransaction(
                    command, fragmentManager.findFragmentById(containerId),
                    fragment, transaction);

            transaction
                    .replace(containerId, fragment)
                    .addToBackStack(screen.getScreenKey())
                    .commit();

            localStackCopy.add(screen.getScreenKey());

        } else {
            setupFragmentTransaction(
                    command, fragmentManager.findFragmentById(containerId),
                    fragment, transaction);

            transaction
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    /**
     * Performs {@link BackTo} command transition
     */
    protected void backTo(BackTo command) {
        if (command.getScreen() != null) {
            final String key = command.getScreen().getScreenKey();
            final int index = localStackCopy.indexOf(key);
            final int size = localStackCopy.size();

            if (index != -1) {
                for (int i = 1; i < size - index; i++) {
                    localStackCopy.removeLast();
                }
                fragmentManager.popBackStack(key, 0);
            } else {
                backToUnexisting(command.getScreen());
            }

        } else {
            backToRoot();
        }
    }

    private void backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        localStackCopy.clear();
    }

    /**
     * Override this method to setup fragment transaction {@link FragmentTransaction}.
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command         current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param currentFragment current fragment in container
     *                        (for {@link Replace} command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment    next screen fragment
     * @param transaction     fragment transaction
     */
    protected void setupFragmentTransaction(Command command, Fragment currentFragment,
                                            Fragment nextFragment, FragmentTransaction transaction) {
    }

    /**
     * Override this method to create option for start activity
     *
     * @param command current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param intent  activity intent
     * @return transition options
     */
    protected Bundle createStartActivityOptions(Command command, Intent intent) {
        return null;
    }

    private void checkAndStartActivity(Screen screen, Intent activityIntent, Bundle options) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(activityIntent, options);
        } else {
            unexistingActivity(screen, activityIntent);
        }
    }

    /**
     * Called when there is no activity to open {@code screenKey}.
     *
     * @param screen screen
     * @param intent intent passed to start Activity for the {@code screenKey}
     */
    protected void unexistingActivity(Screen screen, Intent intent) {
        // Do nothing by default
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     *
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    protected Fragment createFragment(Screen screen) {
        return null;
    }

    protected Intent createActivityIntent(Screen screen) {
        return null;
    }

    /**
     * Called when we tried to fragmentBack to some specific screen (via {@link BackTo} command),
     * but didn't found it.
     *
     * @param screen screen
     */
    protected void backToUnexisting(Screen screen) {
        backToRoot();
    }

    protected void errorWhileCreatingScreen(Screen screen) {
        throw new RuntimeException("Can't create a screen: " + screen.getScreenKey());
    }

    private boolean fragmentCreated(Fragment fragment, Screen screen) {
        final boolean created = fragment != null;
        if (!created) errorWhileCreatingScreen(screen);
        return created;
    }
}