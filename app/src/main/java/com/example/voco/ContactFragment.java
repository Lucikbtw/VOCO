package com.example.voco;



import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.voco.contacts.Contact;
import com.example.voco.contacts.ContactAdapter;
import com.example.voco.contacts.ContactsService;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.List;

public class ContactFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ListView contactsListView;
    private ContactAdapter contactAdapter;
    private ContactsService contactsService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        contactsListView = view.findViewById(R.id.lv_contact);
        contactsService = new ContactsService(getContext());

        // Проверяем разрешение на чтение контактов при создании фрагмента
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, необходимо запросить его
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            // Разрешение уже есть, загружаем контакты
            loadContacts();
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение предоставлено, загружаем контакты
                loadContacts();
            } else {
                // Разрешение отклонено, можно показать сообщение пользователю
                // или предпринять другие действия
            }
        }
    }

    private void loadContacts() {
        List<Contact> contacts = contactsService.getContacts();
        contactAdapter = new ContactAdapter(getContext(), R.layout.contact, contacts);
        contactsListView.setAdapter(contactAdapter);
    }
}