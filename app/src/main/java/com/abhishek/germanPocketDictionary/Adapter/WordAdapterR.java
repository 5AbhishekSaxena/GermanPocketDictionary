package com.abhishek.germanPocketDictionary.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhishek.germanPocketDictionary.Activity.SearchResultActivity;
import com.abhishek.germanPocketDictionary.Data.Word;
import com.abhishek.germanPocketDictionary.Fragments.WordsFragment;
import com.abhishek.germanPocketDictionary.R;

import java.util.List;


/**
 * @author Abhishek Saxena
 * @since 22-06-2019 04:14
 */

public class WordAdapterR extends RecyclerView.Adapter<WordAdapterR.ViewHolder>{

    private Context context;
    private WordsFragment fragment;

    private int mFragmentType;

    private boolean isActivity;
    private boolean EMPTY = true;
    private boolean searchState = EMPTY;

    public int selectedNoun;
    public int selectedVerb;

    private List<Word> words;
    private OnWordClickListener onWordClickListener;

    //private final OnClickListener mOnClickListener = new MyOnClickListener();

    public WordAdapterR(WordsFragment fragment, Context context, List<Word> words, int fragmentType,
                        OnWordClickListener onWordClickListener) {
        this.context = context;
        this.words = words;
        this.fragment = fragment;
        mFragmentType = fragmentType;
        this.onWordClickListener = onWordClickListener;
    }

