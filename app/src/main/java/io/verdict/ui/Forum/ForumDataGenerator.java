package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.verdict.R;
import io.verdict.backend.Backend;

@SuppressWarnings("ConstantConditions")
public class ForumDataGenerator {

    private static final String TAG = "ForumDataGenerator";

    private static final String[][] PERSONAL_INJURY_QUESTIONS = {
            {
                    "95",
                    "Question about heated car seats?",
                    "About 6 weeks ago the heated seats in my car burnt my upper leg/butt. Is this worth pursuing against the car manufacturer considering the seats get hot enough to burn skin like this? Thanks in advance for any advice.",
                    "19-02-18"
            },
            {
                    "145",
                    "Got a letter from personal injury lawyer in California.",
                    "Today we got a letter from a personal injury lawyer representing the child and his mother. Stating the child suffered significant injuries. We reached out to our renters insurance and it will likely be covered. I am stressed and worried and am not sure where we go from here. Is there more we should be doing to cover our own bases?",
                    "18-02-17"
            },
            {
                    "112",
                    "Summoned for personal injury after a car accident almost 2 years ago.",
                    "I live in NYC and was involved in a car accident in 2018. Basically the other driver was trying to change into my lane and I was trying change into her lane and we both hit each other. This has become quite a bit stressful for me. Is this something I should be worried about?",
                    "25-01-18"
            },
            {
                    "370",
                    "Legal time limit to sue for personal injury in Korea",
                    "Accident happened in 2015, after years of medical tribulations I am still injured and in pain (spinal injury, had to undergo surgery too). One lawyer told me the limit is 3 years, another told me 10. Does anyone have more information?",
                    "09-12-19"
            },
            {
                    "82",
                    "Sister in law receiving personal injury letters from car accident?",
                    "My SIL's bf got into a pretty serious car accident almost a month ago.",
                    "13-07-19"
            },
            {
                    "76",
                    "My lawyer isn't responding to my emails.",
                    "He says that he is preparing an engagement letter for my personal injury case abroad",
                    "25-12-19"
            },
            {
                    "47",
                    "Needing help picking a good wrongful death attorney.",
                    "I lost a dear family member on the job. We are looking for an experienced and reputable personal injury, wrongful death more specifically, firm or attorney in the Dallas county? I would appreciate any help with this.",
                    "26-03-19"
            },
            {
                    "258",
                    "Need advice about Personal Injury Claim.",
                    "I am pursuing a personal injury claim against my friend's insurance, Progressive. I already recieved $5,000 towards my bills in the \"Med Pay\" portion of her coverage. Is my settlement likely to be low, but I can keep it all? ( Do I have to repay my medical insurance company out of settlement money?)",
                    "05-04-17"
            },
            {
                    "54",
                    "Personal Injury case?",
                    "A couple months back (at a local climbing gym) I was bouldering and fell about 10 feet. Is this a case? Do I have something? Or should I just cut my losses now and let it be.",
                    "09-09-17"
            },
            {
                    "395",
                    "Meeting with personal injury lawyer (MA)?",
                    "After a car accident resulting in a not-insignificant back injury for my mid-60s mother, doctor anticipates lifelong pain and suggested hours reduction at work. What should we be asking a personal injury lawyer and what documents and info should we bring?",
                    "24-10-18"
            },
            {
                    "118",
                    "Motor Vehicle Accident / Personal Injuries Claim",
                    "In June I was rear ended while waiting to turn into a business. I did not go to physio because my doctor told me that light duty at my job would basically help just as much as physio. Looking for any information and help really. What should I do reddit?",
                    "28-04-19"
            },
            {
                    "257",
                    "Help with Allstate Personal Injury Claim.",
                    "I opened a claim after I was rear ended. They accepted the claim and have agreed to fix my car. They are refusing to pay any medical bills and only have offered me 200$ to settle the personal injury claim. What should I do?",
                    "02-04-18"
            },
            {
                    "380",
                    "Should I consult a personal injury lawyer?",
                    "I managed to fall and break my foot. I have ER and orthopedist records to confirm this. Is it worth contacting a personal injury lawyer in this case? Do I even have a case?",
                    "16-01-17"
            },
            {
                    "92",
                    "Getting sued for personal injury for a 4 car accident.",
                    "Three cars in front of him collided into each other with him being the 4th and last car to hit the 3rd car. He was not insured on my policy. There was no major damage to car. Just maybe a scratch. How do I handle this? If my old insurance don’t cover, is there anything I can use for our case such as no damage to my vehicle and the one ahead ?",
                    "07-02-18"
            }
    };

