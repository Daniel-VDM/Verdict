package io.verdict.ui.DetailScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.verdict.R;

public class DetailReviewFragment extends Fragment {

    private final static String TAG = "Detail Review";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_review_fragment, container, false);

        Log.e(TAG, "Fragment Created");

        // TODO logic for the review page

        return view;
    }
}
