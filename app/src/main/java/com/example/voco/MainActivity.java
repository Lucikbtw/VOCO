package com.example.voco;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voco.domain.Chat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatAdapter.OnChatClickListener {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создаем тестовые данные
        chatList = new ArrayList<>();
        chatList.add(new Chat("1", "Алексей", "Привет! Как дела?"));
        chatList.add(new Chat("2", "Мария", "Встречаемся завтра?"));
        chatList.add(new Chat("3", "Дмитрий", "Отправил документы"));
        chatList.add(new Chat("4", "Анна", "Спасибо за помощь!"));
        chatList.add(new Chat("5", "Сергей", "Как проект?"));

        chatAdapter = new ChatAdapter(chatList, this);
        recyclerView.setAdapter(chatAdapter);

        if (recyclerView == null) {
            Log.e("MainActivity", "RecyclerView не найден!");
            return;
        }
    }

    @Override
    public void onChatClick(Chat chat) {
        // Создаем и показываем фрагмент чата
        ChatFragment chatFragment = ChatFragment.newInstance(chat.getId(), chat.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавляем фрагмент в контейнер
        fragmentTransaction.replace(R.id.fragment_container, chatFragment);
        fragmentTransaction.addToBackStack(null); // Позволяет вернуться назад
        fragmentTransaction.commit();

        // Скрываем RecyclerView
        recyclerView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Показываем RecyclerView обратно
            recyclerView.setVisibility(android.view.View.VISIBLE);
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}