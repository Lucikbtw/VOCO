package com.example.voco.contacts;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voco.R;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> contacts;

    public ContactAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }

        Contact contact = contacts.get(position);

        TextView nameTextView = view.findViewById(R.id.name_contact);
        TextView phoneTextView = view.findViewById(R.id.number_contact);
        ImageView photoImageView = view.findViewById(R.id.photo_contact);

        nameTextView.setText(contact.getName());
        phoneTextView.setText(contact.getPhoneNumber());

        // Загрузка и отображение фотографии, если она есть
        if (contact.getPhotoUri() != null) {
            photoImageView.setImageURI(Uri.parse(contact.getPhotoUri()));
        } else {
            // Если фотографии нет, можно установить изображение по умолчанию
            photoImageView.setImageResource(R.drawable.voco_logo);
        }

        return view;
    }
}