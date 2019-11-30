package io.verdict.ui.DetailScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;


@SuppressWarnings("ConstantConditions")
public class DetailSubmitFragment extends Fragment {

    private final static String TAG = "Detail Submit";
    private final static String TESTNAME = "TEST_ACCOUNT";
    private final static String TEST_IMAGEURL = "https://partycity3.scene7.com/is/image/" +
            "PartyCity/_sq_?$_500x500_$&$product=PartyCity/828059_full";

    private int currPriceRating;
    private RatingBar ratingBar;
    private SeekBar priceRatingBar;
    private SeekBar.OnSeekBarChangeListener priceDefaultListener;
    private TextInputEditText reviewText;
    private Button submitBtn;
    private JSONObject lawyerDb;
    private JSONObject lawyer;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_submit_fragment, container, false);
        ratingBar = view.findViewById(R.id.detail_submit_rating);
        priceRatingBar = view.findViewById(R.id.detail_submit_price_select);
        reviewText = view.findViewById(R.id.detail_submit_review_box);
        submitBtn = view.findViewById(R.id.detail_submit_button);
        priceDefaultListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currPriceRating = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                currPriceRating = 1;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
        priceRatingBar.setOnSeekBarChangeListener(priceDefaultListener);
        try {
            lawyer = ((DetailScreen) getActivity()).getLawyer();
            lawyerDb = lawyer.getJSONObject("DATABASE_CONTENTS");
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject review = createReview(TESTNAME, TEST_IMAGEURL);
                    Toast toast = Toast.makeText(view.getContext(),
                            "You review has been submitted!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 10);
                    toast.show();
                    saveReview(review);
                    ratingBar.setRating(0F);
                    reviewText.getText().clear();
                    priceRatingBar.setOnSeekBarChangeListener(priceDefaultListener);
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        });

        return view;
    }

    private JSONObject createReview(String userName, String imageUrl) throws JSONException {
        JSONObject review = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("name", userName);
        user.put("image_url", imageUrl);
        user.put("USER_TYPE", "user");
        String id = "WE-HAVE-NO-TIME-TO-DO-THIS-PROJECT";
        user.put("id", id);
        user.put("KEY", Backend.getKeyFromName(userName, id));
        review.put("user", user);
        review.put("time_created", new Date().toString());
        review.put("rating", ratingBar.getRating());
        review.put("price", currPriceRating);
        review.put("text", reviewText.getText().toString());
        review.put("REVIEWER_KEY", user.getString("KEY"));
        review.put("LAWYER_KEY", lawyer.getString("KEY"));
        review.put("VERIFIED", true);
        return review;
    }

    private void saveReview(final JSONObject review) throws JSONException {
        final Backend backend = ((DetailScreen) getActivity()).getBackend();

        final DatabaseListener listener = new DatabaseListener() {
            @Override
            public void onStart(String key) {
            }

            @Override
            public void onSuccess(String key, String value) {
                try {
                    JSONObject dbContents = new JSONObject(value);
                    JSONArray reviews = dbContents.getJSONArray("USER_REVIEWS");
                    reviews.put(review);

                    String sourceLawyerKey = review.getJSONObject("user").getString("KEY");
                    JSONObject reviewIndexEntry = new JSONObject();
                    reviewIndexEntry.put("REVIEWER_KEY", sourceLawyerKey);
                    reviewIndexEntry.put("LAWYER_KEY", key);
                    JSONObject reviewIndex = backend.getDbReviewIndex();
                    JSONObject userReviews = reviewIndex.getJSONObject("USER_REVIEWS");
                    if (!userReviews.has(sourceLawyerKey)) {
                        userReviews.put(sourceLawyerKey, new JSONArray());
                    }
                    JSONArray thisReviews = userReviews.getJSONArray(sourceLawyerKey);
                    if (!thisReviews.toString().contains(reviewIndexEntry.toString())) {
                        userReviews.getJSONArray(sourceLawyerKey).put(reviewIndexEntry);
                    } else {
                        return;
                    }
                    backend.databasePut(key, dbContents.toString());
                    backend.databasePut("META_REVIEW_INDEX", reviewIndex.toString());
                } catch (JSONException e){
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    String key = lawyer.getString("KEY");
                    backend.databaseGet(key, listener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