    private static final String[][][] PERSONAL_INJURY_ANSWERS = {
            {
                    {"24", "The correct answers is to speak with a personal injury lawyer. Many offer free short consults to determine if you have a case.",},
                    {"89", "Just because you could file suit doesn't mean you should. You may fight for months or years only to lose."},
                    {"75", "There is no simple answer to your situation. Sometimes speaking to a lawyer is the best legal advice we can give."},
            },
            {
                    {"1", "Just let it play out. They are either scamming or they are legitimately concerned about having a scar and want to cover their bases for the kid‘a future but it probably won’t matter either way."},
                    {"20", "There is nothing you should do at this point besides cooperate with your insurance company."},
            },
            {
//                    {"8", "Your insurance is the one making the determination at this point, so ask them"},
//                    {"18", "lawsuits have to be filed within a certain amount of time from the actual accident. I believe 2 years is the limit for car accidents in most states."},
//                    {"55", "This exact same thing happened to my boyfriend this year. Where we live, the statute of limitations on personal Injury cases is 2 years. After the initial accident, no one was injured, both cars drivable, and he never heard so much as a peep from the other drive, until he received the summons this year."},
//                    {"24", "This is why you pay for insurance. They have just as much incentive to get this settled as you do. Most cases settle within policy limits before ever getting to trial."},
            },
            {
                    {"19", "It looks like the statute of limitations for tort in Korea is either (i) ten years from the date the tort was committed or (ii) three years from the date the injured party becomes aware of the damaged suffered and the identity of the tortfeasor, whichever is shorter."},
                    {"44", "You probably need a more in depth consultation with a Korean lawyer to see if you can still file a claim."},
            },
            {
                    {"4", "She should let her insurance know as soon as possible. She shouldn't contact the other attorney."},
                    {"17", "If she contacts the other party's attorney, she needs to keep in mind that that attorney has no duty or obligation to be straightforward with her or to represent their client's claims in a balanced or reasonable way."},
                    {"11", "If the liability insurer refuses to get involved, or tries to prove that they're not legally liable to pay for the damages your SIL is being sued for, then things get kinda weird."},
            },
            {
                    {"63", "Over what period of time did you send the emails?"},
                    {"26", "I would wait for them to reach out to you."},
            },
            {
                    {"58", "Maybe get referrals from friends and family, use Google, search the phone book, and consult with various attorneys until you find one you're comfortable with."},
                    {"3", "Make sure to use the search feature on Verdict!"},
            },
            {
                    {"54", "Yes. The motorcycle insurance is the primary insurance in this case and your personal medical insurance is secondary."},
                    {"29", "You may not have to repay the BCBS. That's going to be dictated by your plan. They may also be willing to negotiate their lien."},
                    {"54", "It can't hurt to get a consultation since they're free. Do you know what your friend's policy limits are? I would imagine that would be a major determining factor in how you might proceed."},
                    {"1", "Google tells me the minimum liability coverage is $15k per person/$30K per accident. Which wouldn't even cover your medicals."},
                    {"37", "Correct, any money for medical bills must be sent back to blue cross. Any money not specifically for medical bills is yours to keep."},
            },
            {
//                    {"84", "The gym almost certainly has insurance for this type of thing, but they do not sound like they were negligent at all."},
//                    {"90", "If you've already spoken to lawyers, just go with the one that thinks you have a case."},
            },
            {
                    {"3", "You should go see a lawyer right away. She's got a pretty good case. Call a personal injury lawyer in your state and they will take care of everything."},
                    {"26", "In my state, she would be entitled to everything you mentioned as well as the medical expenses incurred, whether paid by insurance or other 3rd party or not, and pain and suffering."},
                    {"52", "Don't worry about what to bring - the lawyer will tell you."},
                    {"36", "It is very likely an undisputed liability case and there are probably hundreds of lawyers around you that would love to take the case."},
            },
            {
                    {"15", "it does look like BC allows claims for accelerated depreciation, but you may have to pursue them independently of your ICBC claim."},
                    {"1", "If you believe you're being low-balled by ICBC and they won't shift their settlement, speak to a lawyer about your options."},
            },
            {
                    {"9", "Get a personal injury lawyer immediately. Bring copies of any medical records and bills that you've collected, they will be helpful. Let the lawyer fight for you to get your medical bills paid and such"},
                    {"38", "You have 2 choices. Hire a PI attorney file suit against the driver. The attorney will take about 1/3 plus expenses (so in reality it'll wind up being as much as 50%) of any settlement they get you."},
                    {"40", "Or you can file a small claims suit yourself without an attorney, assuming the dollar amount is within small claims jurisdiction."},
            },
            {
//                    {"31", "Try to grab some photos of the exact spot you fell (both close up and far away, showing landmarks). You will have your pick of PI lawyers to choose from."},
//                    {"30", "Did you trip over an uneven sidewalk? Did you get any witness information? Honestly, since it's been two months it's going to be hard to prove what actually happened and that the city is negligent."},
//                    {"28", "Not reporting after the fact is not dispositive at all. A huge chunk of PI cases are blind accidents."},
//                    {"9", "Speak to a few friends to see if anyone used a PI lawyer they liked. This is a regular, run of the mill trip and fall claim (albeit probably in the Court of Claims), but anyone with more than a few years of experience is more than capable of handling it."}
            },
            {
                    {"14", "He was at fault, he hit a stopped vehicle."},
                    {"37", "Notify the insurance you had at the time immediately."},
                    {"12", "They'll cover you for claims that came about when you had coverage."},
            },

    };

