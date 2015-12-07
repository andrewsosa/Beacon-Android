package com.andrewsosa.beacon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends PagerFragment {

    private OnFragmentInteractionListener mListener;

    private EditText mInputText;
    private ImageButton mSubmitButton;
    private RecyclerView mRecyclerView;
    private FirebaseChatAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Firebase chatRef;
    private String chatKey;

    // Required empty public constructor
    public ChatFragment() {}

    public static ChatFragment newInstance(String chatKey) {
        ChatFragment fragment = new ChatFragment();
        fragment.setChatKey(chatKey);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("Beacon", "onActivityCreated");

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(savedInstanceState != null) {
            Log.d("Beacon", "savedInstanceState != null");
            chatKey = savedInstanceState.getString("chatKey", "");
        } else {
            Log.d("Beacon", "savedInstanceState == null");
        }

        chatRef = new Firebase(Beacon.URL).child("chat").child(chatKey);
        mAdapter = new FirebaseChatAdapter(ChatMessageModel.class, R.layout.tile_chat_right,
                ChatMessageViewHolder.class, chatRef);
        mRecyclerView.setAdapter(mAdapter);

        mSubmitButton.setOnClickListener(new MessageSubmitListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mInputText = (EditText) v.findViewById(R.id.et_chat_input);
        mSubmitButton = (ImageButton) v.findViewById(R.id.btn_chat_submit);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("chatKey", chatKey);
        super.onSaveInstanceState(outState);
    }

    @Override
    public String getName() {
        return "Chat";
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public void updateChatKey(String chatKey) {
        this.chatKey = chatKey;
        chatRef = buildChatReference(this.chatKey);

        if(mAdapter != null) {
            Log.d("Beacon", "Updated with new chat key:" + chatKey);
            mAdapter.swapRef(chatRef);
            mAdapter.notifyDataSetChanged();
        } else {
            Log.d("Beacon", "mAdapter was null");
        }


    }

    public FirebaseChatAdapter buildChatAdapter(Firebase ref) {
        return new FirebaseChatAdapter(ChatMessageModel.class, R.layout.tile_chat_right,
                ChatMessageViewHolder.class, ref);
    }

    public Firebase buildChatReference(String chatKey) {
        return new Firebase(Beacon.URL).child("chat").child(chatKey);
    }


    public interface OnFragmentInteractionListener {
        void onUpdateSelect(Uri uri);
    }

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View tile;
        public TextView message;
        public TextView subheader;
        public TextView icon_letter;
        public ImageView icon_image;

        // Constructor
        public ChatMessageViewHolder(View v) {
            super(v);
            tile = v.findViewById(R.id.tile);
            message = (TextView) v.findViewById(R.id.tv_chat_message);
            //subheader = (TextView) v.findViewById(R.id.tv_tile_subheader);
        }
    }

    public static class FirebaseChatAdapter extends FirebaseRecyclerAdapter<ChatMessageModel, ChatMessageViewHolder> {

        public FirebaseChatAdapter(Class<ChatMessageModel> modelClass, int modelLayout, Class<ChatMessageViewHolder> viewHolderClass, Firebase ref) {
            super(modelClass, modelLayout, viewHolderClass, ref);

        }

        @Override
        protected void populateViewHolder(ChatMessageViewHolder viewHolder, ChatMessageModel model, int position) {
            viewHolder.message.setText(model.getMessage());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChatMessageModel {
        public String message;
        public String authorID;
        public String authorName;
        public long timestamp;

        public ChatMessageModel() {}

        public ChatMessageModel(String authorID, String authorName, String message, long timestamp) {
            this.authorID = authorID;
            this.authorName = authorName;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getAuthorID() {
            return authorID;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getMessage() {
            return message;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    public class MessageSubmitListener implements View.OnClickListener {

        public void submitMessage() {
            String message = mInputText.getText().toString().trim();
            Firebase messageRef = chatRef.push();
            messageRef.setValue(new ChatMessageModel(chatRef.getAuth().getUid(), null, message, 0));
            mInputText.setText("");
        }

        @Override
        public void onClick(View v) {
            submitMessage();
        }
    }
}
