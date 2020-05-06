package com.example.secondtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterFriends extends ArrayAdapter<User> {
    //the list values in the List of type hero
    ArrayList<User> friends;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    FriendsDAO friendsDAO;
    String login;

    //constructor initializing the values
    public CustomAdapterFriends(Context context, int resource, ArrayList<User> friends, String login) {
        super(context, resource, friends);
        this.context = context;
        this.resource = resource;
        this.friends = friends;
        this.login = login;
        this.friendsDAO = new FriendsDAO(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //récupérer la "View"
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        //ImageView imageView = view.findViewById(R.id.imageView);
        TextView friendName = view.findViewById(R.id.friendName);
        Button buttonDelete = view.findViewById(R.id.deleteFriendButton);

        final User friend = friends.get(position);

        //adding values to the list item
        //imageView.setImageDrawable(context.getResources().getDrawable(hero.getImage()));
        friendName.setText(friend.getLogin());

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFriend(friend);
            }
        });
        return view;
    }

    //adaptation de la searchView
    @NonNull
    @Override
    public Filter getFilter() {
        return friendsFilter;
    }

    private Filter friendsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<User> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(friends);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User friend : friends) {
                    if (friend.getLogin().toLowerCase().contains(filterPattern)) {
                        suggestions.add(friend);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((User) resultValue).getLogin();
        }
    };

    //permet de supprimer une wishlist
    private void removeFriend(final User friend) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this friend?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                friendsDAO.deleteFriend(friend.getLogin(),login);
                friends.remove(friend);

                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}