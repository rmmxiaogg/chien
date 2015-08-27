package com.umeng.fb.example;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.fb.util.Log;
import com.umeng.message.PushAgent;


public class MainActivity extends Activity {
    FeedbackAgent fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        com.umeng.fb.util.Log.LOG = true;

        setUpUmengFeedback();
    }

    private void setUpUmengFeedback() {
        fb = new FeedbackAgent(this);
        // check if the app developer has replied to the feedback or not.
		fb.sync();
		fb.openFeedbackPush();
		PushAgent.getInstance(this).enable();

//        fb.setWelcomeInfo("Welcome to use umeng feedback app");
//        FeedbackPush.getInstance(this).init(true);
//        PushAgent.getInstance(this).setPushIntentServiceClass(MyPushIntentService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_feedback) {
            fb.startFeedbackActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_main, container, false);

            root.findViewById(R.id.fb_fragment_activity_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ConversationDetailActivity.class);
                    String id = new FeedbackAgent(getActivity()).getDefaultConversation().getId();
                    intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, id);
                    startActivity(intent);
                }
            });
            root.findViewById(R.id.sdk_fb_activity_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FeedbackAgent(getActivity()).startFeedbackActivity();
                }
            });
            root.findViewById(R.id.multi_conversation_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ConversationListActivity.class);
                    startActivity(intent);
                }
            });
            
            root.findViewById(R.id.custom_activity_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CustomActivity.class);
                    startActivity(intent);
                }
            });
            
            
            return root;
        }
    }
}
