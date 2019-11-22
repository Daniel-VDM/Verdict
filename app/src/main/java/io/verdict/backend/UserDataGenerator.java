package io.verdict.backend;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


class UserDataGenerator {
    private static final String TAG = "UserDataGenerator";

    private Backend backend;
    private Random RNG;
    private JSONObject dataGenTemplate = UserDataGenerator.createFakeDataTemplate();

    UserDataGenerator(final Backend backend) {
        this.backend = backend;
        RNG = new Random();
    }

    private static JSONObject createFakeDataTemplate() {
        JSONObject data = new JSONObject();
        try {
            JSONArray aboutMeTemplate = new JSONArray();
            aboutMeTemplate.put("<NAME> formed their law firm in 2005 on the principle of " +
                    "helping people. <NAME> has handled numerous six, seven, and eight figure " +
                    "cases to successful resolution or verdict. <NAME>'s recent litigation success " +
                    "includes the following verdicts and settlements: a settlement in the case of " +
                    "D.L. v. HMS Construction, Inc. et al. for $21.5 million; a jury verdict in the " +
                    "case of Rittger v. The Cliffs et. al. for $6.7 million.");
            aboutMeTemplate.put("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
                    "do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                    "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex " +
                    "ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate " +
                    "velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat " +
                    "cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id " +
                    "est laborum.");
            aboutMeTemplate.put("<NAME> is dedicated to safeguarding and protecting the rights " +
                    "of clients including health care professionals, professional licensees, " +
                    "businesses and employers whose operations and day-to-day business are " +
                    "heavily regulated by the government. Guided by the ultimate needs of each" +
                    " client and the impact on their particular business or professional " +
                    "circumstances, <NAME> is experienced and dedicated, working to achieve " +
                    "ethical, positive and cost-effective solutions for our clients.");
            aboutMeTemplate.put("<NAME> works with a team of attorneys and administrative " +
                    "professional to help better serve you. Cari prides herself in operating a " +
                    "law firm with a unique law firm culture focused on customer service, " +
                    "timely communication and positivity. <NAME> believes that hiring an attorney " +
                    "should be a form of stress relief for clients instead of the other way around." +
                    " <NAME> take on the pressure of your legal need in a professional, effective," +
                    " and powerful way to help guide you through the process.");
            aboutMeTemplate.put("For nearly 50 years, <NAME> has provided outstanding legal " +
                    "services to individuals, families and businesses throughout this area." +
                    " The firm is best known for its friendly personal relationship with each " +
                    "and every client as well as its very high degree of professionalism and " +
                    "trial law skills. All members of the firm, as well as most of its " +
                    "legal support personnel are completely fluent in Spanish.");
            aboutMeTemplate.put("At vero eos et accusamus et iusto odio dignissimos ducimus qui" +
                    " blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et " +
                    "quas molestias excepturi sint occaecati cupiditate non provident, similique" +
                    " sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum" +
                    " fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero" +
                    " tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus" +
                    " id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis" +
                    " dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut " +
                    "rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et " +
                    "molestiae non recusandae.");
            aboutMeTemplate.put("Donec sed mi velit. Quisque id justo arcu. Class aptent taciti " +
                    "sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
                    "Nam eget enim a turpis aliquam sollicitudin vel et quam. Mauris tincidunt " +
                    "semper eleifend. Aenean diam nibh, pellentesque sit amet purus sed, suscipit" +
                    " maximus nisi. Nullam cursus, lacus at condimentum ultricies, tellus mi " +
                    "aliquam tortor, at efficitur lacus risus nec ante. Proin eleifend tellus " +
                    "orci, a gravida libero hendrerit at. Nullam fringilla orci eget turpis" +
                    " tristique efficitur.");
            aboutMeTemplate.put("Vivamus vel hendrerit quam. Nunc scelerisque magna ut arcu " +
                    "tincidunt interdum. Pellentesque ac tristique diam, vulputate molestie " +
                    "libero. Aliquam nec tortor in libero semper volutpat. Integer iaculis tellus" +
                    " in pellentesque gravida. Cras eros neque, pulvinar eget nunc non, sodales" +
                    " porttitor mauris. Phasellus eu neque lobortis, porttitor urna eget, semper " +
                    "dolor. Nulla imperdiet diam at ante volutpat laoreet. Integer ultrices sapien" +
                    " vitae odio sagittis luctus.");
            aboutMeTemplate.put("Duis sed urna diam. Nam egestas risus vel ornare pharetra. " +
                    "Morbi ultrices purus vel quam tempor sollicitudin. Phasellus maximus " +
                    "hendrerit molestie. Phasellus venenatis nibh vel volutpat viverra. Sed " +
                    "commodo nulla ut ex dictum, quis rhoncus dolor sodales. Ut quis ante maximus," +
                    " efficitur nisl sed, porttitor mauris.");
            aboutMeTemplate.put("Nunc vel sem ultrices, sagittis erat quis, vehicula lorem. " +
                    "Morbi tellus tortor, feugiat nec mi tempor, ullamcorper semper risus. " +
                    "Donec tristique porta odio, at sodales libero porttitor eget. " +
                    "Pellentesque feugiat metus leo, pellentesque lacinia leo pharetra id. " +
                    "Nulla id velit lorem. Cras vitae elit accumsan, venenatis ante ut, " +
                    "convallis tortor. In ullamcorper laoreet velit vitae feugiat.");
            aboutMeTemplate.put("Maecenas quis mauris congue, elementum mauris ut, bibendum" +
                    " sapien. In quis bibendum lacus, sed porttitor tortor. Praesent nec leo " +
                    "nisi. Nulla lobortis libero libero, feugiat ornare dui vehicula sed. " +
                    "In hac habitasse platea dictumst. Nullam nec nulla venenatis, tincidunt " +
                    "massa a, mattis lectus. Morbi massa felis, hendrerit et commodo eu, iaculis " +
                    "eget nulla. Mauris nec vulputate orci, vel aliquet magna. Integer rhoncus " +
                    "metus et ante hendrerit, vitae dictum ligula mattis.");
            data.put("about-me-template", aboutMeTemplate);

            JSONObject reviewTemplate = new JSONObject();

            JSONArray badReview = new JSONArray();
            badReview.put("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua.");
            badReview.put("<NAME> did not put the client ahead of the case and tried too hard to" +
                    " reach for the stars and failed.");
            badReview.put("<NAME> was a walk in the park in court. It felt like they didn't prepare");
            badReview.put("Will never work with <NAME> again. They scammed me out of a deal");
            badReview.put("Aliquam erat volutpat. Sed ut dui ut lacus dictum fermentum vel " +
                    "tincidunt neque. Sed sed lacinia lectus. Duis sit amet sodales felis.");
            badReview.put("At vero eos et accusamus et iusto odio dignissimos ducimus qui" +
                    " blanditiis praesentium voluptatum deleniti atque corrupti quos dolores.");
            badReview.put("Etiam vulputate nulla ac efficitur sagittis. Donec at vehicula lacus." +
                    " Maecenas tempor id risus vitae mollis.");
            badReview.put("Sed commodo nulla ut ex dictum, quis rhoncus dolor sodales. Ut quis " +
                    "ante maximus, efficitur nisl sed, porttitor mauris.");
            badReview.put("Maecenas sed sem ullamcorper, eleifend ligula sit amet, placerat" +
                    " arcu. Nam non facilisis est. Phasellus ac sem non tortor pretium ultricies.");
            reviewTemplate.put("bad-review-template", badReview);

            JSONArray goodReview = new JSONArray();
            goodReview.put("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                    "tempor incididunt ut labore et dolore magna aliqua.");
            goodReview.put("<NAME> was a pleasure to work with. Their work ethic is amazing and " +
                    "any client would be lucky to have them");
            goodReview.put("Would hire <NAME> for my personal issues. They are fantastic and will " +
                    "always put the client ahead of every one.");
            goodReview.put("<NAME> is amazing! They are extremely organized and well prepared. " +
                    "<NAME> is extremely professional in court and has a very commanding presence " +
                    "during trial.");
            goodReview.put("I had a civil case that they handled for me. <NAME> and their team did a" +
                    " great job and won the case for me. If it wasn't for expertise" +
                    " I could have lost it.");
            goodReview.put("<NAME> is an attorney anyone would want in their corner. They are " +
                    "deeply knowledgeable, and knows how to get things done.");
            goodReview.put("I have known <NAME> many years, serving with them on the " +
                    "Sacramento County Board of Directors and know their reputation in the legal " +
                    "community is outstanding. They are open minded, fair, knowledgable " +
                    "and extremely experienced.");
            goodReview.put("<NAME> is an excellent attorney who has a thorough knowledge of " +
                    "their area of expertise. Their trial skills are excellent as well and " +
                    "they are a honorable and compassionate person.");
            goodReview.put("<NAME> is excellent, ethical and highly effective as a lawyer.");
            goodReview.put("Proin lectus erat, consequat sed justo quis, laoreet laoreet dui. " +
                    "Morbi ornare, diam sed cursus tempus.");
            goodReview.put("Nullam sollicitudin ipsum ipsum, et commodo leo fermentum id." +
                    " Fusce finibus lectus neque. Aenean neque diam.");
            goodReview.put("Nulla imperdiet diam at ante volutpat laoreet. " +
                    "Integer ultrices sapien vitae odio sagittis luctus.");
            goodReview.put("Aenean vestibulum lacus suscipit ipsum efficitur aliquam. Fusce mattis," +
                    " urna nec faucibus malesuada, ex est pulvinar ligula, at aliquet.");
            reviewTemplate.put("good-review-template", goodReview);

            data.put("review-template", reviewTemplate);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return data;
    }

    private JSONObject generateLawyerReview(JSONObject targetLawyer, String sourceLawyerKey) {
        JSONObject review = new JSONObject();
        try {
            int wholeRating = (int) (targetLawyer.getDouble("rating") * 2);
            int newRating = ThreadLocalRandom.current().nextInt(wholeRating - 2,
                    Math.max(wholeRating + 2, 10)) / 2;
            review.put("rating", newRating);
            review.put("user", new JSONObject().put("name",
                    Backend.getNameFromKey(sourceLawyerKey)).put("KEY", sourceLawyerKey));
            List<String> reviews = new ArrayList<>();
            JSONArray reviewSource;
            if (newRating < 2.5) {
                reviewSource = dataGenTemplate.getJSONObject("review-template")
                        .getJSONArray("bad-review-template");
            } else {
                reviewSource = dataGenTemplate.getJSONObject("review-template")
                        .getJSONArray("good-review-template");
            }
            for (int i = 0; i < reviewSource.length(); i++) {
                reviews.add(reviewSource.getString(i));
            }
            Collections.shuffle(reviews);
            review.put("text", reviews.get(0).replace("<NAME>",
                    targetLawyer.getString("name")));
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return review;
    }

    private JSONObject generateLawyerAboutMe(JSONObject lawyer) {
        JSONObject aboutMe = new JSONObject();
        try {
            List<String> aboutMeTemplates = new ArrayList<>();
            JSONArray aboutMeTemplate = dataGenTemplate.getJSONArray("about-me-template");
            for (int i = 0; i < aboutMeTemplate.length(); i++) {
                aboutMeTemplates.add(aboutMeTemplate.getString(i));
            }
            Collections.shuffle(aboutMeTemplates);
            aboutMe.put("text", aboutMeTemplates.get(0).replace("<NAME>",
                    lawyer.getString("name")));
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return aboutMe;
    }

    /**
     * This is the main internal method that generates data for a lawyer.
     * This method also updates the firebase's USER_INDEX metadata if it adds new reviewers.
     *
     * @param lawyer Json object for the Yelp api
     * @return The data object associated with LAWYER's KEY in Firebase.
     */
    JSONObject generateDataForLawyer(JSONObject lawyer) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("ABOUT-ME", generateLawyerAboutMe(lawyer));
        JSONArray storedLawyers = backend.getDbUserIndex().getJSONArray("LAWYERS");
        List<String> allLawyers = new ArrayList<>();
        for (int i = 0; i < storedLawyers.length(); i++) {
            allLawyers.add(storedLawyers.getString(i));
        }
        Collections.shuffle(allLawyers);
        int numReviews = RNG.nextInt((int)Math.round(allLawyers.size()*0.15));
        JSONArray lawyerReviews = new JSONArray();
        for (int i = 0; i < numReviews; i++) {
            if (RNG.nextDouble() < 0.5) {
                String sourceLawyerKey = allLawyers.get(i);
                JSONObject review = generateLawyerReview(lawyer, sourceLawyerKey);
                review.put("REVIEWER_KEY", sourceLawyerKey);
                String targetLawyerKey = Backend.getKeyFromName(
                        lawyer.getString("name"), lawyer.getString("id"));
                review.put("LAWYER_KEY", targetLawyerKey);
                lawyerReviews.put(review);
                synchronized (this) {
                    JSONObject reviewIndex = backend.getDbReviewIndex();
                    JSONObject peerReviews = reviewIndex.getJSONObject("PEER_REVIEWS");
                    if (!peerReviews.has(sourceLawyerKey)) {
                        peerReviews.put(sourceLawyerKey, new JSONArray());
                    }
                    JSONArray thisReviews = peerReviews.getJSONArray(sourceLawyerKey);
                    if (!thisReviews.toString().contains(targetLawyerKey)) {
                        peerReviews.getJSONArray(sourceLawyerKey).put(review);
                    }
                    backend.databasePut("META_REVIEW_INDEX", reviewIndex.toString());
                }
            }
        }
        data.put("PEER_REVIEWS", lawyerReviews);
        return data;
    }

    // TODO: handle generation of forums reviews, probably do this based on index
}