    private static final String[][] REAL_ESTATE_QUESTIONS = {
            {
                    "283",
                    "Found a large amount of cache in my new house?",
                    "The cash has been there for a while, from what I can tell, not a single bill is newer than the early 1990’s. There is $312,000 in cash in total, all in pristine condition. I am not sure what to do at this point both from a legal as well as a moral standpoint.",
                    "16-06-17"
            },
            {
                    "281",
                    "HOA formed and being forced to join it and liens on my property.",
                    "We filed a complaint with the police regarding the stolen mailbox and we have a paper trail for that now. It's just a waiting game to see what they do next I guess. Should I send them a letter saying I know their plan and there is no way they can get my land?",
                    "27-12-19"
            },
            {
                    "55",
                    "Neighbors false advertising.",
                    "The buyer is a real estate developer and he told us he is going to hire a friend of his who does land surveys to come out tomorrow to say that the easement does come from our property. Should we hire our own surveyor? Is there anyway we can prove his surveyor is biased? What else should we be concerned about?",
                    "14-05-19"
            },
            {
                    "137",
                    "Bought a house, the owners abandoned all their stuff in it.",
                    "I don't want this lady on my property or in my garage looking for things that might have belonged to her ex husband. Do I have any legal obligation to let her come get the stuff?",
                    "28-06-18"
            },
            {
                    "289",
                    "Ended up in the ER after an accident at work.",
                    "My boss is lying to me to me and playing stupid. Turns out she does not have WC and may not even have a business license. Looking back on it, I think the hospital staff discovered this as well but were going to let me figure that out. Is there anything I can do about this, or am I just stuck with the bill?",
                    "01-06-18"
            },
            {
                    "154",
                    "Seller \"rented\" it to his nephew the day before contracts were signed.",
                    "I'd be willing to do a cash for keys thing to get this guy out of our apartment but he won't speak to us. Beyond that, do I have grounds to evict him?",
                    "25-06-17"
            },
            {
                    "232",
                    "Landlord never signed the lease.",
                    "My question is, is this legal for them to do? Is it our fault for not getting confirmation of the landlord's signature? Should our next move just be bite the bullet and sign the new lease or something else?",
                    "04-10-18"
            },
            {
                    "98",
                    "Unceasing construction for 6+ years on single family home.",
                    "I know this seems so minor, but after five years of constant noise, not even taking the weekends off, I am going batty. Is there any potential solution or way to address it?",
                    "11-03-19"
            },
            {
                    "23",
                    "17 year old took the keys and held a party in the empty condo.",
                    "Causing $50,000 in damages and resulting in a police raid. What to do now?",
                    "09-04-18"
            },
            {
                    "364",
                    "Came home to a new fence in my backyard.",
                    "I don't know who owns it, so I called the real estate agent. He was just as shocked as I was when I told him there had been a fence built at a house he is selling. What do I do about this fence?",
                    "09-11-17"
            },
            {
                    "250",
                    "Year-round neighbor built a shelter under us in NY state.",
                    "",
                    "28-01-17"
            },
            {
                    "96",
                    "Tenant repaired A/C without our permission.",
                    "What if she continues to deduct from rent? What is the best way to have her keep paying rent until her lease ends, without having to go through the hassle of going to court?",
                    "16-09-17"
            },
            {
                    "15",
                    "House sellers want to back out of accepted offer.",
                    "They think they can sell the house for more- do we have any legal recourse?",
                    "18-08-18"
            },
            {
                    "134",
                    "Can I leave a welcome plant/basket for the buyers of our house?",
                    "Selling my home, we have a buyer. And closing scheduled for a couple of weeks from now. This would be their first house together. This house also happened to be our first home, and I remember how overwhelming and special the whole experience was when we bought our first home.",
                    "20-09-19"
            }
    };
    private static final String[][][] REAL_ESTATE_ANSWERS = {
            {
                    {"91", "Don't tell them a peep about the money. You have no duty to tell previous owners what you found in their old place."},
                    {"55", "Was he trying to hide it from family members? Who else was told about it? It's too bad he was deployed but he had a reasonable chance to do this, and not to wait until 6 months after the sale closes."},
                    {"31", "I'm guessing Mr. Deployed didn't want to tell the other family members about the possibility of a cash-filled hidden room."},
                    {"14", "But WOW! You had a great inspector! Comparing building plans for a 1920s house to current room size? Dang! That's some thorough work."},
                    {"70", "Under Massachusetts law, you are required to report the finding of lost money or goods to local law enforcement. It's called the \"Massachusetts' Lost Goods and Stray Beasts Act\""},
                    {"1", "Well, the good news is you have enough money to get actual advice from an attorney regarding the situation."},
            },
            {
//                    {"3", "Contact them only through the lawyer and do not go to the meeting. I know you're angry, but they're trying to upset you to the point of leaving."},
//                    {"57", "Just let them go along thinking you are ignorant of what they want to do and let your lawyer hand them their rears legally."},
//                    {"45", "The US Post Office Postal Inspectors take mail boxes very seriously."},
            },
            {
                    {"5", "From this point on, every time they trespass on your property, call the cops."},
                    {"33", "For a boundary survey one of the very first steps is deed research of your property plus all property that abuts yours. Any/all easements will show up in this and end all speculation."},
                    {"24", "Do you have title insurance? If so, the title company will fight for you if it becomes disputed. They should have issued a report on the property when it was purchased which shows whether or not easements exist on your property."},
                    {"39", "Surveyors will not be biased. They know everything they ever do can and do endd up in court."}
            },
            {
                    {"10", "If she's claiming that the belongings were the property of her deceased ex-husband, how would you even verify that she is the legal heir of his property?"},
                    {"11", "Where someone leaves personal property at the conclusion of a real estate transaction, It is generally not the case that you simply own the property left."},
                    {"37", "Did you use a real estate attorney for the purchase? If so, call him and let him deal with this"},
                    {"6", "Not a lawyer, but I see a red flag. You say you bought the house from a couple who were divorcing, and a significant time later she wants the stuff left behind claiming that he died. This particular advice applies only to the registered items (bike and trailer, but those seem to be what she is after)."},
                    {"26", "When I purchased my last house, I'm pretty sure that my attorney had a clause in the contract about how any personal property remaining in the house would become mine."},
            },
            {
                    {"27", "Based on what you said, you’re probably an employee, not an independent contractor. Here’s what’s important - Florida’s workers comp statute prohibits you from making a bodily injury claim outside of the workers comp system UNLESS your employer fails to carry comp or fails to cover a covered injury."},
                    {"1", "But a few thoughts stick out. If she was paying you by check, presumably without withholding for taxes, she may be categorizing you as a 1099 independent contractor as opposed to an employee."},
            },
            {
                    {"42", "This is basically fraud."},
                    {"15", "In that case you may be able to sue the seller for damages for acting in bad faith. The attorney I'm sure will give you a better idea."},
                    {"16", "Yeah, you're going to want to get your lawyer from the closing involved. They will already have the pertinent documents to follow up on this."},
                    {"26", "I hope you have documentation of the condition that the condo was in during your walk through. You could possibly sue for any damages that the nephew may cause over this especially if you have to go through the eviction process."},
            },
            {
                    {"34", "You've paid a deposit and started paying rent according to the terms of the lease. There's a legal term for this, I can't think of it off the top of my head but an actual lawyer will probably chime in."},
                    {"17", "This is their error. If you have a signed copy of the lease, decline to sign the new lease, and continue to live under the current version."},
                    {"23", "Check to see if your college offers student legal services. It's pretty common, and this is exactly the kind of thing they often deal with."},
            },
            {
                    {"21", "Do they have permits? If not, call code enforcement."},
                    {"33", "Just curious. Is it some sort of trade school situation? Practicing demo and construction?"},
                    {"43", "You've obviously tried contacting the police, but maybe try contacting your city's department of environmental health?"},
                    {"43", "I find it fascinating that all these contractors are getting paid over his length of time. Or are they?"},
                    {"35", "You mention it's the house across from you, have you tried talking to the side neighbors of the construction house?"},
                    {"42", "There might be enough of a paper trail somewhere that might interest tax collectors at the local, state, and/or federal level."},
            },
            {
//                    {"6", "First step is to talk to her homeowner's insurance, plain and simple."},
//                    {"38", "Also file a complaint with the NC real estate licensing board, the NC Association of Realtors and the local board of Realtors."},
            },
            {
                    {"3", "You mentioned the house is for sale. It looks to me like squatters put the fence up to make it harder to see people coming and going next door."},
                    {"29", "Call the local building department and see if the installation required a permit, and if someone obtained it. They might cite the homeowner and require it removed."},
                    {"40", "Former zoning official here - get the city involved asap. Take pictures of every of the fence including front of home behind you."},
                    {"5", "It looks like it's just, sitting there on top of the ground. It looks like it could fall over any second and be dangerous. If it is on your property, I might go about calling the police, someone trespassing on your land, and then remove it."},
            },
            {
                    {"38", "You should talk to a real estate lawyer. You may also need to get a surveyor. You can also contact the county about whether permits were issued."},
                    {"14", "Surveyor tech here. This is a textbook property dispute/encroachment. Hire a real estate lawyer, they may have a local surveyor on retainer that can give you a discount."},
                    {"16", "Start out with code enforcement to see if they have permits. I highly doubt it was approved. They can get it shut down quickly."},
                    {"7", "I would think you could get a property surveyor to tell you exactly where your property line is. Property lines become distorted overtime when fences get put up, ground shifts, etc. It is always a nice thing regardless of this situation to know where that is."},
                    {"2", "I can with reasonable certainty say they didnt get permits to do this work. There are property setback rules that must be followed which will vary but that is definitely not going to pass any requirements."},
                    {"22", "Check to see if any building permits were issued. If there are no permits then look around on the surface for air vents. Has any of the surface soil or vegetation been disturbed?"},
            },
            {
                    {"4", "First, since she did not give landlord notice of the problem and give the landlord ample time to attempt to fix the problem she does not have the right to deduct the repair from the rent."},
                    {"3", "I personally would have evicted the second I caught her using my rental as a vacation rental, you're much too kind in my opinion."},
                    {"20", "Others have already covered the real answer, but just to note that as per my understanding, if you provide A/C when the tenant rents the property, you are responsible for its upkeep."},
            },
            {
                    {"17", "We're just trying to figure out what our options are here. We are obviously upset about this last-minute ambush, especially since they never attempted to negotiate with us when we made the offer or when we asked for repairs."},
                    {"35", "It can take years and tens of thousands of dollars, but you might eventually get the house. If I were in your shoes, I would probably hire an attorney to write a letter and threaten them, but not actually sue."},
                    {"74", "In many jurisdictions your attorney would record a document putting the world on notice that you have a legal claim relating to ownership of the house. Any prospective other buyers will be unable to get a mortgage, because any lender they approach will see your claim."},
                    {"47", "You need a real estate lawyer for this. Personally I would proceed with the contract as normal with the lawyer assisting you. Plan on closing and let them suffer the consequences of not abiding by the contract."},
                    {"12", "I would not take them to court. Could take years, lots of money and they could always wreck the house. First, I don't think they actually want to terminate the contract. My guess is, they want you to agree to pay repair costs or increase the purchase price."},
            },
            {
//                    {"70", "Yes you can leave that stuff and no you won't be liable."},
//                    {"33", "It's a kind gesture. It also ensures that - should you need to do something like pick up unforwarded mail - you've made yourself known to them."},
//                    {"55", "Landlord here. Nothing wrong with leaving them a present. On the more practical side, on top of whatever else you'd like to give them, leave an individually sealed roll of paper towels and toilet paper, a small vial of unscented dish soap, and some disposable plates/cups/utensils."},
            },
    };

