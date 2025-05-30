package com.example.voco;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ChatFragment extends Fragment {

    private static final String ARG_CHAT_ID = "chat_id";
    private static final String ARG_CHAT_NAME = "chat_name";

    private String chatId;
    private String chatName;

    private TextView textViewChatName;
    private TextView textViewChatContent;

    private ImageView btnBack;

    public static ChatFragment newInstance(String chatId, String chatName) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_ID, chatId);
        args.putString(ARG_CHAT_NAME, chatName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chatId = getArguments().getString(ARG_CHAT_ID);
            chatName = getArguments().getString(ARG_CHAT_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        textViewChatName = view.findViewById(R.id.tv_chatname);
        textViewChatContent = view.findViewById(R.id.text_view_chat_content);
        btnBack = view.findViewById(R.id.bt_back);

        if (chatName != null) {
            textViewChatName.setText(chatName);
            textViewChatContent.setText("Чат с " + chatName + "\nID: " + chatId + "\n\nЗдесь будут отображаться сообщения...");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Возврат к предыдущему фрагменту
                NavHostFragment.findNavController(ChatFragment.this).navigateUp();
            }
        });

        return view;
    }

}