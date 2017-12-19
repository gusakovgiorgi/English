package company.self.development.rememberenglishexample.add_to_card;

/**
 * Created by Giorgi on 12/14/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

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
    private static final String BUILDER_ORIGINAL_WORD="BUILDER_ORIGINAL_WORD";
    private static final String BUILDER_TRANSCRIPTION_WORD="BUILDER_TRANSCRIPTION_WORD";
    private static final String BUILDER_TRANSLATION_WORD = "BUILDER_TRANSLATION_WORD";
    private static final String BUILDER_EXAMPLE_WORD = "BUILDER_EXAMPLE_WORD";


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
        builderData.putString(BUILDER_ORIGINAL_WORD, builder.originalWord);
        builderData.putString(BUILDER_TRANSCRIPTION_WORD, builder.transcription);
        builderData.putString(BUILDER_TRANSLATION_WORD, builder.translation);
        builderData.putString(BUILDER_EXAMPLE_WORD, builder.example);
        return builderData;
    }

    private String title;
    private String saveButton;
    private static final int NO_EXTRA_ITEMS = 0;
    private MenuItem itemConfirmButton;
    private String originalWord;
    private String transcriptionWord;
    private String translationWord;
    private String exampleWord;

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
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_add_to_card, container, false);
        initToolbar(view);
        fillViews(view);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        return view;
    }

    private void fillViews(ViewGroup view) {

        TextView originalWordTv=view.findViewById(R.id.original_word_field);
        TextView transcriptionWordTv=view.findViewById(R.id.transcription_word_field);
        TextView translationWordTv=view.findViewById(R.id.translation_word_field);
        TextView exampleWordTv=view.findViewById(R.id.example_word_field);

        TextInputLayout originalWordLayout=view.findViewById(R.id.original_word_layout);
        TextInputLayout transcriptionWordLayout=view.findViewById(R.id.transcription_word_layout);
        TextInputLayout translationWordLayout=view.findViewById(R.id.translation_word_layout);
        TextInputLayout exampleLayout=view.findViewById(R.id.example_word_layout);

        //disable animations if setted text is not empty
        if (originalWord!=null){
            originalWordLayout.setHintAnimationEnabled(false);
            originalWordTv.setText(originalWord);
            originalWordTv.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !originalWordLayout.isHintAnimationEnabled())originalWordLayout.setHintAnimationEnabled(true);
            });
        }
        if (transcriptionWord !=null){
            transcriptionWordLayout.setHintAnimationEnabled(false);
            transcriptionWordTv.setText(transcriptionWord);
            transcriptionWordTv.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !transcriptionWordLayout.isHintAnimationEnabled())transcriptionWordLayout.setHintAnimationEnabled(true);
            });
        }

        if (translationWord !=null){
            translationWordLayout.setHintAnimationEnabled(false);
            translationWordTv.setText(translationWord);;
            translationWordTv.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !translationWordLayout.isHintAnimationEnabled())translationWordLayout.setHintAnimationEnabled(true);
            });
        }

        if (exampleWord !=null){
            exampleLayout.setHintAnimationEnabled(false);
            exampleWordTv.setText(exampleWord);
            exampleWordTv.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !exampleLayout.isHintAnimationEnabled())exampleLayout.setHintAnimationEnabled(true);
            });
        }
    }


    private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Drawable closeDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_24px);
        Drawable doneDrawable =  ContextCompat.getDrawable(getContext(), R.drawable.ic_done_black_24px);

        tintToolbarButton(toolbar, closeDrawable);
        tintToolbarButton(toolbar, doneDrawable);

        toolbar.setNavigationIcon(closeDrawable);
        toolbar.setNavigationOnClickListener(v -> discard());

        toolbar.setTitle(title);

        Menu menu = toolbar.getMenu();

        final int itemConfirmButtonId = 1;
        itemConfirmButton = menu.add(0, itemConfirmButtonId, 0, this.saveButton);
        itemConfirmButton.setIcon(getResources().getDrawable(R.drawable.ic_done_black_24px));
        itemConfirmButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
        originalWord=builderData.getString(BUILDER_ORIGINAL_WORD);
        transcriptionWord =builderData.getString(BUILDER_TRANSCRIPTION_WORD);
        translationWord =builderData.getString(BUILDER_TRANSLATION_WORD);
        exampleWord =builderData.getString(BUILDER_EXAMPLE_WORD);
    }


    public static class Builder {
        private Context context;
        private String title;
        private String originalWord;
        private String transcription;
        private String translation;
        private String example;
        private String saveButton;
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

        /**
         * set the word wich was translated
         *
         * @param originalWord
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOriginalWord(String originalWord) {
            this.originalWord = originalWord;
            return this;
        }

        /**
         * set the transcriptionWord of translated word
         * @param transcription
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTranscription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        /**
         * set the translationWord of translated word
         *
         * @param translation
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTranslation(String translation) {
            this.translation = translation;
            return this;
        }

        /**
         * set the exampleWord with original word
         * @param example
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setExample(String example) {
            this.example = example;
            return this;
        }
    }
}