    private static Backend backend;
    private Context context;

    @SuppressLint("Assert")
    public ForumDataGenerator(Context context) {
        assert PERSONAL_INJURY_ANSWERS.length == PERSONAL_INJURY_QUESTIONS.length;
        assert REAL_ESTATE_ANSWERS.length == REAL_ESTATE_QUESTIONS.length;
        backend = new Backend();
        this.context = context;
    }

    private ArrayList<Question> createDebugQuestions(String lawField) {
        ArrayList<Question> result = new ArrayList<>();
        List<String> dummy_questions = new ArrayList<>();
        dummy_questions.add("Should I take my case to court?");
        dummy_questions.add("Who is the best lawyer in my area?");
        dummy_questions.add("Who should I ask for help?");
        dummy_questions.add("What am I entitled to?");
        dummy_questions.add("What do I do now?");
        dummy_questions.add("How do I file a case?");

        for (int i = 0; i < 6; i++) {
            String date = "01-11-" + String.format("%02d", new Random().nextInt(31));
            String question = dummy_questions.get(i);
            String questionDetails = "This is a test";
            Question q = new Question(lawField, date, question, questionDetails,
                    new Random().nextInt(20), Answer.createDummyAnswers(6));
            result.add(q);

        }
        return result;
    }

    private ArrayList<Question> createCustomData(String[][] questions, String[][][] answers,
                                                 String lawFiled)
            throws JSONException {
        ArrayList<Question> result = new ArrayList<>();
        Random rand = new Random();
        JSONObject index = backend.getDbUserIndex();
        JSONArray users = index.getJSONArray("USERS");
        JSONArray lawyers = index.getJSONArray("LAWYERS");
        for (int i = 0; i < questions.length; i++) {
            String[] qArr = questions[i];
            String[][] aArr = answers[i];
            int rating = Integer.parseInt(qArr[0]);
            String question = qArr[1];
            String details = qArr[2];
            String date = qArr[3];
            ArrayList<Answer> answerList = new ArrayList<>();
            for (String[] ans : aArr) {
                int ansRating = Integer.parseInt(ans[0]);
                String ansText = ans[1];
                String type = rand.nextBoolean() ? "user" : "lawyer";
                String name;
                boolean annon;
                if (type.equals("user")) {
                    int k = rand.nextInt(users.length() - 1);
                    name = Backend.getNameFromKey(users.getString(k));
                    annon = true;
                } else {
                    int k = rand.nextInt(lawyers.length() - 1);
                    name = Backend.getNameFromKey(lawyers.getString(k));
                    annon = rand.nextBoolean();
                }
                answerList.add(new Answer(question, date, name, ansText, type,
                        ansRating, annon));
            }
            result.add(new Question(lawFiled, date, question, details, rating, answerList));
        }
        return result;
    }

