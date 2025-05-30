package com.example.voco.contacts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.voco.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsService {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private Context context;

    public ContactsService(Context context) {
        this.context = context;
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, необходимо запросить его
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            return contacts; // Возвращаем пустой список, пока разрешение не будет получено
        }

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") String hasPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                @SuppressLint("Range") String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)); // Получаем URI фото

                if (Integer.parseInt(hasPhoneNumber) > 0) {
                    Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
                    String[] selectionArgs = {id};
                    Cursor phoneCursor = contentResolver.query(phoneUri, null, selection, selectionArgs, null);

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {
                        while (phoneCursor.moveToNext()) {
                            @SuppressLint("Range") String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Contact contact = new Contact(name, phoneNumber, photoUri);
                            contacts.add(contact);
                        }
                        phoneCursor.close();
                    }
                }
            }
            cursor.close();
        }

        return contacts;
    }
}