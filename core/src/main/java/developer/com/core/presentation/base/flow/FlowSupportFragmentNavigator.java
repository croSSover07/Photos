package developer.com.core.presentation.base.flow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

public abstract class FlowSupportFragmentNavigator implements Navigator, OnBackStackChangedListener {

    protected FragmentManager manager;
    protected int containerId;
    protected LinkedList<String> localStackCopy;

    /**
     * Creates SupportFragmentNavigator.
     *
     * @param fragmentManager support fragment manager
     * @param containerId     id of the fragments container layout
     */
    public FlowSupportFragmentNavigator(FragmentManager fragmentManager, int containerId) {
        this.manager = fragmentManager;
        this.containerId = containerId;

        manager.addOnBackStackChangedListener(this);
    }

    /**
     * Override this method to setup custom fragment transaction animation.
     *
     * @param command             current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param currentFragment     current fragment in container
     *                            (for {@link Replace} command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    protected void setupFragmentTransactionAnimation(Command command,
                                                     Fragment currentFragment,
                                                     Fragment nextFragment,
                                                     FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void applyCommands(Command[] commands) {
        manager.executePendingTransactions();

        //copy stack before apply commands
        copyStackToLocal();

        for (Command command : commands) {
            applyCommand(command);
        }
    }

    private void copyStackToLocal() {
        localStackCopy = new LinkedList<>();

        final int stackSize = manager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(manager.getBackStackEntryAt(i).getName());
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected void applyCommand(Command command) {
        if (command instanceof Forward) {
            forward((Forward) command);
        } else if (command instanceof Back) {
            back();
        } else if (command instanceof Replace) {
            replace((Replace) command);
        } else if (command instanceof BackTo) {
            backTo((BackTo) command);
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    /**
     * Performs {@link Forward} command transition
     */
    protected void forward(Forward command) {
        Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());

        if (fragment == null) {
            unknownScreen(command);
            return;
        }

        final String tag = getTag(command.getTransitionData());

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        setupFragmentTransactionAnimation(
                command,
                manager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        );

        fragmentTransaction
                .replace(containerId, fragment, tag)
                .addToBackStack(command.getScreenKey())
                .commit();
        localStackCopy.add(command.getScreenKey());
    }

    /**
     * Performs {@link Back} command transition
     */
    protected void back() {
        if (localStackCopy.size() > 0) {
            manager.popBackStack();
            localStackCopy.pop();
        } else {
            exit();
        }
    }

    /**
     * Performs {@link Replace} command transition
     */
    protected void replace(Replace command) {
        Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());

        if (fragment == null) {
            unknownScreen(command);
            return;
        }

        final String tag = getTag(command.getTransitionData());
        replacingTag(tag);

        if (localStackCopy.size() > 0) {
            manager.popBackStack();
            localStackCopy.pop();

            FragmentTransaction fragmentTransaction = manager.beginTransaction();

            setupFragmentTransactionAnimation(
                    command,
                    manager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment, tag)
                    .addToBackStack(command.getScreenKey())
                    .commit();
            localStackCopy.add(command.getScreenKey());

        } else {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

            setupFragmentTransactionAnimation(
                    command,
                    manager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment, tag)
                    .commit();
        }
    }

    /**
     * Performs {@link BackTo} command transition
     */
    protected void backTo(BackTo command) {
        String key = command.getScreenKey();

        if (key == null) {
            backToRoot();

        } else {
            int index = localStackCopy.indexOf(key);
            int size = localStackCopy.size();

            if (index != -1) {
                for (int i = 1; i < size - index; i++) {
                    localStackCopy.pop();
                }
                manager.popBackStack(key, 0);
            } else {
                backToUnexisting(command.getScreenKey());
            }
        }
    }

    protected void backToRoot() {
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        localStackCopy.clear();
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     *
     * @param screenKey screen key
     * @param data      initialization data
     * @return instantiated fragment for the passed screen key
     */
    protected abstract Fragment createFragment(String screenKey, Object data);

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract void showSystemMessage(String message);

    /**
     * Called when we try to back from the root.
     */
    protected abstract void exit();

    /**
     * Called when we tried to back to some specific screen (via {@link BackTo} command),
     * but didn't found it.
     *
     * @param screenKey screen key
     */
    protected void backToUnexisting(String screenKey) {
        backToRoot();
    }

    @Nullable
    protected String getTag(Object data) {
        return null;
    }

    protected void replacingTag(@Nullable String tag) {
    }

    /**
     * Called if we can't create a screen.
     */
    protected void unknownScreen(Command command) {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }

    @Override
    public void onBackStackChanged() {
    }
}