    public void generateDebugData() {
        try {
            String[] forumCategories = context.getResources().getStringArray(R.array.law_topics);
            JSONObject jsonObject = new JSONObject();
            for (String s : forumCategories) {
                JSONObject questions = new JSONObject();
                for (Question q : createDebugQuestions(s)) {
                    questions.put(q.getUUID(), q.toString());
                }
                jsonObject.put(s, questions);
            }
            backend.initForumData(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void generateDemoData() {
        try {
            String[] forumCategories = context.getResources().getStringArray(R.array.law_topics);
            JSONObject jsonObject = new JSONObject();
            for (String s : forumCategories) {
                JSONObject questions = new JSONObject();
                ArrayList<Question> list;
                if (s.equals("Personal Injury")) {
                    list = createCustomData(PERSONAL_INJURY_QUESTIONS, PERSONAL_INJURY_ANSWERS, s);
                } else if (s.equals("Real Estate")) {
                    list = createCustomData(REAL_ESTATE_QUESTIONS, REAL_ESTATE_ANSWERS, s);
                } else {
                    list = createDebugQuestions(s);
                }
                for (Question q : list) {
                    questions.put(q.getUUID(), q.toString());
                }
                jsonObject.put(s, questions);
            }
            backend.initForumData(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
    }
}
