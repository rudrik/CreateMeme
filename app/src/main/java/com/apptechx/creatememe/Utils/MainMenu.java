package com.apptechx.creatememe.Utils;

import android.content.Context;
import com.apptechx.creatememe.R;

import java.util.ArrayList;

/**
 * Created by AppTechX on 12/10/2014.
 */

public class MainMenu {
    public static ArrayList<MenuItems> Items;

    public static void LoadModel(Context context) {
        Items = new ArrayList<MenuItems>();
        String[] mPlanetTitles = context.getResources().getStringArray(R.array.menu_array);
        for (int i = 0; i < mPlanetTitles.length; i++) {
            Items.add(new MenuItems(i + 1, R.drawable.ic_drawer, mPlanetTitles[i]));
        }
    }

    public static MenuItems GetbyId(int id) {
        for (MenuItems item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }
}