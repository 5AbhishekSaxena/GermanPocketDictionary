package com.abhishek.germanPocketDictionary.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.activity.SearchResultActivity;
import com.abhishek.germanPocketDictionary.interfaces.OnWordClickListener;
import com.abhishek.germanPocketDictionary.model.Word;
import com.abhishek.germanPocketDictionary.fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.R;

import java.util.List;

import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_NOUNS;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_OPPOSITE;
import static com.abhishek.germanPocketDictionary.utilities.Constants.API_KEYS.CATEGORY_VERBS;
import static com.abhishek.germanPocketDictionary.utilities.Utils.getWordsUsingCategory;

import com.abhishek.germanPocketDictionary.utilities.Constants;

/**
 * @author Abhishek Saxena
 * @since 22-06-2019 04:14
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Context context;
    private WordsFragment fragment;

    private String mFragmentType;

    private boolean isActivity;

    private List<Word> words;
    private OnWordClickListener onWordClickListener;

    public WordAdapter(WordsFragment fragment, Context context, String fragmentType,
                       OnWordClickListener onWordClickListener) {
        this.context = context;
        this.words = getWordsUsingCategory(context, fragmentType);
        this.fragment = fragment;
        mFragmentType = fragmentType;
        this.onWordClickListener = onWordClickListener;
    }

    public WordAdapter(Activity context, List<Word> words,
                       OnWordClickListener onWordClickListener) {
        this.context = context;
        isActivity = true;
        this.words = words;
        this.onWordClickListener = onWordClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(mFragmentType.equals(Constants.API_KEYS.CATEGORY_OPPOSITE) ? R.layout.opposite_words : R.layout.list_item, parent, false);
        return new ViewHolder(view, isActivity, onWordClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word currentWord = words.get(position);
        if (currentWord != null) {

            if (!mFragmentType.equals(CATEGORY_OPPOSITE)) {
                holder.germanTextView.setText(currentWord.getGermanTranslation());
                holder.englishTextView.setText(currentWord.getEnglishTranslation());
            }

            if (!isActivity) {
                if (currentWord.hasPlural()) {
                    viewPlural(holder, currentWord);
                }

                if (currentWord.hasOpposite() && !mFragmentType.equals(CATEGORY_OPPOSITE)) {
                    viewOpposite(holder, currentWord);
                }

                if (mFragmentType.equals(CATEGORY_NOUNS)) {
                    holder.arrowImageView.setVisibility(View.VISIBLE);
                    if (fragment.selectedNoun == position) {
                        holder.expandableView.setVisibility(View.VISIBLE);
                        holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                    } else {
                        holder.expandableView.setVisibility(View.GONE);
                        holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                    }
                }

                if (mFragmentType.equals(CATEGORY_VERBS)) {
                    holder.arrowImageView.setVisibility(View.VISIBLE);
                    if (fragment.selectedVerb == position) {
                        holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                        holder.expandableView.setVisibility(View.VISIBLE);
                        viewRootWord(holder, currentWord);

                        if (currentWord.hasPartizip()) {
                            viewPartizip(holder, currentWord);
                        }
                    } else {
                        holder.expandableView.setVisibility(View.GONE);
                        holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                    }
                }

                if (mFragmentType.equals(CATEGORY_OPPOSITE)) {
                    holder.germanTextViewOne.setText(currentWord.getGermanTranslation());
                    holder.englishTextViewOne.setText(currentWord.getEnglishTranslation());

                    holder.germanTextViewTwo.setText(currentWord.getGermanOpposite());
                    holder.englishTextViewTwo.setText(currentWord.getGermanOppositeMeaning());
                }
            } else {
                holder.expandableView.setVisibility(View.GONE);

                if (holder.expandableView != null) {
                    if (!SearchResultActivity.searchState) {
                        if (currentWord.getCategory().equals(CATEGORY_NOUNS)) {
                            holder.expandableView.setVisibility(View.VISIBLE);


                            if (currentWord.hasPlural())
                                viewPlural(holder, currentWord);
                        }
                        if (currentWord.getCategory().equals(CATEGORY_VERBS)) {
                            holder.expandableView.setVisibility(View.VISIBLE);
                            viewRootWord(holder, currentWord);
                            viewPartizip(holder, currentWord);
                        }
                        if (currentWord.hasOpposite() && !mFragmentType.equals(CATEGORY_OPPOSITE)) {
                            holder.expandableView.setVisibility(View.VISIBLE);
                            viewOpposite(holder, currentWord);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return words == null ? 0 : words.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView germanTextView;
        private TextView englishTextView;
        private TextView pluralLabelTextView;
        private TextView pluralTextView;
        private TextView rootWordLabelTextView;
        private TextView rootWord;
        private TextView partizipLabelTextView;
        private TextView partizipTextView;
        private TextView helpingVerbLabelTextView;
        private TextView helpingVerbTextView;
        private TextView oppositeLabelTextView;
        private TextView oppositeTextView;

        private ImageView arrowImageView;

        private View expandableView;
        private View oppositeListView;

        private TextView germanTextViewOne;
        private TextView germanTextViewTwo;
        private TextView englishTextViewOne;
        private TextView englishTextViewTwo;

        private OnWordClickListener onWordClickListener;

        ViewHolder(View itemView, boolean isActivity, OnWordClickListener onWordClickListener) {
            super(itemView);
            if (!mFragmentType.equals(CATEGORY_OPPOSITE)) {
                germanTextView = itemView.findViewById(R.id.german_text_view);
                englishTextView = itemView.findViewById(R.id.english_text_view);
                expandableView = itemView.findViewById(R.id.expandable_view);
                pluralLabelTextView = expandableView.findViewById(R.id.label_one_text_view);
                pluralTextView = expandableView.findViewById(R.id.value_one_text_view);
                rootWordLabelTextView = pluralLabelTextView;
                rootWord = pluralTextView;
                partizipLabelTextView = expandableView.findViewById(R.id.label_two_text_view);
                partizipTextView = expandableView.findViewById(R.id.value_two_text_view);
                helpingVerbLabelTextView = expandableView.findViewById(R.id.label_three_text_view);
                helpingVerbTextView = expandableView.findViewById(R.id.value_three_text_view);
                oppositeLabelTextView = partizipLabelTextView;
                oppositeTextView = partizipTextView;
            }

            if (!isActivity) {
                arrowImageView = itemView.findViewById(R.id.arrow);
                if (mFragmentType.equals(CATEGORY_OPPOSITE)) {
                    oppositeListView = itemView.findViewById(R.id.opposite_list_item);
                    germanTextViewOne = oppositeListView.findViewById(R.id.german_text_view_one);
                    germanTextViewTwo = oppositeListView.findViewById(R.id.german_text_view_two);
                    englishTextViewOne = oppositeListView.findViewById(R.id.english_text_view_one);
                    englishTextViewTwo = oppositeListView.findViewById(R.id.english_text_view_two);
                }
                this.onWordClickListener = onWordClickListener;
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            onWordClickListener.onWordClickListener(getAdapterPosition(), itemView);
        }
    }


    private void viewPlural(ViewHolder holder, Word currentWord) {
        holder.pluralLabelTextView.setVisibility(View.VISIBLE);
        holder.pluralTextView.setVisibility(View.VISIBLE);
        holder.pluralTextView.setText(currentWord.getGermanPlural());

        holder.partizipLabelTextView.setVisibility(View.GONE);
        holder.partizipTextView.setVisibility(View.GONE);
        holder.helpingVerbLabelTextView.setVisibility(View.GONE);
        holder.helpingVerbTextView.setVisibility(View.GONE);


    }

    private void viewRootWord(ViewHolder holder, Word currentWord) {
        holder.rootWordLabelTextView.setText(R.string.root_word_label);
        holder.rootWord.setText(currentWord.getVerbRootWord());

        holder.partizipTextView.setVisibility(View.GONE);
        holder.partizipLabelTextView.setVisibility(View.GONE);
    }


    private void viewPartizip(ViewHolder holder, Word currentWord) {
        if (currentWord.hasPartizip()) {
            holder.partizipLabelTextView.setVisibility(View.VISIBLE);
            holder.partizipLabelTextView.setText(R.string.partizip_label);
            holder.partizipTextView.setVisibility(View.VISIBLE);
            holder.partizipTextView.setText(currentWord.getVerbPartizip());
            holder.helpingVerbLabelTextView.setVisibility(View.VISIBLE);
            holder.helpingVerbTextView.setVisibility(View.VISIBLE);
            holder.helpingVerbTextView.setText(currentWord.getHelpingVerb());
        } else {
            holder.partizipLabelTextView.setVisibility(View.GONE);
            holder.partizipTextView.setVisibility(View.GONE);
            holder.helpingVerbLabelTextView.setVisibility(View.GONE);
            holder.helpingVerbTextView.setVisibility(View.GONE);
        }
    }


    private void viewOpposite(ViewHolder holder, Word currentWord) {
        holder.oppositeLabelTextView.setText(R.string.opposite_word_label);
        holder.oppositeTextView.setText((currentWord.getGermanOpposite() +
                " (" + currentWord.getGermanOppositeMeaning() + ")"));

        holder.oppositeTextView.setVisibility(View.VISIBLE);
        holder.oppositeLabelTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        words.clear();
        words.addAll(getWordsUsingCategory(context, mFragmentType));
        notifyDataSetChanged();
    }
}
