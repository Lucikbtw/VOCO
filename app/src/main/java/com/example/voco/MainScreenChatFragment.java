package com.example.voco;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voco.domain.Chat;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainScreenChatFragment extends Fragment {

    private RecyclerView recyclerViewChats;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;
    private OnChatSelectedListener listener;
    private Button btnContacts;


    public interface OnChatSelectedListener {
        void onChatSelected(Chat chat);
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof OnChatSelectedListener) {
//            listener = (OnChatSelectedListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnChatSelectedListener");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen_chat_fragment, container, false);

        btnContacts = view.findViewById(R.id.bottom_navigation);

        recyclerViewChats = view.findViewById(R.id.recycler_view_chats);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(getContext()));
        initializeChatList();
        setupRecyclerView();



        return view;
    }

    private void setupContactsButton() {
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход к ContactFragment
                NavHostFragment.findNavController(MainScreenChatFragment.this)
                        .navigate(R.id.action_mainScreenChatFragment_to_contactFragment);
            }
        });
    }

    private void initializeChatList() {
        chatList = new ArrayList<>();
        // Добавляем тестовые данные
        chatList.add(new Chat("1", "Иван Петров", "Привет, как дела?", System.currentTimeMillis() - 3600000));
        chatList.add(new Chat("2", "Мария Сидорова", "Увидимся завтра", System.currentTimeMillis() - 7200000));
        chatList.add(new Chat("3", "Группа друзей", "Кто идет в кино?", System.currentTimeMillis() - 10800000));
        chatList.add(new Chat("4", "Алексей Иванов", "Спасибо за помощь!", System.currentTimeMillis() - 14400000));
        chatList.add(new Chat("5", "Семейный чат", "Не забудьте про ужин", System.currentTimeMillis() - 18000000));
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(chatList, new ChatAdapter.OnChatClickListener() {
            @Override
            public void onChatClick(Chat chat) {
                Bundle bundle = new Bundle();
                bundle.putString("chatId", chat.getId());
                bundle.putString("chatName", chat.getName());


                NavHostFragment.findNavController(MainScreenChatFragment.this)
                        .navigate(R.id.action_mainScreenChatFragment_to_chatFragment, bundle);
            }
        });
        recyclerViewChats.setAdapter(chatAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}