    public WordAdapterR(Activity context, List<Word> words, boolean searchState,
                        OnWordClickListener onWordClickListener) {
        this.context = context;
        isActivity = true;
        this.searchState = searchState;
        this.words = words;
        this.onWordClickListener = onWordClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, isActivity, onWordClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word currentWord = words.get(position);

        //Locate the TextView in the list_item
        //TextView germanTextView = holder.findViewById(R.id.german_text_view);

        //Get the German Translation from the Word Class and update the list_item
        holder.germanTextView.setText(currentWord.getmGermanTranslation());

        //Locate the TextView in the list_item
        //TextView englishTextView = holder.findViewById(R.id.english_text_view);

        //Get the English Translation words==null?0:words.size()rom the Word Class and update the list_item
        holder.englishTextView.setText(currentWord.getmEnglishTranslation());

        if (!isActivity) {
            if (currentWord.hasPlural()) {
                viewPlural(holder, currentWord);
            }

            if (currentWord.hasOpposite()) {
                viewOpposite(holder, currentWord);
            }

            if (mFragmentType == WordsFragment.NOUNS) {
                holder.arrowImageView.setVisibility(View.VISIBLE);
                if (fragment.selectedNoun == position) {
                    holder.expandableView.setVisibility(View.VISIBLE);
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                } else {
                    holder.expandableView.setVisibility(View.GONE);
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                }

                /*holder.regularListView.setOnClickListener(v -> {
                    selectedNoun = holder.getLayoutPosition();
                    notifyDataSetChanged();
                    if (position == selectedNoun) {
                        if (holder.expandableView.getVisibility() == View.VISIBLE) {
                            holder.expandableView.setVisibility(View.GONE);
                            holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
                            selectedNoun = -1;
                        } else {
                            holder.expandableView.setVisibility(View.VISIBLE);
                            holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
                        }
                    }


                    //notifyDataSetChanged();
                });*/

            }

            if (mFragmentType == WordsFragment.VERBS) {
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

            if (mFragmentType == WordsFragment.OPPOSITE) {
                holder.regularListView.setVisibility(View.GONE);
                holder.oppositeListView.setVisibility(View.VISIBLE);
                holder.germanTextViewOne.setText(currentWord.getmGermanTranslation());
                holder.englishTextViewOne.setText(currentWord.getmEnglishTranslation());

                holder.germanTextViewTwo.setText(currentWord.getmGermanOpposite());
                holder.englishTextViewTwo.setText(currentWord.getmGermanOppositeMeaning());
            }

        } else {
            holder.expandableView.setVisibility(View.GONE);

            if (holder.expandableView != null) {
                if (SearchResultActivity.searchState != EMPTY) {
                    if (currentWord.getmCategory().contains("1")) {
                        holder.expandableView.setVisibility(View.VISIBLE);


                        if (currentWord.hasPlural())
                            viewPlural(holder, currentWord);
                    }
                    if (currentWord.getmCategory().contains("7")) {
                        holder.expandableView.setVisibility(View.VISIBLE);
                        viewRootWord(holder, currentWord);
                        viewPartizip(holder, currentWord);
                    }
                    if (currentWord.hasOpposite()) {
                        holder.expandableView.setVisibility(View.VISIBLE);
                        viewOpposite(holder, currentWord);
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

        //private ViewGroup mainView;
        //private View rootView;
        private View expandableView;
        private View regularListView;
        private View oppositeListView;

        private TextView germanTextViewOne;
        private TextView germanTextViewTwo;
        private TextView englishTextViewOne;
        private TextView englishTextViewTwo;

        private OnWordClickListener onWordClickListener;

        ViewHolder(View itemView, boolean isActivity, OnWordClickListener onWordClickListener) {
            super(itemView);

            //rootView = itemView.findViewById(R.id.main_view);
            //mainView = itemView.findViewById(R.id.main_view);
            germanTextView = itemView.findViewById(R.id.german_text_view);
            englishTextView = itemView.findViewById(R.id.english_text_view);
            expandableView = itemView.findViewById(R.id.expandable_view);
            pluralLabelTextView = expandableView.findViewById(R.id.plural_text_label);
            pluralTextView = expandableView.findViewById(R.id.plural_text);
            rootWordLabelTextView = pluralLabelTextView;
            rootWord = pluralTextView;
            partizipLabelTextView = expandableView.findViewById(R.id.opposite_text_label);
            partizipTextView = expandableView.findViewById(R.id.opposite_text);
            helpingVerbLabelTextView = expandableView.findViewById(R.id.helping_text_label);
            helpingVerbTextView = expandableView.findViewById(R.id.helping_verb_text);
            oppositeLabelTextView = partizipLabelTextView;
            oppositeTextView = partizipTextView;

            if (!isActivity) {
                arrowImageView = itemView.findViewById(R.id.arrow);
                regularListView = itemView.findViewById(R.id.regular_list_item);
                oppositeListView = itemView.findViewById(R.id.opposite_list_item);
                germanTextViewOne = oppositeListView.findViewById(R.id.german_text_view_one);
                germanTextViewTwo = oppositeListView.findViewById(R.id.german_text_view_two);
                englishTextViewOne = oppositeListView.findViewById(R.id.english_text_view_one);
                englishTextViewTwo = oppositeListView.findViewById(R.id.english_text_view_two);
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
        holder.pluralTextView.setText(currentWord.getmGermanPlural());

        holder.partizipLabelTextView.setVisibility(View.GONE);
        holder.partizipTextView.setVisibility(View.GONE);
        holder.helpingVerbLabelTextView.setVisibility(View.GONE);
        holder.helpingVerbTextView.setVisibility(View.GONE);


    }

    private void viewRootWord(ViewHolder holder, Word currentWord) {
        holder.rootWordLabelTextView.setText(R.string.root_word_label);
        holder.rootWord.setText(currentWord.getmVerbRootWord());

        holder.partizipTextView.setVisibility(View.GONE);
        holder.partizipLabelTextView.setVisibility(View.GONE);
    }


    private void viewPartizip(ViewHolder holder, Word currentWord) {
        if (currentWord.hasPartizip()) {
            holder.partizipLabelTextView.setVisibility(View.VISIBLE);
            holder.partizipLabelTextView.setText(R.string.partizip_label);
            holder.partizipTextView.setVisibility(View.VISIBLE);
            holder.partizipTextView.setText(currentWord.getmVerbPartizip());
            holder.helpingVerbLabelTextView.setVisibility(View.VISIBLE);
            holder.helpingVerbTextView.setVisibility(View.VISIBLE);
            holder.helpingVerbTextView.setText(currentWord.getmHelpingVerb());
        } else {
            holder.partizipLabelTextView.setVisibility(View.GONE);
            holder.partizipTextView.setVisibility(View.GONE);
            holder.helpingVerbLabelTextView.setVisibility(View.GONE);
            holder.helpingVerbTextView.setVisibility(View.GONE);
        }
    }


    private void viewOpposite(ViewHolder holder, Word currentWord) {
        holder.oppositeLabelTextView.setText(R.string.opposite_word_label);
        holder.oppositeTextView.setText((currentWord.getmGermanOpposite() +
                " (" + currentWord.getmGermanOppositeMeaning() + ")"));

        holder.oppositeTextView.setVisibility(View.VISIBLE);
        holder.oppositeLabelTextView.setVisibility(View.VISIBLE);

    }

    public interface OnWordClickListener{
        void onWordClickListener(int position, View itemView);
    }
}
