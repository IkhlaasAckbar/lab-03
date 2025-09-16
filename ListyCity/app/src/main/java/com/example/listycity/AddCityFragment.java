package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment  extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    private AddCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement " +
                    "AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        boolean isEdit = false;
        int position = -1;
        if (args != null) {
            String cityName = args.getString("cityName");
            String provinceName = args.getString("provinceName");
            position = args.getInt("position", -1);
            if (cityName != null && provinceName != null) {
                isEdit = true;
                editCityName.setText(cityName);
                editProvinceName.setText(provinceName);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        boolean finalIsEdit = isEdit;
        int finalPosition = position;

        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (finalIsEdit) {
                        listener.editCity(new City(cityName, provinceName), finalPosition);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }                })
                .create();

    }
}
