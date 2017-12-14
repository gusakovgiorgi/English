package company.self.development.rememberenglishexample.add_to_card;

/**
 * Created by Giorgi on 12/14/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import company.self.development.rememberenglishexample.R;

/**
 * A DialogFragment that implements the Full-screen dialog pattern defined in
 * <a href="https://material.io/guidelines/components/dialogs.html#dialogs-full-screen-dialogs">the Material Design guidelines</a>
 */
public class AddToCardDialogFragment extends DialogFragment {
    /**
     * Callback that will be called when the dialog is closed due to a confirm button click.
     */
    public interface OnSaveListener {
        /**
         * Called when dialog is closed due to a confirm button click.
         *
         * @param result optional bundle with result data
         */
        void onSave(@Nullable Bundle result);
    }

    /**
     * Callback that will be called when the dialog is closed due a discard button click.
     */
    public interface OnDiscardListener {
        /**
         * Called when the dialog is closed due a discard button click.
         */
        void onDiscard();
    }

    /**
     * Callback that will be called when the dialog is closed due to an extra action (different from confirm or discard).
     */
    public interface OnDiscardFromExtraActionListener {
        /**
         * Called when the dialog is closed due to an extra action.
         *
         * @param actionId menu item id to identify the action
         * @param result   optional bundle with result data
         */
        void onDiscardFromExtraAction(int actionId, @Nullable Bundle result);
    }

    private static final String BUILDER_TITLE = "BUILDER_TITLE";
    private static final String BUILDER_SAVE_BUTTON = "BUILDER_SAVE_BUTTON";


    private static AddToCardDialogFragment newInstance(Builder builder) {
        AddToCardDialogFragment f = new AddToCardDialogFragment();
        f.setArguments(mapBuilderToArguments(builder));
        f.setOnSaveListener(builder.onSaveListener);
        f.setOnDiscardListener(builder.onDiscardListener);
        f.setOnDiscardFromExtraActionListener(builder.onDiscardFromActionListener);
        return f;
    }

    private static Bundle mapBuilderToArguments(Builder builder) {
        Bundle builderData = new Bundle();
        builderData.putString(BUILDER_TITLE, builder.title);
        builderData.putString(BUILDER_SAVE_BUTTON, builder.saveButton);
        return builderData;
    }

    private String title;
    private String saveButton;
    private static final int NO_EXTRA_ITEMS = 0;
    private MenuItem itemConfirmButton;

    private OnSaveListener onSaveListener;
    private OnDiscardListener onDiscardListener;
    private OnDiscardFromExtraActionListener onDiscardFromExtraActionListener;


    /**
     * Sets the callback that will be called when the dialog is closed due to a confirm button click.
     *
     * @param onSaveListener
     */
    public void setOnSaveListener(@Nullable OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    /**
     * Sets the callback that will be called when the dialog is closed due a discard button click.
     *
     * @param onDiscardListener
     */
    public void setOnDiscardListener(@Nullable OnDiscardListener onDiscardListener) {
        this.onDiscardListener = onDiscardListener;
    }

    /**
     * Sets the callback that will be called when the dialog is closed due a extra action button click.
     *
     * @param onDiscardFromExtraActionListener
     */
    public void setOnDiscardFromExtraActionListener(@Nullable OnDiscardFromExtraActionListener onDiscardFromExtraActionListener) {
        this.onDiscardFromExtraActionListener = onDiscardFromExtraActionListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBuilderArguments();


        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_to_card, container, false);

        initToolbar(view);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        return view;
    }


    private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Drawable closeDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_24px);
        tintToolbarButton(toolbar, closeDrawable);

        toolbar.setNavigationIcon(closeDrawable);
        toolbar.setNavigationOnClickListener(v -> discard());

        toolbar.setTitle(title);

        Menu menu = toolbar.getMenu();

        final int itemConfirmButtonId = 1;
        itemConfirmButton = menu.add(0, itemConfirmButtonId, 0, this.saveButton);
        itemConfirmButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemConfirmButton.setEnabled(false);
        itemConfirmButton.setOnMenuItemClickListener(
                item -> {
                    if (item.getItemId() == itemConfirmButtonId) {
                        confirm(null);
                        return true;
                    } else
                        return false;
                });

    }

    // TODO: 12/14/2017 try without it
    private void tintToolbarButton(Toolbar toolbar, Drawable buttonDrawable) {
        int[] colorAttrs = new int[]{R.attr.colorControlNormal};
        TypedArray a = toolbar.getContext().obtainStyledAttributes(colorAttrs);
        int color = a.getColor(0, -1);
        a.recycle();

        DrawableCompat.setTint(DrawableCompat.wrap(buttonDrawable), color);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initBuilderArguments();

        Dialog dialog = new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                discard();
            }
        };

        return dialog;
    }


    private void confirm(Bundle result) {
        if (onSaveListener != null) {
            onSaveListener.onSave(result);
        }
        dismiss();
    }

    private void discard() {
        if (onDiscardListener != null) {
            onDiscardListener.onDiscard();
        }
        dismiss();
    }

    private void discardFromExtraAction(int actionId, Bundle result) {
        if (onDiscardFromExtraActionListener != null) {
            onDiscardFromExtraActionListener.onDiscardFromExtraAction(actionId, result);
        }
        dismiss();
    }

    private void initBuilderArguments() {
        Bundle builderData = getArguments();
        title = builderData.getString(BUILDER_TITLE);
        saveButton = builderData.getString(BUILDER_SAVE_BUTTON);
    }


    public static class Builder {
        private Context context;
        private String title;
        private String saveButton;
        private Bundle contentArguments;
        private OnSaveListener onSaveListener;
        private OnDiscardListener onDiscardListener;
        private OnDiscardFromExtraActionListener onDiscardFromActionListener;


        /**
         * Builder to construct a {@link AddToCardDialogFragment}.
         *
         * @param context
         */
        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * Creates a {@link AddToCardDialogFragment} with the provided parameters.
         *
         * @return the created instance of {@link AddToCardDialogFragment}
         */
        public AddToCardDialogFragment build() {
            return AddToCardDialogFragment.newInstance(this);
        }

        public Builder setTitle(@NonNull String text) {
            this.title = text;
            return this;
        }

        public Builder setTitle(@StringRes int textResId) {
            this.title = context.getString(textResId);
            return this;
        }

        public Builder setSaveButton(@NonNull String text) {
            this.saveButton = text;
            return this;
        }

        public Builder setConfirmButton(@StringRes int textResId) {
            return setSaveButton(context.getString(textResId));
        }


        /**
         * Sets the callback that will be called when the dialog is closed due to a confirm button click.
         *
         * @param onSaveListener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnSaveListener(@Nullable OnSaveListener onSaveListener) {
            this.onSaveListener = onSaveListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is closed due a discard button click.
         *
         * @param onDiscardListener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDiscardListener(@Nullable OnDiscardListener onDiscardListener) {
            this.onDiscardListener = onDiscardListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is closed due a extra action button click.
         *
         * @param onDiscardFromActionListener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDiscardFromActionListener(@Nullable OnDiscardFromExtraActionListener onDiscardFromActionListener) {
            this.onDiscardFromActionListener = onDiscardFromActionListener;
            return this;
        }
    }
}