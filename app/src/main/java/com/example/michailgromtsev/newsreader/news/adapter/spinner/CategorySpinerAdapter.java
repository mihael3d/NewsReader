package com.example.michailgromtsev.newsreader.news.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.data.network.models.NewsCategory;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CategorySpinerAdapter extends ArrayAdapter<NewsCategory> {

    private static final int DEFAULT_SPINNER_ITEM = R.layout.categories_spinner_item;
    private static final int DEFAULT_DROP_DOWN_ITEM = R.layout.categories_drop_down_spinner_item;

    private static final String TAG_DROPDOWN = "DROPDOWN";
    private static final String TAG_NON_DROPDOWN = "NON_DROPDOWN";

    private  NewsCategory selectedCategory = NewsCategory.HOME;

    @NonNull
    public static CategorySpinerAdapter createDefault(@NonNull Context context,
                                                      @NonNull NewsCategory[] caegories) {
        return new CategorySpinerAdapter(context,DEFAULT_SPINNER_ITEM,caegories);
    }

    private final LayoutInflater inflater;
    private OnCategorySelectedListener categorySelectedListener;

    private CategorySpinerAdapter(@NonNull Context context,
                                 int resource,
                                 @NonNull NewsCategory[] objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        convertView = chekConvertView(convertView,parent,TAG_NON_DROPDOWN,DEFAULT_SPINNER_ITEM);
        final NewsCategory item = getItem(position);
        if (item == null) {
            return convertView;
        }

        final TextView tvTitle = convertView.findViewById(R.id.tv_title_spiner);
        tvTitle.setText(item.displayValue());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = chekConvertView(convertView, parent, TAG_DROPDOWN, DEFAULT_DROP_DOWN_ITEM);

        final NewsCategory item = getItem(position);
        if (item == null) {
            return convertView;
        }

        final TextView tvDropDown = convertView.findViewById(R.id.tv_drop_down);
        tvDropDown.setText(item.displayValue());


        return convertView;
    }

    @NonNull
    private View chekConvertView(@Nullable View convertView,
                                 @NonNull ViewGroup parent,
                                 @NonNull String tag,
                                 @LayoutRes int layoutId) {
        if (convertView == null || !convertView.getTag().equals(tag)) {
            convertView = inflater.inflate(layoutId,parent,false);
            convertView.setTag(tag);
        }
        return convertView;
    }

    public void setOnCategorySelectListner(@Nullable OnCategorySelectedListener categorySelectListner,
                                           @NonNull Spinner spinner) {
        this.categorySelectedListener = categorySelectListner;
        if (categorySelectListner == null) {
            spinner.setOnItemSelectedListener(null);
            return;
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final NewsCategory item = getItem(i);
                CategorySpinerAdapter.this.selectedCategory = item;
                categorySelectListner.onSelected(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @NonNull
    public NewsCategory getSelectedCategory() {
        return selectedCategory;
    }

    public interface OnCategorySelectedListener {
        void onSelected (@NonNull NewsCategory category);
    }
}
